package service;

import com.qiniu.http.Response;

import java.util.List;

/**
 * Created by scott on 2017/4/26.
 */
public interface QiNiuService<E>{
    /**
     * 上传到七牛云储存空间
     */
    Response put(byte[]data, String name, String bucketName);

    void delete(String key, String bucketName);

    String assembleUrl(String domain,String path);

    List<E> assembleUrls(String domain, List<E> source);

    String simpleUrl(String domain, String path);

}
