package lu.nyo.file_indexer.jee.functions.indexation.delete_dir;

import feign.FeignException;
import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Lazy
public class IndexDeleteFromEsFunction extends AbstractFunction implements Function<String, String> {

    @Override
    public String apply(String indexName) {
        try {
            elasticSearchApi.deleteIndex(indexName);
        } catch (FeignException.NotFound ignored) {
        }
        return indexName;
    }
}
