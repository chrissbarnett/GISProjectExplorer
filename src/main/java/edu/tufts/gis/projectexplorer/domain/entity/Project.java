package edu.tufts.gis.projectexplorer.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.tufts.gis.projectexplorer.repository.eventhandlers.ProjectEntityListener;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by cbarne02 on 4/15/15.
 */
@Entity
@EntityListeners(ProjectEntityListener.class)
public class Project {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "PROJECT_ID", columnDefinition = "BINARY(16)")
    private UUID id;

    private String title;

    //keywords level 1
    @ElementCollection(targetClass=String.class)
    private List<String> keywords_1 = new ArrayList<String>();

    //keywords level 2
    @ElementCollection(targetClass=String.class)
    private List<String> keywords_2 = new ArrayList<String>();

    //keywords level 3
    @ElementCollection(targetClass=String.class)
    private List<String> keywords_3 = new ArrayList<String>();

    //geonamesIds
    @ElementCollection(targetClass=String.class)
    private List<String> geonamesIds = new ArrayList<String>(); //do we expand this on ingest or get it on the fly?

    @ElementCollection(targetClass=String.class)
    private List<String> places = new ArrayList<String>();

    @ElementCollection(targetClass=String.class)
    private List<String> reference_links = new ArrayList<String>();

    @ElementCollection(targetClass=String.class)
    private List<String> reference_text = new ArrayList<String>();

    private Boolean searchable = false;

    //info about final poster
    @RestResource(exported = false)
    @JsonIgnore
    private UUID posterId;

    private Boolean winner = false;

    //info about final paper
    @RestResource(exported = false)
    @JsonIgnore
    private UUID paperId;

    //information about the courses the project was created for
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="COURSE_ID")
    private Course course;

    //information about the students who created the project
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="STUDENT_ID")
    @ElementCollection(targetClass=Student.class)
    private List<Student> students = new ArrayList<Student>();

    @JsonIgnore
    private Date lastModified;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public List<String> getKeywords_1() {
        return keywords_1;
    }

    public void setKeywords_1(List<String> keywords_1) {
        this.keywords_1 = keywords_1;
    }

    public List<String> getKeywords_2() {
        return keywords_2;
    }

    public void setKeywords_2(List<String> keywords_2) {
        this.keywords_2 = keywords_2;
    }

    public List<String> getKeywords_3() {
        return keywords_3;
    }

    public void setKeywords_3(List<String> keywords_3) {
        this.keywords_3 = keywords_3;
    }

    public List<String> getGeonamesIds() {
        return geonamesIds;
    }

    public List<String> getPlaces() {
        return places;
    }

    public void setPlaces(List<String> places) {
        this.places = places;
    }

    public void setGeonamesIds(List<String> geonamesIds) {
        this.geonamesIds = geonamesIds;
    }

    public List<String> getReference_links() {
        return reference_links;
    }

    public void setReference_links(List<String> reference_links) {
        this.reference_links = reference_links;
    }

    public List<String> getReference_text() {
        return reference_text;
    }

    public void setReference_text(List<String> reference_text) {
        this.reference_text = reference_text;
    }


    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public Boolean getWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }

    public UUID getPaperId() {
        return paperId;
    }

    public void setPaperId(UUID paperId) {
        this.paperId = paperId;
    }

    public UUID getPosterId() {
        return posterId;
    }

    public void setPosterId(UUID posterId) {
        this.posterId = posterId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
