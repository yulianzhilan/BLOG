package controller.file;

import controller.BaseController;
import dto.CallBackDTO;
import dto.file.FileMeta;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import service.FileUpDownloadService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by scott on 2017/3/22.
 */
@RequestMapping("/file")
@Controller
public class FileController extends BaseController{

    @Autowired
    private FileUpDownloadService fileUpDownloadService;

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
     *  fixme 这个方法保存在本地的同时还保存在内存中，有待商榷
     */
    LinkedList<FileMeta> files = new LinkedList<>();
    FileMeta fileMeta = null;
    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    public ModelAndView fileupload(MultipartHttpServletRequest request) throws Exception{
        //1.
        Iterator<String> it = request.getFileNames();
        MultipartFile multipartFile ;
        //2.
        while(it.hasNext()){
            //2.1
            multipartFile = request.getFile(it.next());
            System.out.println(new String(multipartFile.getOriginalFilename().getBytes("iso8859-1"),"utf-8"));

            //2.2
            if(files.size() >= 10){
                files.pop();
            }

            fileMeta = new FileMeta();

            //fixme 中文乱码问题
            String filename = new String(multipartFile.getOriginalFilename().getBytes("iso8859-1"),"utf-8");

            fileMeta.setFileName(filename);
            fileMeta.setFileSize(multipartFile.getSize()/1024+" Kb");
            fileMeta.setFileType(multipartFile.getContentType());

            try{
                fileMeta.setBytes(multipartFile.getBytes());
                FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream("E:/attachment/"+filename));
            } catch(IOException e){
                e.printStackTrace();
            }
            files.add(fileMeta);
        }
        return ajaxModelAndView(true).addObject("result",files);
    }

    /**
     * 所有类型文件下载
     * fixme 与上面的方法对应。从内存中获取数据。有待商榷
     * @param response
     * @param value
     */
    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
    public void get(HttpServletResponse response, @PathVariable String value){
        FileMeta getFile = files.get(Integer.parseInt(value));
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
}
