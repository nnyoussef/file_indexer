package nyo.lu.appdeployer.jee.functions.indexation;

import nyo.lu.appdeployer.jee.functions.AbstractInteractor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class GetAllIndexesWithMappingFunction extends AbstractInteractor implements Function<Void, Map<String, Object>> {

    @Override
    public Map<String, Object> apply(Void unused) {
        return elasticSearchApi.getAllIndexes();
    }
}
