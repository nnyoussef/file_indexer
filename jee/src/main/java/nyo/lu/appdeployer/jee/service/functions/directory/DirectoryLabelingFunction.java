package nyo.lu.appdeployer.jee.service.functions.directory;

import nyo.lu.appdeployer.jee.app.dto.request.DirectoryIndexingRequest;
import nyo.lu.appdeployer.jee.domain.bdd.entities.DirectoriesLabelsEntity;
import nyo.lu.appdeployer.jee.domain.bdd.repository.DirectoriesLabelsRepository;
import nyo.lu.appdeployer.jee.service.exceptions.DirectoryManagementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;

import static nyo.lu.appdeployer.jee.service.exceptions.DirectoryManagementException.DirectoryManagementErrors.INDEXING_UNKOWN_ISSUE;

@Component
public class DirectoryLabelingFunction implements Function<Entry<Long, DirectoryIndexingRequest>, Long> {

    @Autowired
    private DirectoriesLabelsRepository directoriesLabelsRepository;

    @Override
    public Long apply(Entry<Long, DirectoryIndexingRequest> dirIdDirectoryIndexingRequestEntry) {
        Long dirId = dirIdDirectoryIndexingRequestEntry.getKey();
        DirectoryIndexingRequest directoryIndexingRequest = dirIdDirectoryIndexingRequestEntry.getValue();
        DirectoryManagementException errors = new DirectoryManagementException(directoryIndexingRequest.getPath());

        try {
            List<DirectoriesLabelsEntity> directoriesLabelsEntityList = directoryIndexingRequest.getLabels()
                    .stream()
                    .map(label -> {
                        DirectoriesLabelsEntity directoriesLabelsEntity = new DirectoriesLabelsEntity();
                        directoriesLabelsEntity.setDirId(dirId);
                        directoriesLabelsEntity.setLabel(label.get("key"));
                        directoriesLabelsEntity.setLabelValue(label.get("value"));
                        return directoriesLabelsEntity;
                    }).toList();
            directoriesLabelsRepository.saveAll(directoriesLabelsEntityList);
        } catch (DataAccessException dataAccessException) {
            errors.addError(INDEXING_UNKOWN_ISSUE);
            throw errors;
        }
        return null;
    }
}
