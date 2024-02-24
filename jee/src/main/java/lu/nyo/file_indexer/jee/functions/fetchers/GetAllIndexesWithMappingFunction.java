package lu.nyo.file_indexer.jee.functions.fetchers;

import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public  class GetAllIndexesWithMappingFunction extends AbstractFunction implements Function<Void, Map<String, Object>> {

    @Override
    public Map<String, Object> apply(Void unused) {
        return elasticSearchApi.getAllIndexes();
    }
}
