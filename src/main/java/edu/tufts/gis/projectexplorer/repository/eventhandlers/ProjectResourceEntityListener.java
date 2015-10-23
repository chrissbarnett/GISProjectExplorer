package edu.tufts.gis.projectexplorer.repository.eventhandlers;

import edu.tufts.gis.projectexplorer.component.ResourceDirectory;
import edu.tufts.gis.projectexplorer.component.image.ThumbnailManager;
import edu.tufts.gis.projectexplorer.domain.ResourceType;
import edu.tufts.gis.projectexplorer.domain.entity.Project;
import edu.tufts.gis.projectexplorer.domain.entity.ProjectResource;
import edu.tufts.gis.projectexplorer.exception.SolrPublicationException;
import edu.tufts.gis.projectexplorer.service.PublicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by cbarne02 on 5/13/15.
 */
@Component
public class ProjectResourceEntityListener implements ApplicationContextAware {


    private static final Logger log = LoggerFactory.getLogger(ProjectResourceEntityListener.class);
    private static ApplicationContext context;


    private ResourceDirectory getResourceDirectory(){
        return context.getBean(ResourceDirectory.class);
    }

    private ThumbnailManager getThumbnailManager(){
        return context.getBean(ThumbnailManager.class);
    }



    @PostRemove
    public void handleResourceDelete(ProjectResource projectResource) {
        log.info("pre remove");

        //before an entry is deleted from the repository, the corresponding resources should also be deleted
        try {
            getResourceDirectory().removeAllResources(projectResource.getId());
        } catch (IOException e) {
            //should log the exception
            log.error("Unable to remove resource ['{}']", projectResource.getId());
        }
    }

    @PostPersist
    public void handleResourceCreate(ProjectResource projectResource){
        //the temp path is temporarily stored in the resource path field
        Path newPath = transferResource(Paths.get(projectResource.getResourcePath()), projectResource.getResourceType(), projectResource.getId());

        //create the thumbnail while we're at it.
        createThumbnail(newPath, projectResource.getId());

    }

    private void createThumbnail(Path resourcePath, UUID id){
        try {
            Path tempThumbnailPath = getThumbnailManager().createThumbnailCreator().createFromPdf(resourcePath);
            transferResource(tempThumbnailPath, ResourceType.thumbnail_png, id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path transferResource(Path oldPath, ResourceType resourceType, UUID resourceId){

        //move the temp resource to the Resource directory and set the new path in the object
        Path newPath = null;
        try {
            newPath = getResourceDirectory().addResource(resourceId, resourceType, oldPath);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Unable to move resource from temporary location to Resource directory.");

        }

        return newPath;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
