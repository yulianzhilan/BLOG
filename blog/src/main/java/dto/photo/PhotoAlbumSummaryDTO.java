package dto.photo;

import java.util.List;

/**
 * 相册集
 * Created by scott on 2017/3/14.
 */
public class PhotoAlbumSummaryDTO {
    //名称
    private String name;
    //相册数
    private int count;
    //是否公开
    private boolean isPublic;
    //相册
    private List<PhotoAlbumDTO> photoAlbumDTOs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<PhotoAlbumDTO> getPhotoAlbumDTOs() {
        return photoAlbumDTOs;
    }

    public void setPhotoAlbumDTOs(List<PhotoAlbumDTO> photoAlbumDTOs) {
        this.photoAlbumDTOs = photoAlbumDTOs;
    }
}
