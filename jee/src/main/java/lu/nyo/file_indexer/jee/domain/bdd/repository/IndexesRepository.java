package lu.nyo.file_indexer.jee.domain.bdd.repository;

import lu.nyo.file_indexer.jee.domain.bdd.entities.IndexesEntity;
import lu.nyo.file_indexer.jee.domain.bdd.queryresults.AllIndexesWithFileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IndexesRepository extends JpaRepository<IndexesEntity, Long> {

    @Query(value = "SELECT INDEX_ID from indexes where INDEX_NAME = :indexName", nativeQuery = true)
    Long findIndexIdByIndexName(@Param("indexName") String indexName);

    @Query(value = "SELECT CREATED,MODIFIED,file_name as FILE_NAME,index_name as INDEX_NAME,indices_ref from indexes LEFT JOIN indexed_files  on indexed_files.index_id  = indexes.INDEX_ID", nativeQuery = true)
    List<AllIndexesWithFileDetails> findAllIndexesWithAssociatedFiles();

}
