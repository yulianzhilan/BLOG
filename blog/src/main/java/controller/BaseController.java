package controller;

import dto.system.UserQueryDTO;
import entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import service.ConfigService;
import service.ValidateService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by scott on 2017/3/15.
 */
public class BaseController {
    @Autowired
    private ConfigService configService;

    @Autowired
    private ValidateService validateService;
    // fixme 此处关于登录验证，SSO单点登录需要研究
    protected User getCurrentUser(HttpServletRequest request){
        return (User)request.getSession().getAttribute("_token");

//        String account = configService.decode(pre_account);
//        String password = configService.decode(pre_password);

//        return validateService.getUser(account, password);
    }

    /**
     * 验证是否已登录，登录：true；未登录：false
     * @param request
     * @return
     */
    protected boolean isLogin(HttpServletRequest request){
        return getCurrentUser(request) != null;
    }

    protected ModelAndView ajaxModelAndView(boolean status){
        ModelAndView mav = new ModelAndView();
        mav.addObject("status", status ? "success":"failure");
        return mav;
    }

    protected ModelAndView goLogin(){
        return new ModelAndView("login").addObject("loginDTO", new UserQueryDTO());
    }

    protected int getCurrentUserId(HttpServletRequest request){
        User user = getCurrentUser(request);
        if(user != null){
            return user.getId();
        }
        return 0;
    }
}
