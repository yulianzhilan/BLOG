package service;

import com.infoccsp.framework.core.pagination.OrderablePaginationDTO;
import com.infoccsp.framework.core.pagination.PaginationResultDTO;
import dto.CallBackDTO;
import dto.photo.PhotoDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Created by scott on 2017/3/22.
 */
public interface PhotoService {
    String upload(MultipartHttpServletRequest request, int userId);

    PaginationResultDTO<PhotoDTO> getPhotos(OrderablePaginationDTO op, int userId, int isPrivate);

    void delete(int id, int userId);

    CallBackDTO preview(String path, String order, int userId);
}
