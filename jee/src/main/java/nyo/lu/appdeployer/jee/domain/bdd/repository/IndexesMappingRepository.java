package nyo.lu.appdeployer.jee.domain.bdd.repository;

import nyo.lu.appdeployer.jee.domain.bdd.entities.IndexesEntity;
import nyo.lu.appdeployer.jee.domain.bdd.entities.IndexesMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexesMappingRepository extends JpaRepository<IndexesMappingEntity, Long> {

}
