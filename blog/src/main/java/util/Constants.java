package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by scott on 2017/3/20.
 */
public class Constants {
    public static final String ATTRIBUTE_FOLDER = "folder";
    public static final String ATTRIBUTE_LOCATION = "location";
    public static final String ATTRIBUTE_PERSON = "person";
    public static final String ATTRIBUTE_CREATE = "create";
    public static final String SAVE_PATH = "SAVE_PATH";

//  文件类型
    public static final String ARTICLE = "ARTICLE";
    public static final String IMAGE = "IMAGE";
    public static final String TIMESTAMP = "TIMESTAMP";

//  数据库表名称
    public static final String T_ARTICLE = "ARTICLE";
    public static final String T_A_COMMENT = "A_COMMENTS";

//  七牛云储存
    public static final String ACCESS_KEY = "IQpmM-5rhBcCHPm2MDhtpRCkYA8I4JzxJ4-y85R6";
    public static final String SECRET_KEY = "UB3zrs4v5p_qPGpJNqk9aYCFIvTdPJhSAbH3s7o2";
    public static final String BUCKET_NAME = "janescott";
    public static final String QINIUDOMAIN = "http://ooxxhoti3.bkt.clouddn.com/";



    //定义允许上传的文件扩展名
//    public static class ExtName{
//        public static final String IMAGE = "gif,jpg,jpeg,png,bmp";
//        public static final String FLASH = "swf,flv";
//        public static final String MEDIA = "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb";
//        public static final String File = "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2";
//    }
    public static Map<String,String> getExtName(){
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
        return extMap;
    }
    //图片扩展名
    public static final String[] PICEXTNAME = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
    //文件拓展名类别
    public static final String[] FILEEXTCATEGORY = new String[]{"image","flash","media","file"};

}
