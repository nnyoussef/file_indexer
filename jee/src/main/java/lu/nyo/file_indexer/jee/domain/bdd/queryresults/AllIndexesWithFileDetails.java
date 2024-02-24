package lu.nyo.file_indexer.jee.domain.bdd.queryresults;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

public class AllIndexesWithFileDetails extends HashMap<String, Object> {


    public LocalDateTime getCreatedDate() {
        return ((Timestamp) getOrDefault("CREATED", Timestamp.valueOf(LocalDateTime.MIN))).toLocalDateTime();
    }

    public LocalDateTime getModifiedDate() {
        return ((Timestamp) getOrDefault("MODIFIED", Timestamp.valueOf(LocalDateTime.MIN))).toLocalDateTime();
    }

    public String getFileName() {
        return getOrDefault("FILE_NAME", "").toString();
    }

    public String getIndexName() {
        return getOrDefault("INDEX_NAME", "").toString();
    }

    public String getEsRef() {
        return getOrDefault("indices_ref", "").toString();
    }
}
