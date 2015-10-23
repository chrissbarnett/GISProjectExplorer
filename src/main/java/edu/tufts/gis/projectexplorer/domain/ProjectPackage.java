package edu.tufts.gis.projectexplorer.domain;

import edu.tufts.gis.projectexplorer.domain.entity.Project;
import edu.tufts.gis.projectexplorer.domain.entity.ProjectResource;

import java.util.Set;

/**
 * Created by cbarne02 on 5/15/15.
 */
public class ProjectPackage {
    private Project project;
    private Set<ProjectResource> resourceSet;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<ProjectResource> getResourceSet() {
        return resourceSet;
    }

    public void setResourceSet(Set<ProjectResource> resourceSet) {
        this.resourceSet = resourceSet;
    }
}
