package edu.tufts.gis.projectexplorer.component;

import edu.tufts.gis.projectexplorer.component.textprocessing.TextExtractorManager;
import edu.tufts.gis.projectexplorer.domain.*;
import edu.tufts.gis.projectexplorer.domain.entity.Project;
import org.apache.tika.sax.Link;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cbarne02 on 5/12/15.
 */
public class Analyzer {

    @Autowired
    private ResourceDirectory resourceDirectory;

    @Autowired
    private TextExtractorManager textExtractorManager;

    private Project project = null;

    private Path posterPath = null;

    private Path paperPath = null;

    private DocumentExtract posterExtract;

    private DocumentExtract paperExtract;



  /*  private Path getPosterPath(){
        Long projectId = project.getId();
        if (posterPath == null){
            posterPath = resourceDirectory.getResourcePath(projectId, ResourceType.poster_full_pdf.getResourceName());
        }
        return posterPath;
    }

    private Path getPaperPath() throws Exception {
        Long projectId = project.getId();
        String format = project.getPaper().getFormat();
        if (paperPath == null){
            paperPath = resourceDirectory.getResourcePath(projectId, ResourceType.getPaperType(format).getResourceName());
        }

        return paperPath;
    }


    public Project analyze(Project project) throws Exception {
        this.project = project;

        posterExtract = textExtractorManager.createTextExtractor().parse(getPosterPath());

        Poster poster = project.getPoster();
        poster.setText(posterExtract.getText());
        List<String> links = project.getReference_links();

        links.addAll(parseLinks(posterExtract.getLinks()));

        Paper paper = project.getPaper();
        paperExtract = textExtractorManager.createTextExtractor().parse(getPaperPath());

        paper.setText(paperExtract.getText());
        links.addAll(parseLinks(paperExtract.getLinks()));

        return project;
    }

    //send an analysis query to solr. add the field type using the schema api, populate synonyms, etc.
    //should filter out non-matches

    private List<String> parseLinks(List<Link> links){
        List<String> link$ = new ArrayList<>();
        for (Link link: links){
            link$.add(link.getUri());
        }
        return link$;
    }

*/

}
