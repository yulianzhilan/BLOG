package service.impl;

import com.qiniu.http.Response;
import dto.article.ArticleDTO;
import dto.file.FileDTO;
import dto.file.FileMeta;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.ConfigService;
import service.FileService;
import service.QiNiuService;
import service.mapper.FileMapper;
import util.CodeUtil;
import util.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by scott on 2017/3/30.
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    private ConfigService configService;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private QiNiuService qiNiuService;

    @Override
    public List<FileDTO> getFiles(int userId, String fileName, String fileType, Integer fileId) {
        return fileMapper.getFiles(userId, fileName, fileType, fileId);
    }

    @Override
    public boolean saveFile(int userId, FileDTO fileDTO) {
        fileDTO.setFileId(userId);
        int id = fileMapper.saveFile(fileDTO);
        List result = fileMapper.getFiles(userId, null, null, id);
        return !(result == null || result.size() == 0);
    }

    @Override
    public List<FileDTO> writeFile(MultipartHttpServletRequest request, int userId) {
        Iterator<String> it = request.getFileNames();
        MultipartFile multipartFile ;
        FileMeta fileMeta;
        List<FileDTO> result = new ArrayList<>();
        //2.
        while(it.hasNext()){
            //2.1
            multipartFile = request.getFile(it.next());

            //2.2
//            if(files.size() >= 10){
//                files.pop();
//            }

            fileMeta = new FileMeta();

            String filename = multipartFile.getOriginalFilename();
            fileMeta.setFileName(filename);
            fileMeta.setFileSize(multipartFile.getSize()/1024+" Kb");
            fileMeta.setFileType(multipartFile.getContentType());
            String folderPath = configService.getConfig(Constants.SAVE_PATH)+userId+File.separator+"file";
            File folder = new File(folderPath);
            if(!folder.exists()){
                folder.mkdirs();
            }
            String filePath = folderPath + File.separator+filename;
            try{
                fileMeta.setBytes(multipartFile.getBytes());
                FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(filePath));
            } catch(IOException e){
                e.printStackTrace();
            }
            FileDTO file = new FileDTO();
            file.setFileName(fileMeta.getFileName());
            file.setFilePath(filePath);
            file.setFileSize(fileMeta.getFileSize());
            file.setFileType(filename.split("\\.").length>1 ? filename.split("\\.")[1] : null);
            file.setUserId(userId);
            result.add(file);
        }
        return result;
    }

    @Override
    public FileMeta loadFile(String filePath) {
        FileMeta fileMeta = new FileMeta();
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()){
            return null;
        }
        fileMeta.setFileName(file.getName());
        fileMeta.setFileSize(file.length()/1024 + "Kb");
        fileMeta.setFileType(file.getName().split("\\.").length>1 ? file.getName().split("\\.")[1] : null);
        try {
            fileMeta.setBytes(FileCopyUtils.copyToByteArray(new FileInputStream(file)));
        } catch(IOException e){
            e.printStackTrace();
        }
        return fileMeta;
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
            String fileType = fileName.split("\\.").length>1 ? fileName.substring(fileName.lastIndexOf(".")+1) : "unknown";
            String path = CodeUtil.encode(userId+"") + "." + fileType;
            Response response = null;
            try{
                response = qiNiuService.put(multipartFile.getBytes(), path, Constants.BUCKET_NAME_FILE);
            } catch(IOException ex){
                ex.printStackTrace();
            }
            if(response != null && response.isOK()){
                insert(fileName,userId,path,size,fileType);
                return qiNiuService.assembleUrl(Constants.QINIUDOMAIN_FILE, path);
            }
        }
        return null;
    }

    protected void insert(String name, int userId, String path, long size, String type){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setUserId(userId);
        fileDTO.setFilePath(path);
        fileDTO.setFileName(name);
        fileDTO.setFileSize(size+"");
        fileDTO.setFileType(type);
        fileMapper.saveFile(fileDTO);
    }
}
