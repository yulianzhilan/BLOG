package util;

import framework.service.ServiceException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * Created by scott on 2017/4/21.
 */
public class DefaultMappingExceptionResolver extends SimpleMappingExceptionResolver{


    @Override
    protected ModelAndView getModelAndView(String viewName, Exception ex) {
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("status", "failure");
        if(ex instanceof ServiceException){
            // 错误代码
            mav.addObject("errcd", "B01");
            // 错误内容
            mav.addObject("errtx", ex.getMessage());
        } else{
            mav.addObject("errcd", "S01");
            mav.addObject("errtx", "an unexpected error occurred.");
        }
        // 错误原因

        logger.error(ex.getMessage(), ex);

        return mav;
    }
}
