package edu.tufts.gis.projectexplorer.repository.eventhandlers;

import edu.tufts.gis.projectexplorer.component.ResourceDirectory;
import edu.tufts.gis.projectexplorer.domain.entity.Project;
import edu.tufts.gis.projectexplorer.exception.SolrPublicationException;
import edu.tufts.gis.projectexplorer.repository.ProjectResourceRepository;
import edu.tufts.gis.projectexplorer.service.PublicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


import javax.persistence.*;
import java.io.IOException;

/**
 * Created by cbarne02 on 5/13/15.
 */
@Component
public class ProjectEntityListener implements ApplicationContextAware {


    private static final Logger log = LoggerFactory.getLogger(ProjectEntityListener.class);
    private static ApplicationContext context;


    private PublicationService getPublicationService(){
        return context.getBean(PublicationService.class);
    }

    private ProjectResourceRepository getProjectResourceRepository(){
        return context.getBean(ProjectResourceRepository.class);
    }


    @PreRemove
    public void handleProjectDelete(Project project) {
        log.info("pre remove");
        //make sure to prevent orphaned resources
        //should use the before save callback to delete existing resources associated with a project if it is changing

        if (project.getSearchable()){
            try {
                getPublicationService().unpublish(project);
            } catch (SolrPublicationException e) {
                e.printStackTrace();
                log.error("Unable to unpublish project ['{}'] from solr.", project.getId());
            }
        }

        //before an entry is deleted from the repository, the corresponding resources should also be deleted
        //better to do after?
        if (project.getPosterId() != null) {
            getProjectResourceRepository().delete(project.getPosterId());
        }
        if (project.getPaperId() != null) {
            getProjectResourceRepository().delete(project.getPaperId());
        }

    }

    @PostUpdate
    public void handleProjectSave(Project project){
        log.info("After update");
        log.info(Boolean.toString(project == null));
        if (project == null){
            return;
        }

        if (project.getSearchable()){
            log.info("project is searchable");
            try {
                getPublicationService().publish(project);
            } catch (SolrPublicationException e) {
                e.printStackTrace();
                log.error("Unable to publish project ['{}'] to solr.", project.getId());
            }
        }
    }

    @PreUpdate
    public void handleBeforeSave(Project project){
        //TODO; is this project value the one being updated or the update itself?
        log.info("preupdate");
        //make sure to prevent orphaned resources
        //should use the before save callback to delete existing resources associated with a project if it is changing
    }

    @PrePersist
    public void handleBeforeCreate(Project project){
        log.info("pre persist");
    }

    public void handleBeforeLinkSave(Project project){
        log.info("post update");

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
