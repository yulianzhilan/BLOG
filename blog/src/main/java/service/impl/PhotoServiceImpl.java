package service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.infoccsp.framework.core.pagination.OrderablePaginationDTO;
import com.infoccsp.framework.core.pagination.PaginationResultDTO;
import com.qiniu.http.Response;
import dto.photo.PhotoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.PhotoService;
import service.mapper.PhotoMapper;
import service.QiNiuService;
import util.CodeUtil;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by scott on 2017/3/22.
 */
@Service("photoService")
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private QiNiuService qiNiuService;

    protected void insert(String name, int userId, String path){
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setUserId(userId);
        photoDTO.setPath(path);
        photoDTO.setName(name);
        photoMapper.insert(photoDTO);
    }

    @Override
    public String upload(MultipartHttpServletRequest request, int userId) {
        Iterator<String> it = request.getFileNames();
        MultipartFile multipartFile ;
        //2.
        while(it.hasNext()){
            multipartFile = request.getFile(it.next());
            String fileName = multipartFile.getOriginalFilename();
            String fileType = fileName.split("\\.").length>1 ? fileName.substring(fileName.lastIndexOf(".")) : "jpg";
            String path = CodeUtil.encode(userId+"") + "." + fileType;
            Response response = null;
            try{
                response = qiNiuService.put(multipartFile.getBytes(), path);
            } catch(IOException ex){
                ex.printStackTrace();
            }
            if(response != null && response.isOK()){
                insert(fileName,userId,path);
                return qiNiuService.assembleUrl(path);
            }
        }
        return null;
    }

    @Override
    public PaginationResultDTO<PhotoDTO> getPhotos(OrderablePaginationDTO op, int userId, int isPrivate) {
        Page<PhotoDTO> page = PageHelper.startPage(op.getPage(), op.getSize()).doSelectPage(() -> qiNiuService.assembleUrls(photoMapper.getPhotos(userId, isPrivate)));
        op.setTotalCount((int)page.getTotal());
        return new PaginationResultDTO<>(op, page.getResult());
    }

    @Override
    public void delete(int id, int userId) {
        String key = photoMapper.getQiNiuKey(id, userId);
        qiNiuService.delete(key);
        photoMapper.delete(id, userId);
    }
}
