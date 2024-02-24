package lu.nyo.file_indexer.jee.functions.indexation.rename;

import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Lazy
public class IndexRenameEsFunction extends AbstractFunction implements Function<IndexRenameEsFunction.Input, IndexRenameEsFunction.Input> {
    public static class Input {
        private String indexName;

        private String newIndexName;

        public String getIndexName() {
            return indexName;
        }

        public void setIndexName(String indexName) {
            this.indexName = indexName;
        }

        public String getNewIndexName() {
            return newIndexName;
        }

        public void setNewIndexName(String newIndexName) {
            this.newIndexName = newIndexName;
        }
    }

    @Override
    public IndexRenameEsFunction.Input apply(Input input) {
        Map<String, Object> data = new HashMap<>();

        Map<String, String> source = Map.of("index", input.indexName);
        Map<String, String> dest = Map.of("index", input.newIndexName);

        data.put("source", source);
        data.put("dest", dest);

        System.out.println(data.toString().replace("=", ":"));
        elasticSearchApi.indexRename(data);
        return input;
    }
}
