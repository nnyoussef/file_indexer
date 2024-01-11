package nyo.lu.appdeployer.jee.service.functions;

import nyo.lu.appdeployer.jee.app.configuration.ApplicationStorage;
import nyo.lu.appdeployer.jee.domain.bdd.repository.DirectoriesLabelsRepository;
import nyo.lu.appdeployer.jee.domain.bdd.repository.DirectoryRepository;
import nyo.lu.appdeployer.jee.domain.bdd.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Lazy
@Validated
public class AbstractInteractor {

    @Autowired
    protected ApplicationStorage applicationStorage;

    @Autowired
    protected DirectoryRepository directoryRepository;

    @Autowired
    protected DirectoriesLabelsRepository directoriesLabelsRepository;

    @Autowired
    protected FileRepository fileRepository;

}
