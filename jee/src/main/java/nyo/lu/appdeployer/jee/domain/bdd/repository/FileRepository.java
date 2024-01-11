package nyo.lu.appdeployer.jee.domain.bdd.repository;

import nyo.lu.appdeployer.jee.domain.bdd.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
