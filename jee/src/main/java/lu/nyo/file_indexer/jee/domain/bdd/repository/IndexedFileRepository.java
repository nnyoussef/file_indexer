package lu.nyo.file_indexer.jee.domain.bdd.repository;

import lu.nyo.file_indexer.jee.domain.bdd.entities.IndexedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IndexedFileRepository extends JpaRepository<IndexedFile, Long> {

    @Query(value = "SELECT file_name from indexed_files where indices_ref in :refs", nativeQuery = true)
    List<String> findFileNameByDocRef(@Param("refs") List<String> refs);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM indexed_files where index_id = :indexId", nativeQuery = true)
    void deleteByIndexId(@Param("indexId") Long indexId);

    @Query("select indexedFile from IndexedFile indexedFile where indexedFile.indexId = :indexId and indexedFile.fileName = :fileName")
    IndexedFile findIndexedFileByIndexIdAndFileName(@Param("indexId") Long indexId,
                                                    @Param("fileName") String fileName);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM indexed_files where index_id = :indexId and file_name = :fileName", nativeQuery = true)
    void deleteFileByFileId(@Param("indexId") Long indexId,
                            @Param("fileName") String fileName);
}
