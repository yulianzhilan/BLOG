package service;

import dto.CallBackDTO;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by scott on 2017/3/22.
 */
public interface FileUpDownloadService {
    //上传文件
    CallBackDTO upload(HttpServletRequest request, int userId, String dir) throws FileUploadException;

    //下载文件
    CallBackDTO download(String category,String location, int userId, String name,String ext);

    CallBackDTO preview(String path,String dir,String order, int userId);
}
