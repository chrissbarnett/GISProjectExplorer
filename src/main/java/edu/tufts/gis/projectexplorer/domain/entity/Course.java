package edu.tufts.gis.projectexplorer.domain.entity;

import edu.tufts.gis.projectexplorer.domain.Semester;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by cbarne02 on 4/15/15.
 */
@Entity
public class Course {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "COURSE_ID", columnDefinition = "BINARY(16)")
    public UUID id;
    //courses metadata

    //courses name
    public String name;

    //semester held
    @Enumerated(EnumType.STRING)
    public Semester semester;

    //year held
    public String year;

    public String instructor;

    //school & department?


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
