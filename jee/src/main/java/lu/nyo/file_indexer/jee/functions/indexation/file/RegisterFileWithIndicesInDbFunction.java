package lu.nyo.file_indexer.jee.functions.indexation.file;

import lu.nyo.file_indexer.jee.domain.bdd.entities.IndexedFile;
import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Component
public  class RegisterFileWithIndicesInDbFunction extends AbstractFunction implements Function<RegisterFileWithIndicesInDbFunction.Input, Void> {

    @Override
    public Void apply(Input input) {
        Long indexId = indexesRepository.findIndexIdByIndexName(input.requestinput.index());
        AtomicInteger index = new AtomicInteger();
        List<IndexedFile> indexedFileList = Arrays.stream(input.requestinput.files())
                .map(e -> {
                    IndexedFile indexedFile = new IndexedFile();
                    indexedFile.setFileName(input.requestinput.getFileNameWithTimeStamp(index.getAndIncrement()));
                    indexedFile.setIndexId(indexId);
                    indexedFile.setIndicesRef(input.esReference);
                    return indexedFile;
                }).toList();
        indexedFileRepository.saveAllAndFlush(indexedFileList);
        return null;
    }

    public record Input(String esReference, MoveFilesToDirectoryWithIndexNameFunction.Input requestinput) {
    }
}
