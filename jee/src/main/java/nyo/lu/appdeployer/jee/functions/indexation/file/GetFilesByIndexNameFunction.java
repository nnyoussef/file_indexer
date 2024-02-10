package nyo.lu.appdeployer.jee.functions.indexation.file;

import nyo.lu.appdeployer.jee.functions.AbstractInteractor;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static nyo.lu.appdeployer.jee.functions.indexation.file.GetFilesByIndexNameFunction.Input;

@Service
@Lazy
public class GetFilesByIndexNameFunction extends AbstractInteractor implements Function<Input, List<String>> {

    @Override
    public List<String> apply(Input input) {
        HashMap<String, Object> esRequestBody = new HashMap<>();
        HashMap<String, Object> query = new HashMap<>();
        HashMap<String, Object> bool = new HashMap<>();
        List<Object> must = new LinkedList<>(input.indices.entrySet()
                .stream()
                .filter(e -> StringUtils.isNotEmpty(e.getValue()))
                .map(e -> Map.of("wildcard", Map.of(e.getKey(), String.format("*%s*", e.getValue()))))
                .toList());
        must.add(createRangeSearchCriteria(input.dateFrom, input.dateTo, "created_at"));

        bool.put("must", must);
        query.put("bool", bool);
        esRequestBody.put("query", query);
        System.out.println(esRequestBody);

        return ((List) ((Map<String, Object>) elasticSearchApi.searchByIndexAndIndices(input.index, esRequestBody).get("hits")).get("hits")).stream()
                .map(e -> ((Map<String, Object>) e).get("_id")).toList();
    }

    public record Input(String index, String dateFrom, String dateTo, Map<String, String> indices) {
    }

    public Map<String, Object> createRangeSearchCriteria(String startValue,
                                                         String endValue,
                                                         String fieldName) {
        long from = 0;
        long to = Long.MAX_VALUE;

        if (StringUtils.isNotEmpty(startValue) && StringUtils.isNumeric(startValue)) {
            from = Long.parseLong(startValue);
        }
        if (StringUtils.isNotEmpty(endValue) && StringUtils.isNumeric(startValue)) {
            to = Long.parseLong(endValue);
        }

        return Map.of("range", Map.of(fieldName, Map.of("gte", from, "lte", to)));
    }
}
