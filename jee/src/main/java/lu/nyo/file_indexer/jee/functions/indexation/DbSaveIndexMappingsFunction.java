package lu.nyo.file_indexer.jee.functions.indexation;

import lu.nyo.file_indexer.jee.domain.bdd.entities.IndexesMappingEntity;
import lu.nyo.file_indexer.jee.functions.AbstractInteractor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class DbSaveIndexMappingsFunction extends AbstractInteractor implements Function<DbSaveIndexMappingsFunction.Input, Void> {

    @Override
    public Void apply(Input indexingRequest) {
        indexingRequest.getIndexes().add("id");
        List<IndexesMappingEntity> indexesMappingEntities = indexingRequest.getIndexes()
                .stream()
                .map(in -> {
                    IndexesMappingEntity indexesMappingEntity = new IndexesMappingEntity();
                    IndexesMappingEntity.Id id = new IndexesMappingEntity.Id();
                    id.setIndexId(indexingRequest.indexDbId);
                    id.setMappingName(in);
                    indexesMappingEntity.setId(id);
                    return indexesMappingEntity;
                }).toList();
        indexesMappingRepository.saveAllAndFlush(indexesMappingEntities);
        return null;
    }

    public static class Input {
        private Long indexDbId;

        private List<String> indexes;

        public Long getIndexDbId() {
            return indexDbId;
        }

        public void setIndexDbId(Long indexDbId) {
            this.indexDbId = indexDbId;
        }

        public List<String> getIndexes() {
            return indexes;
        }

        public void setIndexes(List<String> indexes) {
            this.indexes = indexes;
        }
    }
}
