package controller.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by scott on 2017/3/22.
 */
@RequestMapping("/file")
@Controller
public class FileController {

    @RequestMapping(value = "manage", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView manage(){
        return null;
    }

    @RequestMapping(value = "upload", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView upload(){
        return null;
    }
}
