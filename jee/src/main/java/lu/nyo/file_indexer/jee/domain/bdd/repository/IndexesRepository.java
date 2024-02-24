package lu.nyo.file_indexer.jee.domain.bdd.repository;

import lu.nyo.file_indexer.jee.domain.bdd.entities.IndexesEntity;
import lu.nyo.file_indexer.jee.domain.bdd.queryresults.AllIndexesWithFileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IndexesRepository extends JpaRepository<IndexesEntity, Long> {

    @Query(value = "SELECT INDEX_ID from indexes where INDEX_NAME = :indexName", nativeQuery = true)
    Long findIndexIdByIndexName(@Param("indexName") String indexName);

    @Query(value = "SELECT CREATED,MODIFIED,file_name as FILE_NAME,index_name as INDEX_NAME,indices_ref from indexes LEFT JOIN indexed_files  on indexed_files.index_id  = indexes.INDEX_ID", nativeQuery = true)
    List<AllIndexesWithFileDetails> findAllIndexesWithAssociatedFiles();

    @Query(value = "Update indexes set INDEX_NAME = :newIndexName where INDEX_ID = :targetIndexId", nativeQuery = true)
    @Modifying
    @Transactional
    void renameIndex(@Param("newIndexName") String newIndexName,
                     @Param("targetIndexId") Long targetIndexId);

}
