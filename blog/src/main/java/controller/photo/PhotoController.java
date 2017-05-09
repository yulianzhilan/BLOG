package controller.photo;

import com.infoccsp.framework.core.pagination.OrderablePaginationDTO;
import com.infoccsp.framework.core.pagination.PaginationResultDTO;
import controller.BaseController;
import dto.CallBackDTO;
import framework.service.ServiceException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import service.ConfigService;
import service.PhotoService;
import util.Constants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by scott on 2017/3/14.
 */
@Controller
@RequestMapping("/photo")
public class PhotoController extends BaseController{
    @Autowired
    private PhotoService photoService;

    protected ModelAndView prepared(){
        return new ModelAndView("photo/check");
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView execute(){
        return prepared();
    }

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView query(){
        return prepared();
    }

    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public ModelAndView upload(MultipartHttpServletRequest request){
        String url = photoService.upload(request, getCurrentUserId(request));
        if(url == null){
            return ajaxModelAndView(false);
        }
        return ajaxModelAndView(true).addObject("link", url);
    }

    @RequestMapping(value = "/manage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView manage(HttpServletRequest request) throws Exception{
        List<?> result = executeQuery(request, 5,new SerializablePaginationQueryCallback() {
            @Override
            public PaginationResultDTO<?> query(OrderablePaginationDTO op) {
                return photoService.getPhotos(op, getCurrentUserId(request), 0, true);
            }
        });

        return new ModelAndView("photo/manage").addObject("result", result);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView delete(HttpServletRequest request, int id){
        if(id == 0){
            throw new ServiceException("删除信息错误！");
        }

        photoService.delete(id, getCurrentUserId(request));

        return ajaxModelAndView(true);
    }

    @RequestMapping(value = "/kindGroup", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String kindGroup(HttpServletRequest request, String path, String order, HttpServletResponse response){
        CallBackDTO result = photoService.preview(path,order,getCurrentUserId(request));
        if(!result.isOkay()){
            return result.getErrtx();
        } else{
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            return ((JSONObject)result.getObj()).toJSONString();
        }
    }

    // fixme 只是用于测试编辑器
    @RequestMapping(value = "/editor")
    public ModelAndView editor(){
        return new ModelAndView("editor");
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(HttpServletRequest request){
        return null;
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    public String get(int photoId){
        return "http://ooxxhoti3.bkt.clouddn.com/1_2017042813555242510.jpg";
    }
}
