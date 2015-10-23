package edu.tufts.gis.projectexplorer.component;

import edu.tufts.gis.projectexplorer.domain.*;
import edu.tufts.gis.projectexplorer.domain.entity.Project;
import edu.tufts.gis.projectexplorer.domain.entity.ProjectResource;
import edu.tufts.gis.projectexplorer.repository.ProjectRepository;
import edu.tufts.gis.projectexplorer.repository.ProjectResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

/**
 * Created by cbarne02 on 5/14/15.
 */
public class StorageHandler {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectResourceRepository projectResourceRepository;


    public ProjectResource storeResource(MultipartFile multipartFile, ResourceType resourceType) throws IOException {
        ProjectResource projectResource = new ProjectResource();
        Path tempPath = Files.createTempFile("tempProjectResource", "");
        multipartFile.transferTo(tempPath.toFile());
        //if we're going to convert .doc and .docx to .pdf, we should do that here.
        projectResource.setResourcePath(tempPath.toAbsolutePath().toString());
        projectResource.setResourceType(resourceType);
        projectResource.setCreationDate(new Date());
        ProjectResource savedResource = projectResourceRepository.save(projectResource);
        //TODO: does savedResource reflect changes made by the EntityListener?
        return savedResource;
    }

    public ProjectResource storeResource(MultipartFile multipartFile, ResourceType resourceType, UUID projectId) throws IOException {

        ProjectResource newResource = storeResource(multipartFile, resourceType);

        Project project = projectRepository.findOne(projectId);

        associateResourceWithProject(newResource, project);

        return newResource;
    }

    private void associateResourceWithProject(ProjectResource newResource, Project project) {
        if (newResource.getResourceType().isPaper()){
            if (project.getPaperId() != null){
                //need to delete old paper resource; listener will remove the actual files
                projectResourceRepository.delete(project.getPaperId());

            }
            project.setPaperId(newResource.getId());
        } else if (newResource.getResourceType().isPoster()){
            if (project.getPosterId() != null){
                //need to delete old poster resource; listener will remove the actual files
                projectResourceRepository.delete(project.getPosterId());
            }
            project.setPosterId(newResource.getId());
        }
        projectRepository.save(project);
    }





}
