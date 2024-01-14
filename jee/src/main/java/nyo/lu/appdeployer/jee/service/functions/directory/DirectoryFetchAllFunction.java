package nyo.lu.appdeployer.jee.service.functions.directory;

import nyo.lu.appdeployer.jee.app.dto.response.DirectoryTree;
import nyo.lu.appdeployer.jee.service.functions.AbstractInteractor;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

@Component
public class DirectoryFetchAllFunction extends AbstractInteractor implements Function<Void, DirectoryTree> {
    private static final String LABEL = "LABEL";


    @Override
    public DirectoryTree apply(Void unused) {
        DirectoryTree directoryTree = new DirectoryTree();
        directoryRepository.getAllInfoForAllDirectories().forEach(data -> {
            final DirectoryTree[] currentDirectoryTree = {directoryTree};
            String directory = data.get(LABEL).toString().replace(applicationStorage.getBasepath(), "");
            LocalDateTime created = ((Timestamp) data.get("CREATED")).toLocalDateTime();
            LocalDateTime update = ((Timestamp) data.get("MODIFIED")).toLocalDateTime();

            Paths.get(directory).iterator().forEachRemaining(p -> {
                currentDirectoryTree[0] = currentDirectoryTree[0].getSubTree().computeIfAbsent(p.toString(), k -> new DirectoryTree());
            });

            Optional.ofNullable(data.get("LABEL")).ifPresent(label -> {
                String labelValue = Optional.ofNullable(data.get("LABEL_VALUE")).map(Object::toString).orElse("");
                String labelKey = label.toString();
                currentDirectoryTree[0].getLabels().put(labelKey, labelValue);
            });

            currentDirectoryTree[0].setFullPath(directory);
            currentDirectoryTree[0].setCreated(created);
            currentDirectoryTree[0].setUpdatedAt(update);
        });

        return directoryTree;
    }


}
