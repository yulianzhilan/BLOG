package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by scott on 2017/3/14.
 */
@Controller
@RequestMapping("/photo")
public class PhotoController {

    protected ModelAndView prepared(){
        return new ModelAndView("photo/check");
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView execute(){
        return prepared();
    }

    @RequestMapping(name = "query", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView query(){
        return prepared();
    }

}
