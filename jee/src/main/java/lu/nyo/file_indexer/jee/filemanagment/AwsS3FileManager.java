package nyo.lu.appdeployer.jee.service.filemanagment;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Profile("aws")
public class AwsS3FileManager implements FileManager {
    @Override
    public void createDirectory(String directory) {

    }

    @Override
    public void uploadFile(MultipartFile[] files,String directory) {

    }
}
