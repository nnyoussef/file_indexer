package lu.nyo.file_indexer.jee.domain.bdd.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "index_mappings")
public class IndexesMappingEntity {

    public static class Id {
        @Column(name = "INDEX_ID")
        private Long indexId;

        @Column(name = "MAPPING_NAME")
        private String mappingName;

        public Long getIndexId() {
            return indexId;
        }

        public void setIndexId(Long indexId) {
            this.indexId = indexId;
        }

        public String getMappingName() {
            return mappingName;
        }

        public void setMappingName(String mappingName) {
            this.mappingName = mappingName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return Objects.equals(indexId, id.indexId) && Objects.equals(mappingName, id.mappingName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(indexId, mappingName);
        }
    }

    @EmbeddedId
    private Id id;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }
}

