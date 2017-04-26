package service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.infoccsp.framework.core.pagination.OrderablePaginationDTO;
import com.infoccsp.framework.core.pagination.PaginationResultDTO;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
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
             return uploadManager.put(data, name, token);

        } catch(QiniuException ex){
            ex.printStackTrace();
        }
        return null;
    }

    protected void delete(String key){
        Auth auth = Auth.create(Constants.ACCESS_KEY, Constants.SECRET_KEY);

        BucketManager bucketManager = new BucketManager(auth);
        try {
            bucketManager.delete(Constants.BUCKET_NAME, key);
        } catch(QiniuException e){
            e.printStackTrace();
        }

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
            String fileType = fileName.split("\\.").length>1 ? fileName.substring(fileName.lastIndexOf(".")) : "jpg";
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

    @Override
    public PaginationResultDTO<PhotoDTO> getPhotos(OrderablePaginationDTO op, int userId, int isPrivate) {
        Page<PhotoDTO> page = PageHelper.startPage(op.getPage(), op.getSize()).doSelectPage(() -> assembleUrls(photoMapper.getPhotos(userId, isPrivate)));
        op.setTotalCount((int)page.getTotal());
        return new PaginationResultDTO<>(op, page.getResult());
    }

    protected String assembleUrl(String path){
        return Constants.QINIUDOMAIN+path;
    }

    protected List<PhotoDTO> assembleUrls(List<PhotoDTO> source){
        for(PhotoDTO photoDTO : source){
            photoDTO.setPath(assembleUrl(photoDTO.getPath()));
        }
        return source;
    }

    @Override
    public void delete(int id, int userId) {
        String key = photoMapper.getQiNiuKey(id, userId);
        delete(key);
        photoMapper.delete(id, userId);
    }
}
