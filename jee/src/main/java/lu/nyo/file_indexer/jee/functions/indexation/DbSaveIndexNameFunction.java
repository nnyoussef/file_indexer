package nyo.lu.appdeployer.jee.functions.indexation;

import nyo.lu.appdeployer.jee.app.dto.request.IndexingRequest;
import nyo.lu.appdeployer.jee.domain.bdd.entities.IndexesEntity;
import nyo.lu.appdeployer.jee.functions.AbstractInteractor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public class DbSaveIndexNameFunction extends AbstractInteractor implements Function<IndexingRequest, DbSaveIndexMappingsFunction.Input> {

    @Override
    public DbSaveIndexMappingsFunction.Input apply(IndexingRequest indexingRequest) {

        IndexesEntity indexesEntity = new IndexesEntity();
        indexesEntity.setIndexName(indexingRequest.getIndexName());
        indexesEntity.setCreated(LocalDateTime.now());
        indexesEntity.setModified(LocalDateTime.now());
        Long id = indexesRepository.save(indexesEntity).getIndexId();

        DbSaveIndexMappingsFunction.Input input = new DbSaveIndexMappingsFunction.Input();
        input.setIndexDbId(id);
        input.setIndexes(indexingRequest.getMappings());
        return input;
    }
}
