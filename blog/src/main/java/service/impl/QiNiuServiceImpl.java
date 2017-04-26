package service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import dto.photo.PhotoDTO;
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
    public Response put(byte[] data, String name) {
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

    @Override
    public void delete(String key) {
        BucketManager bucketManager = new BucketManager(auth);
        try {
            bucketManager.delete(Constants.BUCKET_NAME, key);
        } catch(QiniuException e){
            e.printStackTrace();
        }
    }

    @Override
    public String assembleUrl(String path) {
        return Constants.QINIUDOMAIN+path;
    }

    @Override
    public List assembleUrls(List source) {
        for(Object object : source){
            if(object instanceof PhotoDTO){
                PhotoDTO photoDTO = (PhotoDTO)object;
                photoDTO.setPath(assembleUrl(photoDTO.getPath()));
            }
        }
        return source;
    }
}
