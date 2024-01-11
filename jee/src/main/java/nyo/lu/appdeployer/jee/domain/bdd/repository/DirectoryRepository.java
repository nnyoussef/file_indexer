package nyo.lu.appdeployer.jee.domain.bdd.repository;

import nyo.lu.appdeployer.jee.domain.bdd.entities.DirectoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DirectoryRepository extends JpaRepository<DirectoriesEntity, Long> {

    @Query(nativeQuery = true,
            value = """
                    select DIR_PATH,LABEL,LABEL_VALUE,CREATED,MODIFIED from directories\s
                    left join directories_labels on directories.DIR_ID = directories_labels.DIR_ID\s
                    ORDER BY CREATED DESC,DIR_PATH
                    """)
    List<Map<String, Object>> getAllInfoForAllDirectories();
}
