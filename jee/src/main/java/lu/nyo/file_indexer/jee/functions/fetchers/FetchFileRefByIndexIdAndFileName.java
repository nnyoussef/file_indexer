package lu.nyo.file_indexer.jee.functions.fetchers;

import lu.nyo.file_indexer.jee.domain.bdd.entities.IndexedFile;
import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Lazy
@Service
public class FetchFileRefByIndexIdAndFileName extends AbstractFunction implements Function<Map<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> apply(Map<String, Object> input) {
        Long indexId = ((Long) input.get("indexId"));
        String fileName = input.get("fileName").toString();
        IndexedFile indexedFile = indexedFileRepository.findIndexedFileByIndexIdAndFileName(indexId, fileName);
        HashMap<String, Object> data = new HashMap<>(input);
        data.put("indicesRef", indexedFile.getIndicesRef());
        return data;
    }
}
