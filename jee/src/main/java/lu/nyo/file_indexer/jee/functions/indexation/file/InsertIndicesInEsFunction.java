package lu.nyo.file_indexer.jee.functions.indexation.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import lu.nyo.file_indexer.jee.functions.indexation.file.MoveFilesToDirectoryWithIndexNameFunction.Input;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Lazy
public  class InsertIndicesInEsFunction extends AbstractFunction implements Function<Input, RegisterFileWithIndicesInDbFunction.Input> {

    @Override
    public RegisterFileWithIndicesInDbFunction.Input apply(Input input) {
        String esRef;
        try {
            Map<String, Object> indices = objectMapper.readValue(input.indices(), Map.class);
            Map<String, Object> data = new HashMap<>(indices.size());

            indices.forEach((key, value) -> {
                Map<String, Object> lastVisitedObjectMap = data;
                String lastCheckedKey = null;
                String[] keyComposition = key.split("[.]");

                for (int i = 0; i < keyComposition.length; i++) {
                    lastCheckedKey = keyComposition[i];

                    if (i == keyComposition.length - 1) {
                    } else if (lastVisitedObjectMap.containsKey(lastCheckedKey)) {
                        lastVisitedObjectMap = ((Map) lastVisitedObjectMap.get(lastCheckedKey));
                    } else {
                        Map<String, Object> node = new HashMap<>(20);
                        lastVisitedObjectMap.put(lastCheckedKey, node);
                        lastVisitedObjectMap = node;
                    }
                }

                lastVisitedObjectMap.put(lastCheckedKey, value);
            });
            esRef = ((String) elasticSearchApi.indexDocument(input.index(), data).get("_id"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new RegisterFileWithIndicesInDbFunction.Input(esRef, input);
    }
}
