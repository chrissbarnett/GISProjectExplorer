package edu.tufts.gis.projectexplorer.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.tufts.gis.projectexplorer.domain.ResourceType;
import edu.tufts.gis.projectexplorer.repository.eventhandlers.ProjectResourceEntityListener;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by cbarne02 on 5/14/15.
 */
@Entity
@EntityListeners(ProjectResourceEntityListener.class)
public class ProjectResource {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    private ResourceType resourceType;

    @Column(columnDefinition="text")
    @RestResource(exported = false)
    private String extractedText;

    private Date creationDate;

    @RestResource(exported = false)
    @JsonIgnore
    private String resourcePath;

    @Transient
    @JsonIgnore
    private String url;

    @Transient
    @JsonIgnore
    private String thumbUrl;


    public UUID getId() {
        return id;
    }


    public String getUrl() {
        return url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }


    public Date getCreationDate() {
        return creationDate;
    }


    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }
}
