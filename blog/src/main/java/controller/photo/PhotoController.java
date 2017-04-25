package controller.photo;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import service.ConfigService;
import service.PhotoService;
import util.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by scott on 2017/3/14.
 */
@Controller
@RequestMapping("/photo")
public class PhotoController extends BaseController{
    @Autowired
    private PhotoService photoService;

    protected ModelAndView prepared(){
        return new ModelAndView("photo/check");
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView execute(){
        return prepared();
    }

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView query(){
        return prepared();
    }

    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public ModelAndView upload(MultipartHttpServletRequest request){
        String url = photoService.upload(request, getCurrentUserId(request));
        if(url == null){
            return ajaxModelAndView(false);
        }
        return ajaxModelAndView(true).addObject("url", url);
    }

    @RequestMapping(value = "/manage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView manage(){
        return new ModelAndView("photo/manage");
    }

}
