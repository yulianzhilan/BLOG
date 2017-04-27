package controller.article;

import com.infoccsp.framework.core.pagination.OrderablePaginationDTO;
import com.infoccsp.framework.core.pagination.PaginationResultDTO;
import controller.BaseController;
import dto.DataBaseDTO;
import dto.article.ArticleDTO;
import dto.article.ArticleFolderDTO;
import dto.article.ArticleSummaryDTO;
import entity.system.User;
import framework.service.ServiceException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.ArticleService;
import service.HelperService;
import service.ValidateService;
import util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by scott on 2017/3/20.
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController{
    @Autowired
    private ArticleService articleService;

    @Autowired
    private HelperService helperService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView execute(HttpServletRequest request) throws Exception{
        return classify(request);
    }

    @RequestMapping(value = "/classify", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView classify(HttpServletRequest request) throws Exception{
        List<ArticleFolderDTO> result = articleService.getDefaultArticleFolders(getCurrentUserId(request));
        List<?> articleDTOs = executeQuery(request, new SerializablePaginationQueryCallback() {
            @Override
            public PaginationResultDTO<?> query(OrderablePaginationDTO op) {
                return articleService.getArticles(op, 0, getCurrentUserId(request));
            }
        });
        return new ModelAndView("article/classify").addObject("result", result).addObject("articleDTOs", articleDTOs).addObject("attribute","folder");
    }

    @RequestMapping(value = "/classifyByFolder", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView readByFolder(@ModelAttribute("attribute")String attribute, HttpServletRequest request) throws Exception{
        List<ArticleFolderDTO> result = articleService.getArticleFolders(attribute, getCurrentUserId(request));
        List<?> articleDTOS = executeQuery(request, new SerializablePaginationQueryCallback() {
            @Override
            public PaginationResultDTO<?> query(OrderablePaginationDTO op) {
                return articleService.getArticles(op, 0, getCurrentUserId(request));
            }
        });
        return new ModelAndView("article/classify").addObject("result", result).addObject("articleDTOs", articleDTOS);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView list(@ModelAttribute("name")String name,@ModelAttribute("attribute")String attribute, HttpServletRequest request){
        if(StringUtils.isEmpty(attribute)){
            attribute = Constants.ATTRIBUTE_FOLDER;
        }
        ArticleFolderDTO result = articleService.getArticleFoldersByName(attribute,getCurrentUserId(request),name);
        return new ModelAndView("article/list").addObject("result", result);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView delete(HttpServletRequest request, @ModelAttribute("id")int id){
        boolean result = articleService.deleteById(id, getCurrentUserId(request));
        return ajaxModelAndView(result);
    }

    @RequestMapping(value = "/preview", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView preview(HttpServletRequest request, @ModelAttribute("id")int id){
        ArticleDTO result = articleService.preview(0, getCurrentUserId(request), id);
        return ajaxModelAndView(true).addObject("result", result);
    }

    @RequestMapping(value = "/modify", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView modify(int id, HttpServletRequest request){
        ArticleDTO articleDTO = articleService.preview(0, getCurrentUserId(request), id);
        List<DataBaseDTO> result = helperService.getInfoFromDataBase(getCurrentUserId(request), "ARTICLE", "FOLDER", null);
        return new ModelAndView("article/write").addObject("articleDTO", articleDTO).addObject("folders", result);
    }

    @RequestMapping(value = "/write", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView write(HttpServletRequest request){
        List<DataBaseDTO> result = helperService.getInfoFromDataBase(getCurrentUserId(request), "ARTICLE", "FOLDER", null);
        return new ModelAndView("article/write").addObject("folders", result).addObject("articleDTO", new ArticleDTO());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("articleDTO")ArticleDTO articleDTO, HttpServletRequest request) throws Exception{
        if(articleDTO.getId() == 0){
            articleService.saveArticle(articleDTO, getCurrentUserId(request));
            return null;
        } else{
            articleService.editArticle(articleDTO);
            return new ModelAndView("redirect:read").addObject("id", articleDTO.getId());
        }
    }

    @RequestMapping(value = "/show", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView show(int id, HttpServletRequest request){
        ArticleDTO result = articleService.preview(0, getCurrentUserId(request), id);
        return new ModelAndView().addObject("result", result);
    }

    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<ArticleDTO> result = articleService.getArticles(0, getCurrentUserId(request));
        HSSFWorkbook workbook = generateExcel(result);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition","attachment; filename=" + workbook.getSheetName(0) +".xls");
        workbook.write(response.getOutputStream());
    }

    @RequestMapping(value = "/read", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView read(Integer id){
        if(id == null || id == 0){
            throw new ServiceException("文章id不能为空！");
        }
        ArticleSummaryDTO result = articleService.getArticle(id);
        ArticleDTO articleDTO = articleService.preview(0, result.getUserId(), result.getId());
        return new ModelAndView("article/read").addObject("result", result).addObject("articleDTO", articleDTO);
    }

    protected HSSFWorkbook generateExcel(List<ArticleDTO> list) throws Exception{
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("article");
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        Cell cell0 = row.createCell(0);
        cell0.setCellValue("id");
        Cell cell1 = row.createCell(1);
        cell1.setCellValue("name");
        for(int j=0; j<list.size(); j++){
            Row newRow = sheet.createRow(rowNum++);
            Cell cella = newRow.createCell(0);
            cella.setCellValue(list.get(j).getId());
            Cell cellb = newRow.createCell(1);
            cellb.setCellValue(list.get(j).getName());
        }
        return workbook;
    }
}
