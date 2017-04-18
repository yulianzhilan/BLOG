package controller;

import com.infoccsp.framework.web.springmvc.controller.PaginationableController;
import entity.system.User;
import framework.web.pagination.AbstractWebPagination;
import framework.web.pagination.Bootstrap3KeyboardableWebPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import service.ConfigService;
import service.ValidateService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by scott on 2017/3/15.
 */
public class BaseController extends PaginationableController {
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

    protected int getCurrentUserId(HttpServletRequest request){
        User user = getCurrentUser(request);
        if(user != null){
            return user.getId();
        }
        //fixme 如果没有id，处理
        return 1;
    }
    /**
     * 分页默认使用Bootstrap3KeyboardableWebPagination的实现
     */
    @Override
    protected List<?> executeQuery(HttpServletRequest request, PaginationableController.PaginationQueryCallback pqc) throws Exception {
        return this.executeQuery(request, AbstractWebPagination.PAGER_ATTRIBUTE, DEFAULT_PAGE_SIZE, Bootstrap3KeyboardableWebPagination.class, pqc);
    }
    @Override
    protected List<?> executeQuery(HttpServletRequest request, int size, PaginationableController.PaginationQueryCallback pqc) throws Exception {
        return this.executeQuery(request, AbstractWebPagination.PAGER_ATTRIBUTE, size, Bootstrap3KeyboardableWebPagination.class, pqc);
    }
}
