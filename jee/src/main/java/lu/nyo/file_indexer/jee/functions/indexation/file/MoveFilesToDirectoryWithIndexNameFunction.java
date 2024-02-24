package lu.nyo.file_indexer.jee.functions.indexation.file;

import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import lu.nyo.file_indexer.jee.functions.indexation.file.MoveFilesToDirectoryWithIndexNameFunction.Input;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static java.lang.String.format;
import static java.time.ZoneOffset.UTC;

@Component
public  class MoveFilesToDirectoryWithIndexNameFunction extends AbstractFunction implements Function<Input, Input> {
    @Override
    public Input apply(Input input) {
        AtomicInteger index = new AtomicInteger();
        Arrays.stream(input.files())
                .forEach(e -> {
                    try {
                        e.transferTo(Path.of(applicationStorage.getBasepath(), input.index(), input.getFileNameWithTimeStamp(index.getAndIncrement())));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
        return input;
    }

    public record Input(MultipartFile[] files, String indices, String index, Long timeStamp) {
        public Input(MultipartFile[] files, String indices, String index) {
            this(files, indices, index, LocalDateTime.now().toEpochSecond(UTC));
        }

        public String getFileNameWithTimeStamp(int index) {
            return format("%d_%s", timeStamp, files[index].getOriginalFilename());
        }
    }
}
