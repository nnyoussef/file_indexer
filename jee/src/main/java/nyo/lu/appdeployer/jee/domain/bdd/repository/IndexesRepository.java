package nyo.lu.appdeployer.jee.domain.bdd.repository;

import nyo.lu.appdeployer.jee.domain.bdd.entities.IndexesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexesRepository extends JpaRepository<IndexesEntity, Long> {

    @Query(value = "SELECT INDEX_ID from indexes where INDEX_NAME = :indexName", nativeQuery = true)
    Long findIndexIdByIndexName(@Param("indexName") String indexName);

}
