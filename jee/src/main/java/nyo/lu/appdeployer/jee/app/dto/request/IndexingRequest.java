package nyo.lu.appdeployer.jee.app.dto.request;

import java.util.LinkedList;

public class IndexingRequest {

    private String indexName;

    private LinkedList<String> mappings;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public LinkedList<String> getMappings() {
        return mappings;
    }

    public void setMappings(LinkedList<String> mappings) {
        this.mappings = mappings;
    }
}
