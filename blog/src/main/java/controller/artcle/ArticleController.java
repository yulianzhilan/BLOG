package controller.artcle;

import controller.BaseController;
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

    @RequestMapping(value = "read", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView read(HttpServletRequest request){
        //fixme 登录验证单独提取出去，使用拦截器或者其他技术手段
        //登录验证
        if(!isLogin(request)){
            return goLogin();
        }
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
        return new ModelAndView("article/manage");
    }

    @RequestMapping(value = "demo", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView demo(){
        return new ModelAndView("demo");
    }
}
