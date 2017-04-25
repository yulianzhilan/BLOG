package service.mapper;

import dto.photo.PhotoDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by scott on 2017/3/20.
 */
@Component("photoMapper")
public interface PhotoMapper {
    void insert(PhotoDTO photo);

    List<PhotoDTO> getPhotos(@Param("userId") int userId, @Param("isPrivate") int isPrivate);
}
