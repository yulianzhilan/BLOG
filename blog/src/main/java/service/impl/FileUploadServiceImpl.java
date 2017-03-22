package service.impl;

import dto.CallBackDTO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ConfigService;
import service.FileUploadService;
import util.Constants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by scott on 2017/3/22.
 */
@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private ConfigService configService;

    @Override
    public CallBackDTO upload(List items, int userId, String dirName) {
        CallBackDTO result = new CallBackDTO();
        //文件保存目录路径
        String savePath = configService.getConfig(Constants.SAVE_PATH);
        //文件保存目录URL
        String saveUrl = "";
        //最大文件大小
        long maxSize = 1000000;
        //检查目录
        File uploadDir = new File(savePath);
        if(!uploadDir.isDirectory()){
            result.setOkay(false);
            result.setErrtx("上传目录不存在");
        }
        //检查目录写权限
        if(!uploadDir.canWrite()){
            result.setOkay(false);
            result.setErrtx("上传目录没有写权限");
        }

        if(dirName == null){
            dirName = "image";
        }
        if(!Constants.getExtName().containsKey(dirName)){
            result.setOkay(false);
            result.setErrtx("目录名称不正确");
        }
        //创建文件夹
        savePath += userId + "/" + dirName + "/";
        saveUrl += "/file/download/" + dirName + "/";

        File saveDirFile = new File(savePath);
        if(!saveDirFile.exists()){
            saveDirFile.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        savePath += date + "/";
        saveUrl += date + "/";
        File dirFile = new File(savePath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        Iterator it = items.iterator();

        while(it.hasNext()){
            FileItem item = (FileItem) it.next();
            String fileName = item.getName();
            long fileSize = item.getSize();
            if(!item.isFormField()){
                //检查文件大小
                if(fileSize > maxSize){
                    result.setOkay(false);
                    result.setErrtx("文件大小超过限制");
                }
                //检查扩展名
                String fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
                if(!Arrays.<String>asList(Constants.getExtName().get(dirName).split(",")).contains(fileExt)){
                    result.setOkay(false);
                    result.setErrtx("上传文件扩展名是不允许的扩展名。\n只允许"+Constants.getExtName().get(dirName)+"格式。");
                }

                SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
                String newFileName = fmt.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;

                try{
                    File uploadedFile = new File(savePath, newFileName);
                    item.write(uploadedFile);
                } catch(Exception e){
                    result.setOkay(false);
                    result.setErrtx("上传文件失败");
                }
            }
        }

        //设置保存目录
        result.setObj(saveUrl);

        return result;
    }

    public CallBackDTO download(String saveUrl,int userId,String dirName,String path){
        String url = saveUrl.split("/file/download/")[1];
        String id = url.substring(0,url.indexOf("/")-1);
        CallBackDTO result = new CallBackDTO();
        if(!id.equals(Integer.toString(userId))){
            result.setOkay(false);
            result.setErrtx("权限不足");
        }
        String rootPath = configService.getConfig("SAVE_PATH")+userId+"/";
        String rootUrl = "";
        if(dirName != null){
            if(!Arrays.<String>asList(Constants.FILEEXTCATEGORY).contains(dirName)){
                result.setOkay(false);
                result.setErrtx("非法路径");
            }
            rootPath += dirName + "/";
            rootUrl += "/file/download/" + dirName + "/";
            File saveDirFile = new File(rootPath);
            if(!saveDirFile.exists()){
                saveDirFile.mkdirs();
            }
        }
        //根据path参数，设置各路径和URL
        String currentPath = rootPath + path;
        String currentUrl = rootUrl + path;
        String currentDirPath = path;
        String moveupDirPath = "";

        if(!"".equals(path)){
            String str = currentDirPath.substring(0,currentDirPath.length()-1);
            moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/")+1) : "";
        }
    }

}
