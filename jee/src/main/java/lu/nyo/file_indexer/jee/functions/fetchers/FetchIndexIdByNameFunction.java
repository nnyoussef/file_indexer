package lu.nyo.file_indexer.jee.functions.fetchers;

import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Lazy
public class FetchIndexIdByNameFunction extends AbstractFunction implements Function<Map<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> apply(Map<String, Object> stringObjectMap) {
        String indexName = stringObjectMap.get("indexName").toString();
        Long indexId = indexesRepository.findIndexIdByIndexName(indexName);

        HashMap<String, Object> data = new HashMap<>(stringObjectMap.size() + 1);
        data.putAll(stringObjectMap);
        data.put("indexId", indexId);

        return data;
    }
}
