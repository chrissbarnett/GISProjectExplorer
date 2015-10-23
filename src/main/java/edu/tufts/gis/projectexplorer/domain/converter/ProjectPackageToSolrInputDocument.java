package edu.tufts.gis.projectexplorer.domain.converter;

import edu.tufts.gis.projectexplorer.domain.ProjectPackage;
import edu.tufts.gis.projectexplorer.domain.entity.Course;
import edu.tufts.gis.projectexplorer.domain.entity.Project;
import edu.tufts.gis.projectexplorer.domain.entity.ProjectResource;
import edu.tufts.gis.projectexplorer.domain.entity.Student;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by cbarne02 on 4/21/15.
 *
 * Should convert from the Project entity class to SolrInputDocument reflective of desired Solr schema.
 *
 * documentation claims: Automatic registration of Converter, GenericConverter, Formatter beans.
 * TODO: check that this converter is autoregistered
 */

@Component
public final class ProjectPackageToSolrInputDocument implements Converter<ProjectPackage, SolrInputDocument> {
    private SolrInputDocument doc;
    //TODO: add expo winner

    @Override
    public SolrInputDocument convert(ProjectPackage projectPackage) {
        Project project = projectPackage.getProject();
        doc = new SolrInputDocument();
        doc.addField("id", project.getId().toString());

        addValue("title", project.getTitle());

        addMultiValue("key_theme", project.getKeywords_1());

        addMultiValue("key_application", project.getKeywords_2());

        addMultiValue("key_method", project.getKeywords_3());

        addMultiValue("geonames_id", project.getGeonamesIds());

        addMultiValue("key_place", project.getPlaces());

        addMultiValue("reference_url", project.getReference_links());

        addMultiValue("reference_text", project.getReference_text());



        flattenCourse(project.getCourse());

        List<Student> students = project.getStudents();
        int i = 0;
        for (Student student: students) {
            flattenStudent(student, i);
            i++;
        }

        Set<ProjectResource> resourceSet = projectPackage.getResourceSet();
        for (ProjectResource pr: resourceSet){
            if (pr.getResourceType().isPaper()){
                flattenResource(pr, "paper");
            } else if (pr.getResourceType().isPoster()){
                flattenResource(pr, "poster");
            }
        }

        return doc;
    }

    private void flattenCourse(Course course){
        if (course != null) {
            addValue("course_name", course.getName());
            addValue("course_instructor", course.getInstructor());
            addValue("course_semester", course.getSemester().name());
            addDateValue("course_year", course.getYear());
            //storing the id allows us to find other courses with the same id
            addValue("course_id", course.getId().toString());
        }

    }

    private void flattenStudent(Student student, int num){
        if (student != null) {
            addValue("student_name" + num, student.getName());
            addDateValue("student_year" + num, student.getYear());
            addValue("student_degree" + num, student.getDegree());
            addValue("student_school" + num, student.getSchool());
            addMultiValue("student_department" + num, student.getDepartments());
        }
    }

    private void flattenResource(ProjectResource resource, String schema_prefix){
        if (resource != null) {
            addValue(schema_prefix + "_url", resource.getUrl());
            addValue(schema_prefix + "_thumb_url", resource.getThumbUrl());
            addValue(schema_prefix + "_text", resource.getExtractedText());
        }
    }


    private void addMultiValue(String fieldName, Collection<String> collection){
        if (collection != null){
            for (String item: collection){
                doc.addField(fieldName, item);
            }
        }
    }

    private void addValue(String fieldName, String item){
        if (item != null && !item.isEmpty()){
            doc.addField(fieldName, item);
        }
    }

    private void addDateValue(String fieldName, String item){
        //YYYY-MM-DDThh:mm:ssZ //2000-01-01T01:01:01Z
        if (item != null && !item.isEmpty()){
            doc.addField(fieldName, item + "-01-01T01:01:01Z");
        }
    }
}
