package util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by scott on 2017/3/24.
 */
public class MyCommonsMultipartResolver extends CommonsMultipartResolver {

    private String excludeUrl;

    private String includeUrl;

    public MyCommonsMultipartResolver(String excludeUrl, String includeUrl) {
        this.excludeUrl = excludeUrl;
        this.includeUrl = includeUrl;
    }

    @Override
    public boolean isMultipart(HttpServletRequest request) {
//        if(StringUtils.isEmpty(this.excludeUrl)){
//            return super.isMultipart(request);
//        } else{
//            String actualURI = request.getRequestURI().substring(0,request.getRequestURI().indexOf("."));
//            String [] urls = this.excludeUrl.split(",");
//            for(String url : urls){
//                if(actualURI.equals(url)){
//                    return false;
//                }
//            }
//            return super.isMultipart(request);
//        }

        if(StringUtils.isEmpty(this.includeUrl)){
            return false;
        } else{
          String actualURI = request.getRequestURI().substring(0,request.getRequestURI().contains(".")?request.getRequestURI().indexOf("."):request.getRequestURI().length()-1);
          String[] urls = this.includeUrl.split(",");
          for(String url : urls){
              if(actualURI.equals(url)){
                  return super.isMultipart(request);
              }
          }
          return false;
        }
    }
}
