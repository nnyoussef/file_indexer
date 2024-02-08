package nyo.lu.appdeployer.jee.functions.indexation.file;

import nyo.lu.appdeployer.jee.domain.bdd.entities.IndexedFile;
import nyo.lu.appdeployer.jee.functions.AbstractInteractor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Component
public class RegisterFileWithIndicesInDbFunction extends AbstractInteractor implements Function<RegisterFileWithIndicesInDbFunction.Input, Void> {

    @Override
    public Void apply(Input input) {
        Long indexId = indexesRepository.findIndexIdByIndexName(input.requestinput.index());

        List<IndexedFile> indexedFileList = Arrays.stream(input.requestinput.files())
                .map(e -> {
                    IndexedFile indexedFile = new IndexedFile();
                    indexedFile.setFileName(e.getOriginalFilename());
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
