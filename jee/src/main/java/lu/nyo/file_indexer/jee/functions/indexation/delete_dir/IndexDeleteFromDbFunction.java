package lu.nyo.file_indexer.jee.functions.indexation.delete_dir;

import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@Lazy
public class IndexDeleteFromDbFunction extends AbstractFunction implements Function<String, String> {

    @Transactional
    @Override
    public String apply(String indexName) {
        Long indexId = indexesRepository.findIndexIdByIndexName(indexName);
        indexesMappingRepository.deleteByIndexId(indexId.longValue());
        indexedFileRepository.deleteByIndexId(indexId.longValue());
        indexesRepository.deleteById(indexId);
        return indexName;
    }
}
