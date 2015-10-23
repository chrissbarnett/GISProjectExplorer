package edu.tufts.gis.projectexplorer.repository;

import edu.tufts.gis.projectexplorer.domain.entity.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created by cbarne02 on 4/16/15.
 */
public interface CourseRepository extends CrudRepository<Course, UUID> {
}
