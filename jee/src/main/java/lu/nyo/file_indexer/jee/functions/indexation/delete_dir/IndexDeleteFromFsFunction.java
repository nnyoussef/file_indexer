package lu.nyo.file_indexer.jee.functions.indexation.delete_dir;

import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

import static java.nio.file.Files.deleteIfExists;

@Service
@Lazy
public class IndexDeleteFromFsFunction extends AbstractFunction implements Function<String, Void> {

    @Override
    public Void apply(String indexName) {
        try {
            FileUtils.deleteDirectory(Path.of(applicationStorage.getBasepath(), indexName).toFile());
            deleteIfExists(Path.of(applicationStorage.getBasepath(), String.format("%s_%s", indexName, "description.html")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
