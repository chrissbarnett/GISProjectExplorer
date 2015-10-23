package edu.tufts.gis.projectexplorer.service;

import edu.tufts.gis.projectexplorer.controller.ProjectResourceController;
import edu.tufts.gis.projectexplorer.domain.ProjectPackage;
import edu.tufts.gis.projectexplorer.domain.ResourceType;
import edu.tufts.gis.projectexplorer.domain.entity.Project;
import edu.tufts.gis.projectexplorer.domain.entity.ProjectResource;
import edu.tufts.gis.projectexplorer.repository.ProjectRepository;
import edu.tufts.gis.projectexplorer.repository.ProjectResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by cbarne02 on 5/15/15.
 *
 * returns Project and associated resources
 */
@Service
public class ProjectPackageService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectResourceRepository projectResourceRepository;

    public ProjectPackage find(UUID projectId) throws IOException {

        Project project = null;
        if (projectRepository.exists(projectId)) {
            project = projectRepository.findOne(projectId);

        } else {
            throw new IOException("Project not found!");
        }

        return wrapPackage(project);
    }

    public ProjectPackage save(ProjectPackage projectPackage){
        projectRepository.save(projectPackage.getProject());
        Set<ProjectResource> resources = projectPackage.getResourceSet();
        for (ProjectResource pr: resources){
            projectResourceRepository.save(pr);
        }
        return projectPackage;
    }

    public ProjectPackage wrapPackage(Project project){
        ProjectPackage projectPackage = new ProjectPackage();
        Set<ProjectResource> projectResourceSet = new HashSet<>();
        projectPackage.setProject(project);

        UUID posterId = project.getPosterId();
        if (posterId != null) {
            ProjectResource poster = projectResourceRepository.findOne(posterId);
            try {
                addUrlsToResource(poster);
            } catch (Exception e) {
                e.printStackTrace();
            }
            projectResourceSet.add(poster);
        }

        UUID paperId = project.getPaperId();
        if (paperId != null) {
            ProjectResource paper = projectResourceRepository.findOne(paperId);
            try {
                addUrlsToResource(paper);
            } catch (Exception e) {
                e.printStackTrace();
            }
            projectResourceSet.add(paper);
        }


        projectPackage.setResourceSet(projectResourceSet);

        return projectPackage;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }




    public void addUrlsToResource(ProjectResource projectResource) throws Exception {

        Link link = linkTo(methodOn(ProjectResourceController.class)
                .getProjectResource(projectResource.getId(), projectResource.getResourceType().getResourceName())).withRel("resource");

        Link thumbLink = linkTo(methodOn(ProjectResourceController.class)
                .getProjectResource(projectResource.getId(), ResourceType.thumbnail_png.getResourceName())).withRel("preview");

        projectResource.setUrl(link.getHref());
        projectResource.setThumbUrl(thumbLink.getHref());

    }
}
