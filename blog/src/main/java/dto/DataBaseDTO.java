package dto;

import java.util.Date;

/**
 * Created by scott on 2017/3/27.
 */
public class DataBaseDTO {

    private String dictName;

    private String dictCode;

    private String key1;

    private int key2;

    private Date key3;

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public int getKey2() {
        return key2;
    }

    public void setKey2(int key2) {
        this.key2 = key2;
    }

    public Date getKey3() {
        return key3;
    }

    public void setKey3(Date key3) {
        this.key3 = key3;
    }
}
