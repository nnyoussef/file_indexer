package lu.nyo.file_indexer.jee.domain.bdd.repository;

import lu.nyo.file_indexer.jee.domain.bdd.entities.IndexedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexedFileRepository extends JpaRepository<IndexedFile, Long> {

    @Query(value = "SELECT file_name from indexed_files where indices_ref in :refs", nativeQuery = true)
    List<String> findFileNameByDocRef(@Param("refs") List<String> refs);
}
