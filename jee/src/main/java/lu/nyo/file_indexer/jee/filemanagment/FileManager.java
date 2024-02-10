package nyo.lu.appdeployer.jee.service.filemanagment;

import org.springframework.web.multipart.MultipartFile;

public interface FileManager {

    void createDirectory(String directory);

    void uploadFile(MultipartFile[] files, String directory);
}
