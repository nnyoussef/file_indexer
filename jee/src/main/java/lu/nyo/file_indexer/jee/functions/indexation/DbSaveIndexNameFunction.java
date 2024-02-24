package lu.nyo.file_indexer.jee.functions.indexation;

import lu.nyo.file_indexer.jee.app.dto.request.IndexingRequest;
import lu.nyo.file_indexer.jee.domain.bdd.entities.IndexesEntity;
import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public  class DbSaveIndexNameFunction extends AbstractFunction implements Function<IndexingRequest, DbSaveIndexMappingsFunction.Input> {

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
