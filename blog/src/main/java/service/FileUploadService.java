package service;

import dto.CallBackDTO;

import java.util.List;

/**
 * Created by scott on 2017/3/22.
 */
public interface FileUploadService {
    //上传文件
    CallBackDTO upload(List items, int userId, String dir);

    //下载文件

}
