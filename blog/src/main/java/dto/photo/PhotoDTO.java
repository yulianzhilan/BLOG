package dto.photo;

import java.util.Date;

/**
 * 相片信息
 * Created by scott on 2017/3/14.
 */
public class PhotoDTO {
    //创建时间
    private Date date;
    //名称
    private String name;
    //地点
    private String location;
    //描述
    private String description;
    //路径
    private String url;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
