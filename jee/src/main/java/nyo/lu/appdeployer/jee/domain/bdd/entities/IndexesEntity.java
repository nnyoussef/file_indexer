package nyo.lu.appdeployer.jee.domain.bdd.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "indexes")
public class IndexesEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INDEX_ID", insertable = false)
    @Id
    private Long indexId;

    @Column(name = "INDEX_NAME")
    private String indexName;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Column(name = "MODIFIED")
    private LocalDateTime modified;

    public Long getIndexId() {
        return indexId;
    }

    public void setIndexId(Long indexId) {
        this.indexId = indexId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
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
