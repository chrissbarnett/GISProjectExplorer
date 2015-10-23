package edu.tufts.gis.projectexplorer.service;

import edu.tufts.gis.projectexplorer.domain.ProjectPackage;
import edu.tufts.gis.projectexplorer.domain.entity.Project;
import edu.tufts.gis.projectexplorer.exception.SolrPublicationException;

/**
 * Created by cbarne02 on 4/21/15.
 */
public interface PublicationService {
    void publish(Project project) throws SolrPublicationException;

    void publishAll();

    void unpublish(Project project) throws SolrPublicationException;

    void unpublish(ProjectPackage projectPackage) throws SolrPublicationException;

    void unpublishAll();

    /**
     * compare the list of published Projects as listed in the repository vs. the list of published Projects in Solr
     */
    void auditPublished();

    /**
     * given the list of published Projects as listed in the repository vs. the list of published Projects in Solr,
     * repopulates Solr based on the repository list.
     */
    void reconcile();
}
