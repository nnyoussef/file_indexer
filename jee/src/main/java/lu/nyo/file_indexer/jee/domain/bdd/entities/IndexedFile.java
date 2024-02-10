package lu.nyo.file_indexer.jee.domain.bdd.entities;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "indexed_files")
public class IndexedFile {
    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "index_id")
    private Long indexId;

    @Column(name = "indices_ref")
    private String indicesRef;

    @Column(name = "file_name")
    private String fileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIndexId() {
        return indexId;
    }

    public void setIndexId(Long indexId) {
        this.indexId = indexId;
    }

    public String getIndicesRef() {
        return indicesRef;
    }

    public void setIndicesRef(String indicesRef) {
        this.indicesRef = indicesRef;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
