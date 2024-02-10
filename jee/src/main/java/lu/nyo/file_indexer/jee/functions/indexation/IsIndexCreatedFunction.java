package lu.nyo.file_indexer.jee.functions.indexation;

import feign.FeignException;
import lu.nyo.file_indexer.jee.functions.AbstractInteractor;
import lu.nyo.file_indexer.jee.app.dto.request.IndexingRequest;
import lu.nyo.file_indexer.jee.app.dto.response.exceptions.DirectoryManagementException;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static lu.nyo.file_indexer.jee.app.dto.response.exceptions.DirectoryManagementException.DirectoryManagementErrors.PATH_ALREADY_CREATED;

@Component
public class IsIndexCreatedFunction extends AbstractInteractor implements Function<IndexingRequest, IndexingRequest> {

    @Override
    public IndexingRequest apply(IndexingRequest indexingRequest) {
        String indexName = indexingRequest.getIndexName();
        try {
            if (elasticSearchApi.getIndex(indexName).containsKey(indexName)) {
                throw new DirectoryManagementException(indexName).addError(PATH_ALREADY_CREATED);
            }
        } catch (FeignException.NotFound notFound) {
            //no-op
        }
        return indexingRequest;
    }
}
