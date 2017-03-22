package dto;

/**
 * 用于封装验证消息
 * Created by scott on 2017/3/17.
 */

public class CallBackDTO {
    /**
     * 状态
     */
    private boolean okay;
    /**
     * 错误消息
     */
    private String errtx;

    /**
     * 错误代码
     */
    private int errcode;

    /**
     * 结果对象
     */
    private Object obj;

    public boolean isOkay() {
        return okay;
    }

    public void setOkay(boolean okay) {
        this.okay = okay;
    }

    public String getErrtx() {
        return errtx;
    }

    public void setErrtx(String errtx) {
        this.errtx = errtx;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
