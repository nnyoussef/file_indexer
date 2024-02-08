package nyo.lu.appdeployer.jee.functions.indexation.file;

import nyo.lu.appdeployer.jee.functions.AbstractInteractor;
import nyo.lu.appdeployer.jee.functions.indexation.file.MoveFilesToDirectoryWithIndexNameFunction.Input;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.function.Function;

import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;


@Component
public class MoveFilesToDirectoryWithIndexNameFunction extends AbstractInteractor implements Function<Input, Input> {
    @Override
    public Input apply(Input input) {
        Arrays.stream(input.files()).forEach(e -> {
            try {
                e.transferTo(Path.of(applicationStorage.getBasepath(), input.index(), String.format("%s_%s", now().toEpochSecond(UTC), e.getOriginalFilename())));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return input;
    }

    public record Input(MultipartFile[] files, String indices, String index) {
    }
}
