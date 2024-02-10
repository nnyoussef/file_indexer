package nyo.lu.appdeployer.jee.service.filemanagment;

import nyo.lu.appdeployer.jee.app.configuration.ApplicationStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static java.nio.file.Files.exists;

@Service
@Profile("fs")
public class FsFileManager implements FileManager {
    @Autowired
    private ApplicationStorage applicationStorage;

    @Override
    public void createDirectory(String directory) {
        Path fullPath = Path.of(applicationStorage.getBasepath(), directory);
        if (!exists(fullPath)) {
            try {
                Files.createDirectory(fullPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void uploadFile(MultipartFile[] files, String directory) {
        Arrays.stream(files).forEach(multipartFile -> {
            Path fullPath = Path.of(applicationStorage.getBasepath(), directory, multipartFile.getOriginalFilename());
            try {
                multipartFile.transferTo(fullPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
