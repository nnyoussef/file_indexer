package lu.nyo.file_indexer.jee.functions.indexation.details;

import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

@Service
@Lazy
public class GetAllMetaDataFromEsByIndexIdFileNameFunction extends AbstractFunction implements Function<Map<String, Object>, Object> {
    @Override
    public Object apply(Map<String, Object> stringObjectMap) {
        String esRef = stringObjectMap.get("indicesRef").toString();
        String indexName = stringObjectMap.get("indexName").toString();
        Map<String, Object> data = elasticSearchApi.getDoc(indexName, esRef);
        Map<String, Object> source = ((Map<String, Object>) data.get("_source"));

        return flatMap(source, "", new HashMap<>())
                .entrySet()
                .stream()
                .sorted(comparingByKey())
                .collect(toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    static Map<String, Object> flatMap(Map<String, Object> map,
                                       String key,
                                       Map<String, Object> output) {
        map.forEach((key1, value) -> {
            if (value instanceof Map) {
                if (key.equals("")) {
                    flatMap((Map<String, Object>) value, key1, output);
                } else {
                    flatMap((Map<String, Object>) value, key.concat(".").concat(key1), output);
                }

            } else {
                if (key.equals(""))
                    output.put(key1, value);
                else
                    output.put(key.concat(".").concat(key1), value);
            }
        });

        return output;
    }
}
