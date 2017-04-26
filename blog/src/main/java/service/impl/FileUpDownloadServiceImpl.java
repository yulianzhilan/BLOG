package service.impl;

import dto.CallBackDTO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ConfigService;
import service.FileUpDownloadService;
import util.ComparatorUtil;
import util.Constants;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by scott on 2017/3/22.
 */
@Service("fileUpDownloadService")
public class FileUpDownloadServiceImpl implements FileUpDownloadService {
    @Autowired
    private ConfigService configService;

    @Override
    public CallBackDTO upload(HttpServletRequest request, int userId, String dirName) throws FileUploadException {
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
        saveUrl += "/blog/file/download/" + dirName + "/";

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
        List items = upload.parseRequest(request);
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
                    saveUrl += newFileName;
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

    @Override
    public CallBackDTO preview(String path,String dir,String order, int userId){
        // 文件在本地的位置
        CallBackDTO result = new CallBackDTO();
        String rootPath = configService.getConfig(Constants.SAVE_PATH)+userId+"/";
        String rootUrl = "";
        if(dir != null){
            if(!Arrays.<String>asList(Constants.FILEEXTCATEGORY).contains(dir)){
                result.setOkay(false);
                result.setErrtx("非法路径");
            }
            rootPath += dir + "/";
            rootUrl += "/blog/file/download/"+ dir + "/";
            File saveDirFile = new File(rootPath);
            //如果文件夹不存在，新建文件夹
            if(!saveDirFile.exists()){
                saveDirFile.mkdirs();
            }
        }
        //根据path参数，设置各路径和URL
        //path是类别目录下一级目录
        String currentPath = rootPath + path;
        String currentUrl = rootUrl + path;
        String currentDirPath = path;
        String moveupDirPath = "";

        // 改变目录等级
        if(!"".equals(path)){
            String str = currentDirPath.substring(0,currentDirPath.length()-1);
            moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/")+1) : "";
        }

        //排序
        order = order != null ? order.toLowerCase() : "name";

        // 不允许使用..移动到上一级目录
        if(path.contains("\\.\\.")){
            result.setOkay(false);
            result.setErrtx("Parameter is not valid.");
        }

        // 不允许最后一个字符不是/
        if(!"".equals(path)&& !path.endsWith("/")){
            result.setOkay(false);
            result.setErrtx("Parameter is not valid.");
        }

        File currentFile = new File(currentPath);
        if(!currentFile.exists()){
            result.setOkay(false);
            result.setErrtx("Directory does not exist.");
        }

        List<Hashtable> fileList = findFiles(currentFile);

        if("size".equalsIgnoreCase(order)){
            Collections.sort(fileList,new ComparatorUtil.SizeComparator());
        } else if("type".equalsIgnoreCase(order)){
            Collections.sort(fileList, new ComparatorUtil.TypeComparator());
        } else{
            Collections.sort(fileList, new ComparatorUtil.NameComparator());
        }

        JSONObject obj = new JSONObject();
        obj.put("moveup_dir_path", moveupDirPath);
        obj.put("current_dir_path", currentDirPath);
        obj.put("current_url", currentUrl);
        obj.put("total_count", fileList.size());
        obj.put("file_list", fileList);

        result.setOkay(true);
        result.setObj(obj);
        return result;
    }

    @Override
    public CallBackDTO download(String category,String location, int userId, String name,String ext){
        CallBackDTO result = new CallBackDTO();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(configService.getConfig(Constants.SAVE_PATH)+userId+File.separator+category+File.separator+location+File.separator+name+"."+ext));
        } catch(IOException e){
            e.printStackTrace();
        }
        result.setObj(image);
        return result;
    }

    /**
     * 遍历目录获取文件信息
     */
    protected List<Hashtable> findFiles(File source){
        if(source == null){
            return  null;
        }
        List<Hashtable> fileList = new ArrayList<>();
        for(File file : source.listFiles()){
            Hashtable<String, Object> hash = new Hashtable<>();
            String fileName = file.getName();
            if(file.isDirectory()){
                hash.put("is_dir", true);
                hash.put("has_file",(file.listFiles() != null));
                hash.put("filesize", 0L);
                hash.put("is_photo", false);
                hash.put("filetype", "");
            } else if(file.isFile()){
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                hash.put("is_dir", false);
                hash.put("has_file", false);
                hash.put("filesize", file.length());
                hash.put("is_photo", Arrays.<String>asList(Constants.PICEXTNAME).contains(fileExt));
                hash.put("filetype", fileExt);
            }
            hash.put("filename", fileName);
            hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
            fileList.add(hash);
        }
        return fileList;
    }
}
