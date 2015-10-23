package edu.tufts.gis.projectexplorer.repository;

import edu.tufts.gis.projectexplorer.domain.entity.ProjectResource;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created by cbarne02 on 4/16/15.
 */

public interface ProjectResourceRepository extends CrudRepository<ProjectResource, UUID> {
}
