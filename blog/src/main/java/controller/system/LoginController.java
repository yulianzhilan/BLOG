package controller.system;

import com.infoccsp.framework.core.pagination.OrderablePaginationDTO;
import com.infoccsp.framework.core.pagination.PaginationResultDTO;
import controller.BaseController;
import dto.CallBackDTO;
import dto.system.RegisterDTO;
import dto.system.UserQueryDTO;
import dto.system.UserSummaryDTO;
import entity.system.User;
import framework.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.ArticleService;
import service.ValidateService;
import util.AssembleUtil;
import util.LoginException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by scott on 2017/3/15.
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{

    @Autowired
    private ValidateService validateService;

    @Autowired
    private ArticleService articleService;

    private ModelAndView prepared(){
        return new ModelAndView("login");
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView execute(HttpServletRequest request) throws Exception{
        if(isLogin(request)){
            request.getSession().setAttribute("modules",validateService.getSideBar(getCurrentUser(request)));
            request.getSession().setAttribute("userSummaryDTO", validateService.getUserInfo(getCurrentUserId(request)));
//            request.getSession().setAttribute("nickName",getCurrentUser(request).getNickName());
            return home(request);
        }
        return prepared().addObject("loginDTO", new UserQueryDTO());
    }

    @RequestMapping(value = "/validate", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView validate(@ModelAttribute("loginDTO")UserQueryDTO user, HttpServletRequest request) throws Exception{
        if(StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getPassword())){
            return prepared().addObject("errcd", "L104");
        }
        CallBackDTO callBackDTO = validateService.validate(user.getAccount(), user.getPassword());
        if(callBackDTO.isOkay()){
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30*60);
//            session.setAttribute("userSummaryDTO",AssembleUtil.assembleUserSummaryDTO((User) callBackDTO.getObj()));
            session.setAttribute("_token", callBackDTO.getObj());
            return execute(request);
        }
        return prepared().addObject("errcd", callBackDTO.getErrcd());
    }

    @RequestMapping(value = "/passerby", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView passerby(HttpServletRequest request) throws Exception{
        UserQueryDTO user = new UserQueryDTO();
        user.setAccount("PASSERBY");
        user.setPassword("PASSERBY");
        return validate(user, request);
    }

    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView home(HttpServletRequest request) throws Exception{
        if(!isLogin(request)){
            throw new LoginException();
        }
        List<?> articleDTOs = executeQuery(request, 5, new SerializablePaginationQueryCallback() {
            @Override
            public PaginationResultDTO<?> query(OrderablePaginationDTO op) {
            return articleService.getArticles(op, true, 0);
            }
        });
        return new ModelAndView("home").addObject("articleDTOs", articleDTOs);
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView logout(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        session.setAttribute("_token", null);
        return execute(request);
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView register(@ModelAttribute("registerDTO")RegisterDTO registerDTO){
        validateService.register(registerDTO);
        return ajaxModelAndView(true);
    }

    @RequestMapping(value = "/getInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getInfo(HttpServletRequest request){
        return ajaxModelAndView(true).addObject("infoDTO",validateService.getUserInfo(getCurrentUserId(request)));
    }

    @RequestMapping(value = "/setting", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView setting(){
        return null;
    }
}
