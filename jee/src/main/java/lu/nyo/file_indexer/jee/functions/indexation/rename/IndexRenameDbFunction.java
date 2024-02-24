package lu.nyo.file_indexer.jee.functions.indexation.rename;

import lu.nyo.file_indexer.jee.comon.ProblemResponseEntityBuilder;
import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Lazy
public class IndexRenameDbFunction extends AbstractFunction implements Function<IndexRenameEsFunction.Input, IndexRenameEsFunction.Input> {

    @Override
    public IndexRenameEsFunction.Input apply(IndexRenameEsFunction.Input input) {
        Long indexId = indexesRepository.findIndexIdByIndexName(input.getIndexName());
        Long newIndexId = indexesRepository.findIndexIdByIndexName(input.getNewIndexName());

        if (newIndexId == null) {
            indexesRepository.renameIndex(input.getNewIndexName(), indexId);
        } else {
            try {
                throw new InstantiationException("Cannot rename. Index already exists");
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        }

        return input;
    }
}
