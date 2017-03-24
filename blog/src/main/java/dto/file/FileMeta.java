package dto.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by scott on 2017/3/23.
 */
@JsonIgnoreProperties({"bytes"})
public class FileMeta {
    private String fileName;

    private String fileSize;

    private String fileType;

    private byte [] bytes;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
