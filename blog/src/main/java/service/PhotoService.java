package service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Created by scott on 2017/3/22.
 */
public interface PhotoService {
    String upload(MultipartHttpServletRequest request, int userId);



}
