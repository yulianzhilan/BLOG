package service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.infoccsp.framework.core.pagination.OrderablePaginationDTO;
import com.infoccsp.framework.core.pagination.PaginationResultDTO;
import com.qiniu.http.Response;
import dto.CallBackDTO;
import dto.photo.PhotoDTO;
import framework.core.utils.CollectionUtils;
import framework.core.utils.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.PhotoService;
import service.mapper.PhotoMapper;
import service.QiNiuService;
import util.CodeUtil;
import util.ComparatorUtil;
import util.Constants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by scott on 2017/3/22.
 */
@Service("photoService")
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private QiNiuService qiNiuService;

    protected void insert(String name, int userId, String path, long size, String type){
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setUserId(userId);
        photoDTO.setPath(path);
        photoDTO.setName(name);
        photoDTO.setSize(size);
        photoDTO.setType(type);
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
            long size = multipartFile.getSize();
            String fileType = fileName.split("\\.").length>1 ? fileName.substring(fileName.lastIndexOf(".")+1) : "jpg";
            String path = CodeUtil.encode(userId+"") + "." + fileType;
            Response response = null;
            try{
                response = qiNiuService.put(multipartFile.getBytes(), path);
            } catch(IOException ex){
                ex.printStackTrace();
            }
            if(response != null && response.isOK()){
                insert(fileName,userId,path,size,fileType);
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

    @Override
    public CallBackDTO preview(String path, String order, int userId){
        CallBackDTO result = new CallBackDTO();
        List<PhotoDTO> photos = photoMapper.kindEditGet(path, order, userId);
        if(CollectionUtils.isEmpty(photos)){
            result.setOkay(false);
            return result;
        }
        List<Hashtable> fileList = classify(photos,StringUtils.isEmpty(path));
        if("name".equalsIgnoreCase(order)){
            Collections.sort(fileList, new ComparatorUtil.NameComparator());
        } else if("type".equalsIgnoreCase(order)){
            Collections.sort(fileList, new ComparatorUtil.TypeComparator());
        } else{
            Collections.sort(fileList,new ComparatorUtil.SizeComparator());
        }

        JSONObject obj = new JSONObject();
        obj.put("moveup_dir_path", path);
        obj.put("current_dir_path", path);
        obj.put("current_url", path);
        obj.put("total_count", fileList.size());
        obj.put("file_list", fileList);

        result.setOkay(true);
        result.setObj(obj);
        return result;
    }

    protected List<Hashtable> classify(List<PhotoDTO> source,boolean isFolder){
        List<Hashtable> result = new ArrayList<>();
        if(isFolder) {
            Hashtable<String, List<PhotoDTO>> hash = new Hashtable<>();
            for (PhotoDTO photoDTO : source) {
                if (hash.containsKey(photoDTO.getFolder())) {
                    List photos = hash.get(photoDTO.getFolder());
                    photos.add(photoDTO);
                } else {
                    List<PhotoDTO> photos = new ArrayList<>();
                    photos.add(photoDTO);
                    hash.put(photoDTO.getFolder(), photos);
                }
            }
            Iterator<Map.Entry<String, List<PhotoDTO>>> it = hash.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, List<PhotoDTO>> next = it.next();
                Hashtable<String, Object> hs = new Hashtable<>();
                hs.put("is_dir", true);
                hs.put("has_file",next.getValue().size()>0);
                hs.put("filesize", countSize(next.getValue()));
                hs.put("is_photo", false);
                hs.put("filetype", "");
                hs.put("filename", next.getKey());
                hs.put("datetime", "");
                result.add(hs);
            }
        } else{
            for(PhotoDTO photoDTO : source){
                Hashtable<String, Object> hash = new Hashtable<>();
                hash.put("is_dir", false);
                hash.put("has_file", false);
                hash.put("filesize", photoDTO.getSize());
                hash.put("is_photo", Arrays.<String>asList(Constants.PICEXTNAME).contains(photoDTO.getType()));
                hash.put("filetype", photoDTO.getType());
                hash.put("filename", photoDTO.getName());
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(photoDTO.getCreated()));
                result.add(hash);
            }
        }
        return result;
    }

    protected long countSize(List<PhotoDTO> photoDTOS){
        long size = 0;
        for(PhotoDTO photoDTO : photoDTOS){
            size += photoDTO.getSize();
        }
        return size;
    }
}
