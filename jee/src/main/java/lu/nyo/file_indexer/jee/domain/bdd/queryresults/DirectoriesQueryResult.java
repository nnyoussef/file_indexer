package lu.nyo.file_indexer.jee.domain.bdd.queryresults;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

import static java.util.Optional.ofNullable;

public class DirectoriesQueryResult extends HashMap<String, Object> {

    public String getLabel() {
        return ofNullable(get("LABEL")).map(Object::toString).orElse("");
    }

    public String getLabelValue() {
        return ofNullable(get("LABEL_VALUE")).map(Object::toString).orElse("");
    }

    public String getDirPath() {
        return get("DIR_PATH").toString();
    }

    public LocalDateTime getUpdated() {
        return ((Timestamp) get("MODIFIED")).toLocalDateTime();
    }


    public LocalDateTime getCreated() {
        return ((Timestamp) get("CREATED")).toLocalDateTime();
    }

}
