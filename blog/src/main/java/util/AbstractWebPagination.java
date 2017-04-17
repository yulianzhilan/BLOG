package util;

/**
 * Created by scott on 2017/4/17.
 */

import framework.core.utils.HttpRequestUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


public abstract class AbstractWebPagination extends OrderablePagination {

    private static final long serialVersionUID = -8027898038440898955L;

    public static String START_NAME = "___start";	//参数中起始记录数的名称

    public static String PAGE_NAME = "___page";	//参数中起始页码

    public static String PAGER_ATTRIBUTE = "framework.framework.web.pagination.support.pager";

    public static String TOTAL_COUNT_ATTRIBUTE = "___total_count";

    /**
     * url： 为绝对路径或者相对路径，目前pagination不对url进行合法性判断和关键字过滤，建议使用绝对路径。
     */
    protected String url;

    /**
     * url后的参数
     */
    protected String param;

    public AbstractWebPagination(String url,
                                 String param,
                                 int totalCount,
                                 int startIndex,
                                 int pageSize) {
        super(totalCount, startIndex, pageSize);
        setUrl(url);
        setParam(param);
    }

    public String getParam() {
        return param;
    }

    protected void setParam(String param) {
        this.param = param;
    }

    public String getUrl() {
        return url;
    }

    protected void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlPageToolbar(Locale locale) {
        return getHtmlPageToolbar(locale, null);
    }

    public abstract String getHtmlPageToolbar(Locale locale, Map<String,Object> additionalParams);

    public String getHtmlPageToolbar() {
        return getHtmlPageToolbar(Locale.getDefault());
    }

    /**
     * 根据页码获得开始技术数
     * @param pageIndex
     * @return
     */
    protected int getStartIndexByPageIndex(int pageIndex) {
        return this.pageSize * (pageIndex - 1);
    }

    protected String cleanParam(Map<String,Object> additionalParams, int totalCount) {
        if(StringUtils.isEmpty(this.param)) {
            return "";
        }
        Map<String, Object> paramMap = new TreeMap<String, Object>(HttpRequestUtils.parseRequestParameters(param));
        paramMap.remove(START_NAME);
        if(totalCount > 0) {
            paramMap.put(TOTAL_COUNT_ATTRIBUTE, totalCount);
        }
        if(!CollectionUtils.isEmpty(additionalParams)) paramMap.putAll(additionalParams);
        return HttpRequestUtils.generateQueryString(paramMap);
    }


    public static class PageIndexRange {

        public PageIndexRange(int start, int end) {
            this.start = start;
            this.end = end;
        }

        private int start;
        private int end;

        public int getEnd() {
            return end;
        }
        public int getStart() {
            return start;
        }
    }
}