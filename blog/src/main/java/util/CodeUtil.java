package util;

import framework.core.utils.DateUtils;
import framework.core.utils.StringUtils;

import java.util.Date;

/**
 * Created by scott on 2017/4/25.
 */
public class CodeUtil {
    public static String encode(String ... params){
        StringBuffer sb = new StringBuffer();
        if(params != null && params.length > 0){
            for(String param : params){
                sb.append(param).append("_");
            }
        }
        return sb.append(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + StringUtils.generateNumberSequence(5)).toString();
    }

}
