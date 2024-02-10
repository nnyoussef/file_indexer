package nyo.lu.appdeployer.jee.functions.indexation.file;

import nyo.lu.appdeployer.jee.functions.AbstractInteractor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Lazy
@Component
public class FetchFilesByIdsFunction extends AbstractInteractor implements Function<List<String>, List<String>> {

    @Override
    public List<String> apply(List<String> strings) {

        return indexedFileRepository.findFileNameByDocRef(strings);
    }
}
