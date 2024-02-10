package lu.nyo.file_indexer.jee.filemanagment;

import org.springframework.web.multipart.MultipartFile;

public interface FileManager {

    void createDirectory(String directory);

    void uploadFile(MultipartFile[] files, String directory);
}
