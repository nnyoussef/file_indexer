package nyo.lu.appdeployer.jee.domain.bdd.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "directories_labels")
public class DirectoriesLabelsEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LABEL_ID", insertable = false)
    @Id
    private Long labelId;

    @Column(name = "DIR_ID")
    private Long dirId;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "LABEL_VALUE")
    private String labelValue;

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Long getDirId() {
        return dirId;
    }

    public void setDirId(Long dirId) {
        this.dirId = dirId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
