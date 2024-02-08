package nyo.lu.appdeployer.jee.domain.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@FeignClient(url = "http://localhost:9200", name = "es")
public interface ElasticSearchApi {
    @RequestMapping(method = RequestMethod.GET, value = "/_mapping")
    HashMap<String, Object> getAllIndexes();

    @RequestMapping(method = RequestMethod.GET, value = "/{indexName}")
    HashMap<String, Object> getIndex(@PathVariable("indexName") String indexName);

    @RequestMapping(method = RequestMethod.PUT, value = "/{indexName}")
    void createIndex(@PathVariable("indexName") String indexName);

    @PutMapping(value = "/{indexName}/_mapping")
    void updateMappingForIndex(@PathVariable("indexName") String indexName,
                               @RequestBody HashMap<String, Object> requestBody);

    @DeleteMapping(value = "/{indexName}")
    Object delete(@PathVariable("indexName") String indexName);

    @PostMapping(value = "/{indexName}/_doc/")
    Map<String, Object> indexDocument(@PathVariable("indexName") String indexName,
                                      @RequestBody Map<String, Object> data);

    @PostMapping(value = "/{indexName}/_search")
    Map<String, Object> searchByIndexAndIndices(@PathVariable("indexName") String indexName,
                                                @RequestBody Map<String, Object> data);

}
