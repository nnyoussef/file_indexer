package lu.nyo.file_indexer.jee.functions.indexation.file.delete;

import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;

@Service
@Lazy
public class DeleteFileRefFromfSFunction extends AbstractFunction implements Function<Map<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> apply(Map<String, Object> input) {
        String indexName = input.get("indexName").toString();
        String fileName = input.get("fileName").toString();
        try {
            Files.delete(Path.of(applicationStorage.getBasepath(), indexName, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return input;
    }
}
