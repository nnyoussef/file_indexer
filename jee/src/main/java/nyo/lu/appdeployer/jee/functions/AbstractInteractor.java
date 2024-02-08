package nyo.lu.appdeployer.jee.functions;

import com.fasterxml.jackson.databind.ObjectMapper;
import nyo.lu.appdeployer.jee.app.configuration.ApplicationStorage;
import nyo.lu.appdeployer.jee.domain.bdd.repository.*;
import nyo.lu.appdeployer.jee.domain.rest.ElasticSearchApi;
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
    protected ElasticSearchApi elasticSearchApi;

    @Autowired
    protected IndexesRepository indexesRepository;

    @Autowired
    protected IndexesMappingRepository indexesMappingRepository;

    @Autowired
    protected IndexedFileRepository indexedFileRepository;
    protected final ObjectMapper objectMapper = new ObjectMapper();
}
