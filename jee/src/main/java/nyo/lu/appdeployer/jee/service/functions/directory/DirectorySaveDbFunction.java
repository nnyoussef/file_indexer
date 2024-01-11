package nyo.lu.appdeployer.jee.service.functions.directory;

import nyo.lu.appdeployer.jee.app.dto.request.DirectoryIndexingRequest;
import nyo.lu.appdeployer.jee.domain.bdd.entities.DirectoriesEntity;
import nyo.lu.appdeployer.jee.service.exceptions.DirectoryManagementException;
import nyo.lu.appdeployer.jee.service.functions.AbstractInteractor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.function.Function;

import static java.nio.file.Files.createDirectories;
import static java.time.LocalDateTime.now;
import static nyo.lu.appdeployer.jee.service.exceptions.DirectoryManagementException.DirectoryManagementErrors.PATH_ALREADY_CREATED;

@Component
public class DirectorySaveDbFunction extends AbstractInteractor implements Function<DirectoryIndexingRequest, Entry<Long, DirectoryIndexingRequest>> {

    public Entry<Long, DirectoryIndexingRequest> apply(DirectoryIndexingRequest indexingRequest) {
        DirectoryManagementException errors = new DirectoryManagementException(indexingRequest.getPath());
        Path physicalPath = Path.of(applicationStorage.getBasepath(), indexingRequest.getPath());
        Path relative = Path.of(indexingRequest.getPath());
        DirectoriesEntity directoriesEntity = new DirectoriesEntity();
        Long directoryId;
        try {
            directoriesEntity.setCreated(now());
            directoriesEntity.setModified(now());
            directoriesEntity.setDirPath(relative.toString());
            directoryId = directoryRepository.save(directoriesEntity).getDirId();
            createDirectories(physicalPath);
        } catch (DataAccessException | IOException e) {
            errors.addError(PATH_ALREADY_CREATED);
            throw errors;
        }
        return new SimpleEntry<>(directoryId, indexingRequest);
    }
}
