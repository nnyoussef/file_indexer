package lu.nyo.file_indexer.jee.functions.indexation.details;

import lu.nyo.file_indexer.jee.domain.bdd.queryresults.AllIndexesWithFileDetails;
import lu.nyo.file_indexer.jee.functions.AbstractInteractor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@Lazy
public class GetAllIndexesInDbFunction extends AbstractInteractor implements Function<Void, List<AllIndexesWithFileDetails>> {
    @Override
    public List<AllIndexesWithFileDetails>  apply(Void unused) {
        indexesRepository.findAllIndexesWithAssociatedFiles();
        return  indexesRepository.findAllIndexesWithAssociatedFiles();
    }
}
