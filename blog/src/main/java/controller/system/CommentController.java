package controller.system;

import controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by scott on 2017/4/25.
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

    public ModelAndView like(){
        return null;
    }

}
