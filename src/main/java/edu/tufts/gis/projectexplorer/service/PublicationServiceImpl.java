package edu.tufts.gis.projectexplorer.service;

import edu.tufts.gis.projectexplorer.domain.ProjectPackage;
import edu.tufts.gis.projectexplorer.domain.converter.ProjectPackageToSolrInputDocument;
import edu.tufts.gis.projectexplorer.domain.entity.Project;
import edu.tufts.gis.projectexplorer.exception.SolrPublicationException;
import edu.tufts.gis.projectexplorer.service.solr.SolrService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by cbarne02 on 4/21/15.
 */
@Service
public class PublicationServiceImpl implements PublicationService {
    @Autowired
    private SolrService solrService;

    @Autowired
    private ProjectPackageService projectPackageService;


    @Autowired
    private ProjectPackageToSolrInputDocument projectToSolrInputDocument;

    @Override
    public void publish(Project project) throws SolrPublicationException {
        //covert Project to ProjectRecord
        try {

            ProjectPackage projectPackage = projectPackageService.wrapPackage(project);
            solrService.updateRecord(projectToSolrInputDocument.convert(projectPackage));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SolrPublicationException(e.getMessage());
        }
    }



    @Override
    public void publishAll() { //TODO: should this publish everything, or only everything marked publish. if the former,
    // we should also set the property in the repository
        for (Project project: projectPackageService.findAllProjects()){
            try {

                publish(project);
            } catch (SolrPublicationException e) {
                e.printStackTrace();
                //TODO: we should do something here; what types of errors should be fatal?
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void unpublish(Project project) throws SolrPublicationException {
        try {
            solrService.deleteRecord(project.getId().toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SolrPublicationException(e.getMessage());
        }
    }

    @Override
    public void unpublish(ProjectPackage projectPackage) throws SolrPublicationException {
        try {
            solrService.deleteRecord(projectPackage.getProject().getId().toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SolrPublicationException(e.getMessage());
        }
    }

    @Override
    public void unpublishAll() { //TODO: we should also set the property in the repository
        try {
            solrService.deleteAll();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void auditPublished() {

    }

    @Override
    public void reconcile() {

    }
}
