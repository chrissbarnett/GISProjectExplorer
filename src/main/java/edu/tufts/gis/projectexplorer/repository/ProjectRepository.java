package edu.tufts.gis.projectexplorer.repository;

import edu.tufts.gis.projectexplorer.domain.entity.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by cbarne02 on 4/16/15.
 */
public interface ProjectRepository extends CrudRepository<Project, UUID> {
    List<Project> findBySearchableTrue();

    List<Project> findBySearchableFalse();

}
