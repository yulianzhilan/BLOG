package service;

import dto.file.FileDTO;
import dto.file.FileMeta;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * Created by scott on 2017/3/30.
 */
public interface FileService {
    List<FileDTO> getFiles(int userId, String fileName, String fileType, Integer fileId);

    boolean saveFile(int userId, FileDTO fileDTO);

    List<FileDTO> writeFile(MultipartHttpServletRequest request, int userId);

    FileMeta loadFile(String filePath);
}
