package nyo.lu.appdeployer.jee.app.dto.response;

import java.time.LocalDateTime;
import java.util.HashMap;

public class DirectoryInfo {
    private final String path;
    private final HashMap<String, String> labels;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public DirectoryInfo(String path, HashMap<String, String> labels) {
        this.path = path;
        this.labels = labels;
    }

    public String getPath() {
        return path;
    }

    public HashMap<String, String> getLabels() {
        return labels;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
