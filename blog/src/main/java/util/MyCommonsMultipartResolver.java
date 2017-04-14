package util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

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
