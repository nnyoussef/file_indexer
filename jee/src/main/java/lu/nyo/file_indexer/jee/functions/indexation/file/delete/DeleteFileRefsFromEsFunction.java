package lu.nyo.file_indexer.jee.functions.indexation.file.delete;

import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
@Lazy
public class DeleteFileRefsFromEsFunction extends AbstractFunction implements Function<Map<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> apply(Map<String, Object> input) {
        String indexName = input.get("indexName").toString();
        String esRef = input.get("indicesRef").toString();
        elasticSearchApi.deleteDoc(indexName, esRef);
        return input;
    }
}
