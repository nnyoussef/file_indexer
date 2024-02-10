package lu.nyo.file_indexer.jee.domain.bdd.repository;


import lu.nyo.file_indexer.jee.domain.bdd.entities.IndexesMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexesMappingRepository extends JpaRepository<IndexesMappingEntity, Long> {

}
