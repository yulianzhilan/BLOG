package service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import dto.CallBackDTO;
import dto.file.FileDTO;
import dto.file.FileMeta;
import dto.photo.PhotoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.PhotoService;
import service.mapper.PhotoMapper;
import util.CodeUtil;
import util.Constants;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by scott on 2017/3/22.
 */
@Service("photoService")
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoMapper photoMapper;
    /**
     * 上传到七牛云储存空间
     * @return
     */
    protected Response put(byte[] data, String name){
        Auth auth = Auth.create(Constants.ACCESS_KEY, Constants.SECRET_KEY);

        UploadManager uploadManager = new UploadManager();

        String token = auth.uploadToken(Constants.BUCKET_NAME);

        try{
            auth.uploadToken(Constants.BUCKET_NAME);
            Response response = uploadManager.put(data, name, token);
            return response;
        } catch(QiniuException ex){
            ex.printStackTrace();
        }
        return null;
    }

    protected void insert(String name, int userId, String path){
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setUserId(userId);
        photoDTO.setPath(path);
        photoDTO.setName(name);
        photoMapper.insert(photoDTO);
    }

    @Override
    public String upload(MultipartHttpServletRequest request, int userId) {
        Iterator<String> it = request.getFileNames();
        MultipartFile multipartFile ;
        //2.
        while(it.hasNext()){
            multipartFile = request.getFile(it.next());
            String fileName = multipartFile.getOriginalFilename();
            String fileType = fileName.split("\\.").length>1 ? fileName.split("\\.")[1] : "jpg";
            String path = CodeUtil.encode(userId+"") + "." + fileType;
            Response response = null;
            try{
                response = put(multipartFile.getBytes(), path);
            } catch(IOException ex){
                ex.printStackTrace();
            }
            if(response != null && response.isOK()){
                insert(fileName,userId,path);
                return assembleUrl(path);
            }
        }
        return null;
    }

    protected String assembleUrl(String path){
        return Constants.QINIUDOMAIN+path;
    }
}
