package nyo.lu.appdeployer.jee.domain.bdd.entities;

import jakarta.persistence.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "directories")
public class DirectoriesEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIR_ID", insertable = false)
    @Id
    private Long dirId;

    @Column(name = "DIR_PATH")
    private String dirPath;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Column(name = "MODIFIED")
    private LocalDateTime modified;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dirId", dirId)
                .append("dirPath", dirPath)
                .append("created", created)
                .append("modified", modified)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DirectoriesEntity that = (DirectoriesEntity) o;

        return new EqualsBuilder().append(dirId, that.dirId).append(dirPath, that.dirPath).append(created, that.created).append(modified, that.modified).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(dirId).append(dirPath).append(created).append(modified).toHashCode();
    }

    public Long getDirId() {
        return dirId;
    }

    public void setDirId(Long dirId) {
        this.dirId = dirId;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }
}
