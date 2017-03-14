package dto.photo;

import java.util.Date;
import java.util.List;

/**
 * 相册信息
 * Created by scott on 2017/3/14.
 */
public class PhotoAlbumDTO {
    //相册名
    private String name;
    //相册描述
    private String description;
    //相册创建时间
    private Date date;
    //相片数
    private int count;
    //包含相片
    private List<PhotoDTO> photoDTOS;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PhotoDTO> getPhotoDTOS() {
        return photoDTOS;
    }

    public void setPhotoDTOS(List<PhotoDTO> photoDTOS) {
        this.photoDTOS = photoDTOS;
    }
}
