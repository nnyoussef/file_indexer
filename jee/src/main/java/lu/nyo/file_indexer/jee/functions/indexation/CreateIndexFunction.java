package lu.nyo.file_indexer.jee.functions.indexation;

import lu.nyo.file_indexer.jee.app.dto.request.IndexingRequest;
import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

import static java.nio.file.Files.createDirectories;

@Component
public class CreateIndexFunction extends AbstractFunction implements Function<IndexingRequest, IndexingRequest> {

    @Override
    public IndexingRequest apply(final IndexingRequest indexingRequest) {
        Path physicalPath = Path.of(applicationStorage.getBasepath(), indexingRequest.getIndexName());

        try {
            elasticSearchApi.createIndex(indexingRequest.getIndexName());
            createDirectories(physicalPath);
            Path indexDescriptionHtml = Path.of(applicationStorage.getBasepath(), indexingRequest.getIndexName().concat("_description.html"));
            indexingRequest.getDescription().transferTo(indexDescriptionHtml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return indexingRequest;
    }
}
