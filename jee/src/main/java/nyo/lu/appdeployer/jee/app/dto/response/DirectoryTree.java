package nyo.lu.appdeployer.jee.app.dto.response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DirectoryTree {

    private final Map<String, String> labels = new HashMap<>();

    private final Map<String, DirectoryTree> subTree = new LinkedHashMap<>(10_000);

    private LocalDateTime created;

    private LocalDateTime updatedAt;

    private String fullPath;

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public Map<String, DirectoryTree> getSubTree() {
        return subTree;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
