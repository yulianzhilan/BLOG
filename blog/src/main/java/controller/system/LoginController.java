package controller.system;

import controller.BaseController;
import dto.CallBackDTO;
import dto.system.UserQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.ConfigService;
import service.ValidateService;
import util.AssembleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by scott on 2017/3/15.
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
    @Autowired
    private ConfigService configService;

    @Autowired
    private ValidateService validateService;

    private ModelAndView prepared(){
        return new ModelAndView("login");
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView execute(HttpServletRequest request){
        if(isLogin(request)){
            request.getSession().setAttribute("modules",validateService.getSideBar(getCurrentUser(request)));
            return new ModelAndView("home").addObject("userDTO", AssembleUtil.assembleUserSummaryDTO(getCurrentUser(request)));
        }
//        System.out.println(configService.getConfig("_TOKEN_KEY"));
        return prepared().addObject("loginDTO", new UserQueryDTO());
    }

    @RequestMapping(value = "/validate", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView validate(@ModelAttribute("loginDTO")UserQueryDTO user, HttpServletRequest request){
        if(StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getPassword())){
            return prepared().addObject("errtx", "发生未知异常！");
        }
        CallBackDTO callBackDTO = validateService.validate(user.getAccount(), user.getPassword());
        if(callBackDTO.isStatus()){
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30*60);
            session.setAttribute("_token", callBackDTO.getObj());
            return execute(request);
        }
        return prepared().addObject("errtx", callBackDTO.getErrtx());
    }

}
