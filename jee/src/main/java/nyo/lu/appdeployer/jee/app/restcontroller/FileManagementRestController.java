package nyo.lu.appdeployer.jee.app.restcontroller;

import nyo.lu.appdeployer.jee.app.dto.request.FileIndexingRequest;
import nyo.lu.appdeployer.jee.app.validators.annotation.ZipTypeValidation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/filemanagment")
public class FileManagementRestController extends BaseRestController {


    @PostMapping(value = "/indexing/zip")
    public Map<String, Object> uploadZip(@ZipTypeValidation @RequestPart("zip") MultipartFile zip,
                                         @RequestPart("metadata") FileIndexingRequest tags) {
        System.out.println(applicationStorage.getBasepath());
        return Map.of("data", tags);
    }
}
