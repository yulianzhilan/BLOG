package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.HelperService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by scott on 2017/3/27.
 */
@Controller
@RequestMapping("helper")
public class HelperController extends BaseController{

    @Autowired
    private HelperService helperService;

    @RequestMapping(value = "/getInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getInfoFromDataBase(HttpServletRequest request, @ModelAttribute("table")String table, @ModelAttribute("column")String column, @ModelAttribute("query")String query){
        List result =  helperService.getInfoFromDataBase(getCurrentUserId(request),table,column,query);
        return ajaxModelAndView(true).addObject("result", result);
    }





}
