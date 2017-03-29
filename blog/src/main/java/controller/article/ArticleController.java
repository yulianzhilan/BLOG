package controller.article;

import controller.BaseController;
import dto.DataBaseDTO;
import dto.article.ArticleDTO;
import dto.article.ArticleFolderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.ArticleService;
import service.HelperService;
import util.Constants;

import javax.servlet.http.HttpServletRequest;
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

}
