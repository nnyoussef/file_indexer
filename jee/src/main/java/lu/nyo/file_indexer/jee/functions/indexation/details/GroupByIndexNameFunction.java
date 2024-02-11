package lu.nyo.file_indexer.jee.functions.indexation.details;

import lu.nyo.file_indexer.jee.domain.bdd.queryresults.AllIndexesWithFileDetails;
import lu.nyo.file_indexer.jee.functions.AbstractInteractor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

@Service
@Lazy
public class GroupByIndexNameFunction extends AbstractInteractor implements Function<List<AllIndexesWithFileDetails>, Object> {
    @Override
    public Object apply(List<AllIndexesWithFileDetails> allIndexesWithFileDetails) {
        return allIndexesWithFileDetails.stream()
                .collect(groupingBy(AllIndexesWithFileDetails::getIndexName,
                        mapping(this::getDataObjectWithoutIndexName, toCollection(LinkedList::new))));
    }

    public Map<String, Object> getDataObjectWithoutIndexName(Map<String, Object> data) {
        Map<String, Object> dataToReturn = new HashMap<>(data);
        dataToReturn.remove("INDEX_NAME");
        return dataToReturn;
    }

}
