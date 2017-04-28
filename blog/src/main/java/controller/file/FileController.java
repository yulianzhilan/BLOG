package controller.file;

import controller.BaseController;
import dto.CallBackDTO;
import dto.file.FileDTO;
import dto.file.FileMeta;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import service.ConfigService;
import service.FileService;
import service.FileUpDownloadService;
import util.Constants;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by scott on 2017/3/22.
 */
@RequestMapping("/file")
@Controller
public class FileController extends BaseController{


    @Autowired
    private FileUpDownloadService fileUpDownloadService;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "manage", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView manage(){
        return new ModelAndView("file/manage");
    }

    @RequestMapping(value = "upload", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String upload(String dir, HttpServletRequest request) throws Exception{
        CallBackDTO callback = fileUpDownloadService.upload(request, getCurrentUserId(request), dir);
        JSONObject obj = new JSONObject();

        obj.put("error",0);
        obj.put("url", callback.getObj());

        return obj.toJSONString();
    }

    /**
     */
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public ModelAndView fileUpload(MultipartHttpServletRequest request) throws Exception{
        List<FileDTO> files = fileService.writeFile(request, getCurrentUserId(request));
        if(files == null || files.size() == 0){
            return ajaxModelAndView(false);
        }
        boolean flag;
        for(FileDTO file : files){
            flag = fileService.saveFile(getCurrentUserId(request),file);
            if(!flag){
                return ajaxModelAndView(false);
            }
        }
        return ajaxModelAndView(true).addObject("result",files);
    }

    /**
     * 所有类型文件下载
     * @param response
     * @param fileId
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void get(HttpServletResponse response, int fileId, HttpServletRequest request){

        List<FileDTO> files = fileService.getFiles(getCurrentUserId(request),null,null,fileId);
        if(files == null || files.size() == 0){
            return;
        }
        FileMeta getFile = fileService.loadFile(files.get(0).getFilePath());
        if(getFile == null){
            return;
        }
        try{
            response.setContentType(getFile.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getFileName()+"\"");
            FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 图片类型加载页面显示
     * @param request
     * @param response
     * @param category
     * @param location
     * @param name
     * @throws Exception
     */
    @RequestMapping(value = "/download/{category}/{location}/{name}", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable String category, @PathVariable String location, @PathVariable String name) throws  Exception{

        String ext = request.getRequestURI().split("\\.")[1];

        CallBackDTO callBackDTO = fileUpDownloadService.download(category,location,getCurrentUserId(request),name,ext);
        response.setContentType("image/"+ext);
        OutputStream os = response.getOutputStream();
        ImageIO.write((BufferedImage)callBackDTO.getObj(),ext,os);
    }

    /**
     * 加载个人的空间（图片、多媒体...）
     * @param path
     * @param order
     * @param dir
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/preview", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String preview(@ModelAttribute("path")String path,@ModelAttribute("order")String order,@ModelAttribute("dir")String dir, HttpServletRequest request, HttpServletResponse response) throws Exception{
        CallBackDTO result = fileUpDownloadService.preview(path,dir,order,getCurrentUserId(request));
        if(!result.isOkay()){
            return result.getErrtx();
        } else{
            response.setContentType("application/json; charset=UTF-8");
            return ((JSONObject)result.getObj()).toJSONString();
        }
    }

    @RequestMapping(value = "/upload_file",method = {RequestMethod.POST})
    public ModelAndView uploadFile(MultipartHttpServletRequest request){
        String path = fileService.upload(request,getCurrentUserId(request));
        if(path == null){
           return ajaxModelAndView(false);
        }
        return ajaxModelAndView(true).addObject("link", path);
    }
}
