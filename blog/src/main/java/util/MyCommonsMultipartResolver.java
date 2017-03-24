package util;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by scott on 2017/3/24.
 */
public class MyCommonsMultipartResolver extends CommonsMultipartResolver {

    private String excludeUrl;

    public MyCommonsMultipartResolver(String excludeUrl) {
        this.excludeUrl = excludeUrl;
    }

    @Override
    public boolean isMultipart(HttpServletRequest request) {
        if(StringUtils.isEmpty(this.excludeUrl)){
            return super.isMultipart(request);
        } else{
            String actualURI = request.getRequestURI();
            String [] urls = this.excludeUrl.split(",");
            for(String url : urls){
                if(actualURI.contains(url)){
                    return false;
                }
            }
            return super.isMultipart(request);
        }

    }
}
