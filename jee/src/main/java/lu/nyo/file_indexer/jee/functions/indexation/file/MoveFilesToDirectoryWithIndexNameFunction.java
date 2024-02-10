package lu.nyo.file_indexer.jee.functions.indexation.file;

import lu.nyo.file_indexer.jee.functions.AbstractInteractor;
import lu.nyo.file_indexer.jee.functions.indexation.file.MoveFilesToDirectoryWithIndexNameFunction.Input;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Function;


@Component
public class MoveFilesToDirectoryWithIndexNameFunction extends AbstractInteractor implements Function<Input, Input> {
    @Override
    public Input apply(Input input) {
        Arrays.stream(input.files())
                .forEach(e -> {
                    try {
                        e.transferTo(Path.of(applicationStorage.getBasepath(), input.index(), e.getOriginalFilename()));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
        return input;
    }

    public record Input(MultipartFile[] files, String indices, String index) {
    }
}
