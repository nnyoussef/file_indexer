package lu.nyo.file_indexer.jee.functions.indexation.file;

import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Lazy
@Component
public  class FetchFilesByIdsFunction extends AbstractFunction implements Function<List<String>, List<String>> {

    @Override
    public List<String> apply(List<String> strings) {

        return indexedFileRepository.findFileNameByDocRef(strings);
    }
}
