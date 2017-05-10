package service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import dto.file.FileDTO;
import dto.photo.PhotoDTO;
import framework.core.utils.StringUtils;
import org.springframework.stereotype.Service;
import service.QiNiuService;
import util.Constants;

import java.util.List;

/**
 * Created by scott on 2017/4/26.
 */
@Service("qiNiuService")
public class QiNiuServiceImpl implements QiNiuService{
    private Auth auth = Auth.create(Constants.ACCESS_KEY, Constants.SECRET_KEY);

    @Override
    public Response put(byte[] data, String name, String bucketName) {
        UploadManager uploadManager = new UploadManager();
        String token = auth.uploadToken(bucketName);

        try{
            auth.uploadToken(bucketName);
            return uploadManager.put(data, name, token);

        } catch(QiniuException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String key, String bucketName) {
        BucketManager bucketManager = new BucketManager(auth);
        try {
            bucketManager.delete(bucketName, key);
        } catch(QiniuException e){
            e.printStackTrace();
        }
    }

    @Override
    public String assembleUrl(String domain, String path) {
        return domain+path;
    }

    @Override
    public List assembleUrls(String domain, List source) {
        for(Object object : source){
            if(object instanceof PhotoDTO){
                PhotoDTO photoDTO = (PhotoDTO)object;
                photoDTO.setPath(assembleUrl(domain, photoDTO.getPath()));
            }else if(object instanceof FileDTO){
                FileDTO fileDTO = (FileDTO) object;
                fileDTO.setFilePath(assembleUrl(domain, fileDTO.getFilePath()));
            }
        }
        return source;
    }

    @Override
    public String simpleUrl(String domain, String path) {
        if(StringUtils.isEmpty(domain) || StringUtils.isEmpty(path) || !path.contains(domain)){
            return null;
        }
        return path.split(domain)[1];
    }
}
