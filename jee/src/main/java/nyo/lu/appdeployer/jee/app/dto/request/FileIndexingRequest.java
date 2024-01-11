package nyo.lu.appdeployer.jee.app.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import nyo.lu.appdeployer.jee.comon.enums.HostingMode;
import org.hibernate.validator.constraints.Length;

import java.util.Map;

public class FileIndexingRequest {

    @NotEmpty
    @Size(min = 2, max = 100)
    private String path;

    private HostingMode deploymentMod = HostingMode.DOWNLOAD;

    @Length(max = 50)
    private Map<String, String> tags;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public HostingMode getDeploymentMod() {
        return deploymentMod;
    }

    public void setDeploymentMod(HostingMode deploymentMod) {
        this.deploymentMod = deploymentMod;
    }
}
