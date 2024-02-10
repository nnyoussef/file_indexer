package nyo.lu.appdeployer.jee.functions.indexation;

import nyo.lu.appdeployer.jee.app.dto.request.IndexingRequest;
import nyo.lu.appdeployer.jee.functions.AbstractInteractor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class CreateIndexMappingsFunction extends AbstractInteractor implements Function<IndexingRequest, IndexingRequest> {

    @Override
    public IndexingRequest apply(IndexingRequest indexingRequest) {

        HashMap<String, Object> elasticSearchMapping = new HashMap<>();
        elasticSearchMapping.put("properties", new HashMap<String, Object>());
        ((HashMap<String, Object>) elasticSearchMapping.get("properties")).put("created_at", Map.of("type", "date"));

        indexingRequest.getMappings().forEach(s -> {
            String[] propertyPathComposition = s.split("[.]");
            HashMap<String, Object> lastKnowSubField = elasticSearchMapping;

            for (String property : propertyPathComposition) {
                if (Objects.equals(property, "undefined"))
                    return;
                if (lastKnowSubField.containsKey("properties")) {

                    if (((HashMap<String, Object>) lastKnowSubField.get("properties")).containsKey(property)) {
                        lastKnowSubField = ((HashMap<String, Object>) ((HashMap<String, Object>) lastKnowSubField.get("properties")).get(property));
                    } else {
                        HashMap<String, Object> fieldType = new HashMap<>();
                        fieldType.put("type", "text");
                        ((HashMap<String, Object>) lastKnowSubField.get("properties")).put(property, fieldType);
                        lastKnowSubField = fieldType;
                    }
                } else {
                    lastKnowSubField.put("type", "object");
                    lastKnowSubField.put("properties", new HashMap<String, Object>());

                    HashMap<String, Object> fieldType = new HashMap<>();
                    fieldType.put("type", "text");

                    ((HashMap<String, Object>) lastKnowSubField.get("properties")).put(property, fieldType);
                }
            }

        });
        elasticSearchApi.updateMappingForIndex(indexingRequest.getIndexName(), elasticSearchMapping);
        return indexingRequest;
    }


}

