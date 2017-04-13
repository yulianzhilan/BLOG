package service.mapper;

import dto.file.FileDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by scott on 2017/3/30.
 */
@Component("fileMapper")
public interface FileMapper {
    List<FileDTO> getFiles(@Param("userId") int userId,@Param("fileName") String fileName,@Param("fileType") String fileType,@Param("fileId") Integer fileId);

    int saveFile(FileDTO fileDTO);
}
