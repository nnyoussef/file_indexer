package lu.nyo.file_indexer.jee.domain.bdd.repository;


import lu.nyo.file_indexer.jee.domain.bdd.entities.IndexesMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IndexesMappingRepository extends JpaRepository<IndexesMappingEntity, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM index_mappings where INDEX_ID = :indexId", nativeQuery = true)
    void deleteByIndexId(@Param("indexId") Long indexId);

}
