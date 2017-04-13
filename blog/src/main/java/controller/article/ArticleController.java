package controller.article;

import controller.BaseController;
import dto.DataBaseDTO;
import dto.article.ArticleDTO;
import dto.article.ArticleFolderDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.ArticleService;
import service.HelperService;
import util.Constants;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @RequestMapping(value = "read", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView read(HttpServletRequest request){
        //fixme 登录验证单独提取出去，使用拦截器或者其他技术手段
        //登录验证
//        if(!isLogin(request)){
//            return goLogin();
//        }
        List<ArticleFolderDTO> result = articleService.getDefaultArticleFolders(getCurrentUserId(request));
        return new ModelAndView("article/read").addObject("result", result);
    }

    @RequestMapping(value = "readByFolder", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView readByFolder(@ModelAttribute("attribute")String attribute, HttpServletRequest request){
        List<ArticleFolderDTO> result = articleService.getArticleFolders(attribute, getCurrentUserId(request));
        return new ModelAndView("article/read").addObject("result", result);
    }

    @RequestMapping(value = "list", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView list(@ModelAttribute("name")String name,@ModelAttribute("attribute")String attribute, HttpServletRequest request){
        if(StringUtils.isEmpty(attribute)){
            attribute = Constants.ATTRIBUTE_FOLDER;
        }
        ArticleFolderDTO result = articleService.getArticleFoldersByName(attribute,getCurrentUserId(request),name);
        return new ModelAndView("article/list").addObject("result", result);
    }

    @RequestMapping(value = "delete", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView delete(HttpServletRequest request, @ModelAttribute("id")int id){
        boolean result = articleService.deleteById(id, getCurrentUserId(request));
        return ajaxModelAndView(result);
    }

    @RequestMapping(value = "preview", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView preview(HttpServletRequest request, @ModelAttribute("id")int id){
        ArticleDTO result = articleService.preview(false, getCurrentUserId(request), id);
        return ajaxModelAndView(true).addObject("result", result);
    }

    @RequestMapping(value = "manage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView manage(HttpServletRequest request){
        List<DataBaseDTO> result = helperService.getInfoFromDataBase(getCurrentUserId(request), "ARTICLE", "FOLDER", null);
        return new ModelAndView("article/manage").addObject("folders", result);
    }

    //fixme 关于中文乱码问题
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("content")String content,String name,String folder,String person, HttpServletRequest request) throws Exception{
        System.out.println(request.getCharacterEncoding());
        System.out.println(content);
        System.out.println(name);
        System.out.println(person);
        System.out.println(new String(person.getBytes("ISO8859-1"),"utf-8"));
        System.out.println(folder);
        return null;
    }

    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<ArticleDTO> result = articleService.getArticles(false, getCurrentUserId(request));
        HSSFWorkbook workbook = generateExcel(result);
//        response.setHeader("Content-disposition", "attachment; filename=\"" + workbook.getSheetName(0)+"\"");
//        FileCopyUtils.copy(, response.getOutputStream());
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        workbook.write(os);// 将excel写入流
//        byte[] content = os.toByteArray();
//        InputStream is = new ByteArrayInputStream(content);
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
    //        response.reset();
    //        response.setContentType("application/vnd.ms-excel;charset=utf-8"); // msexcel
    //        response.setHeader("Content-Disposition", "attachment;filename=" + new String((workbook.getSheetName(0)+".xlsx").getBytes(), "iso-8859-1"));
    //        FileCopyUtils.copy(content, response.getOutputStream());
//        ServletOutputStream out = response.getOutputStream();
//        bis = new BufferedInputStream(is);
//        bos = new BufferedOutputStream(out);
//        byte[] buff = new byte[2048];
//        int bytesRead;
//        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
//            bos.write(buff, 0, bytesRead);
//        }
//        out.flush();
//        out.close();

        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition","attachment; filename=" + workbook.getSheetName(0) +".xls");
        workbook.write(response.getOutputStream());
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
