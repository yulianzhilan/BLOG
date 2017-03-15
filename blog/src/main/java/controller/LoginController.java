package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.ConfigService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by scott on 2017/3/15.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ConfigService configService;


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView execute(HttpServletRequest request){
        System.out.println(configService.getConfig("_TOKEN_KEY"));
        return null;
    }

}
