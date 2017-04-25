package service.mapper;

import dto.photo.PhotoDTO;
import org.springframework.stereotype.Component;

/**
 * Created by scott on 2017/3/20.
 */
@Component("photoMapper")
public interface PhotoMapper {
    void insert(PhotoDTO photo);
}
