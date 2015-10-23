package edu.tufts.gis.projectexplorer.config;

import edu.tufts.gis.projectexplorer.component.StorageHandler;
import edu.tufts.gis.projectexplorer.component.StorageHandlerManager;
import edu.tufts.gis.projectexplorer.component.geocoder.Geocoder;
import edu.tufts.gis.projectexplorer.component.textprocessing.TextExtractor;
import edu.tufts.gis.projectexplorer.component.geocoder.GeocoderManager;
import edu.tufts.gis.projectexplorer.component.image.ThumbnailCreator;
import edu.tufts.gis.projectexplorer.component.image.ThumbnailManager;
import edu.tufts.gis.projectexplorer.component.textprocessing.TextExtractorManager;
import edu.tufts.gis.projectexplorer.domain.ResourceType;
import edu.tufts.gis.projectexplorer.domain.entity.Project;
import edu.tufts.gis.projectexplorer.domain.entity.ProjectResource;
import edu.tufts.gis.projectexplorer.repository.eventhandlers.ProjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.validation.Validator;

import java.util.UUID;



/**
 * Created by cbarne02 on 4/30/15.
 */
@Configuration
public class ComponentBeanFactoryConfig {
    private static final Logger log = LoggerFactory.getLogger(ComponentBeanFactoryConfig.class);

    @Bean
    @Scope("prototype")
    public StorageHandler getStorageHandler(){
        return new StorageHandler();
    }

    @Bean
    public StorageHandlerManager storageHandlerManager(){
        return new StorageHandlerManager(){
            @Override
            public StorageHandler createStorageHandler() {
                return getStorageHandler();
            }
        };
    }

    @Bean
    @Scope("prototype")
    public ThumbnailCreator getThumbnailCreator(){
        return new ThumbnailCreator();
    }

    @Bean
    public ThumbnailManager thumbnailManager(){
        return new ThumbnailManager() {
            @Override
            public ThumbnailCreator createThumbnailCreator() {
                return getThumbnailCreator();
            }
        };
    }

    @Bean
    @Scope("prototype")
    public TextExtractor getTextExtractor(){
        return new TextExtractor();
    }

    @Bean
    public TextExtractorManager textExtractorManager(){
        return new TextExtractorManager() {
            @Override
            public TextExtractor createTextExtractor() {
                return getTextExtractor();
            }
        };
    }

    @Bean
    @Scope("prototype")
    public Geocoder getGeocoder(){return new Geocoder();}

    @Bean
    public GeocoderManager geocoderManager(){
        return new GeocoderManager() {
            @Override
            public Geocoder createGeocoder() {
                return getGeocoder();
            }
        };
    }

    @Bean(name = "beforeCreateProjectValidator")
    public Validator getProjectValidator(){
        return new ProjectValidator();
    }


    @Bean
    public ResourceProcessor<Resource<Project>> projectLinkProcessor() {

        return new ResourceProcessor<Resource<Project>>() {

            @Override
            public Resource<Project> process(Resource<Project> resource) {
                Project project = resource.getContent();

                //link back to the projectResources api path
                //ex: http://localhost:8080/api/projectResources/32f6d340-fb36-11e4-b939-0800200c9a66
                String selfLink = resource.getLink("self").getHref();
                if (project.getPosterId() != null) {
                    try {
                        resource.add(getResourceLink(selfLink, project.getPosterId(), "poster"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (project.getPaperId() != null) {
                    try {
                        resource.add(getResourceLink(selfLink, project.getPaperId(), "paper"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                return resource;
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<ProjectResource>> projectResourceLinkProcessor() {

        return new ResourceProcessor<Resource<ProjectResource>>() {

            @Override
            public Resource<ProjectResource> process(Resource<ProjectResource> resource) {
                //this logic should be injected at the repository level
                ProjectResource projectResource = resource.getContent();
                //add links to the resources

                String selfLink = resource.getLink("self").getHref();
                //add a link for the item and its preview
                ResourceType resourceType = projectResource.getResourceType();
                String resourceName = resourceType.getResourceName();
                String relation = "";
                if (resourceType.isPoster()){
                    relation = "poster_pdf";
                } else if (resourceType.isPaper()){
                    relation = "paper_pdf";
                } else {
                    log.error("Unable to process ResourceType ['" + resourceType.name() + "']");
                }

                resource.add(new Link(selfLink + "/" + resourceName, "resource"));
                resource.add(new Link(selfLink + "/" + ResourceType.thumbnail_png.getResourceName(), "preview"));

                return resource;
            }
        };
    }



    public Link getResourceLink(String selfLink, UUID resourceId, String relation) throws Exception {
        selfLink = selfLink.substring(0, selfLink.lastIndexOf("/projects/"));
        return new Link(selfLink + "/projectResources/" + resourceId.toString(), relation);
    }

}
