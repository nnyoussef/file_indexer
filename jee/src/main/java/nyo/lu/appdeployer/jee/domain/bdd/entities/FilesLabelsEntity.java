package nyo.lu.appdeployer.jee.domain.bdd.entities;

import jakarta.persistence.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "files_labels")
public class FilesLabelsEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID", insertable = false)
    @Id
    private Long fileId;

    @Column(name = "LABEL_ID")
    private Long labelId;

    @Column(name = "LABEL")
    private String label;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
