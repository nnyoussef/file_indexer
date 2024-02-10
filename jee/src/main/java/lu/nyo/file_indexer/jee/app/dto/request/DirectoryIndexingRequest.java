package nyo.lu.appdeployer.jee.app.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.LinkedList;
import java.util.Map;

public class DirectoryIndexingRequest {

    @NotEmpty
    @Length(min = 2, max = 1000)
    private String path;

    @Size(max = 50)
    private LinkedList<Map<String, String>> labels;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LinkedList<Map<String, String>> getLabels() {
        return labels;
    }

    public void setLabels(LinkedList<Map<String, String>> labels) {
        this.labels = labels;
    }
}
