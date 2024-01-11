package nyo.lu.appdeployer.jee.service.functions.directory;

import nyo.lu.appdeployer.jee.app.dto.response.DirectoryInfo;
import nyo.lu.appdeployer.jee.service.functions.AbstractInteractor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static org.apache.logging.log4j.util.Strings.EMPTY;

@Component
public class DirectoryFetchAllFunction extends AbstractInteractor implements Function<Void, Collection<Optional<DirectoryInfo>>> {
    private static final String LABEL = "LABEL";
    private static final BinaryOperator<DirectoryInfo> ACCUMULATOR = (accumulated, input) -> {
        accumulated.getLabels().putAll(input.getLabels());
        return accumulated;
    };

    @Override
    public Collection<Optional<DirectoryInfo>> apply(Void unused) {
        return directoryRepository.getAllInfoForAllDirectories()
                .stream()
                .map(e -> {
                    String path = e.get("DIR_PATH").toString().replace(applicationStorage.getBasepath(), EMPTY);
                    HashMap<String, String> labels = ofNullable(e.get(LABEL))
                            .map(label -> {
                                HashMap<String, String> labelKeyValue = new HashMap<>(1);
                                labelKeyValue.put(e.get(LABEL).toString(), e.get("LABEL_VALUE").toString());
                                return labelKeyValue;
                            })
                            .orElse(new HashMap<>(0));

                    DirectoryInfo directoryInfo = new DirectoryInfo(path, labels);
                    directoryInfo.setCreatedAt(((Timestamp) e.get("CREATED")).toLocalDateTime());
                    directoryInfo.setUpdatedAt(((Timestamp) e.get("MODIFIED")).toLocalDateTime());
                    return directoryInfo;
                })
                .collect(groupingBy(DirectoryInfo::getPath, LinkedHashMap::new, reducing(ACCUMULATOR)))
                .values();
    }


}
