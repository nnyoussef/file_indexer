package lu.nyo.file_indexer.jee.functions.indexation.rename;

import lu.nyo.file_indexer.jee.functions.AbstractFunction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Function;

@Service
@Lazy
public class IndexRenameFsFunction extends AbstractFunction implements Function<IndexRenameEsFunction.Input, Void> {

    @Override
    public Void apply(IndexRenameEsFunction.Input input) {
        File file = new File(Path.of(applicationStorage.getBasepath(), input.getIndexName()).toString());
        if (file.exists())
            file.renameTo(new File(applicationStorage.getBasepath(), input.getNewIndexName()));
        else
            try {
                throw new InstantiationException("Folder already exits");
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        return null;
    }
}
