package edu.tufts.gis.projectexplorer.service.solr;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;

import java.io.File;
import java.io.IOException;

/**
 * Created by cbarne02 on 4/21/15.
 */
public interface SolrService {
    boolean updateRecord(SolrInputDocument solrInputDocument) throws IOException, SolrServerException;

    boolean deleteRecord(String id) throws IOException, SolrServerException;

    void optimize() throws IOException, SolrServerException;

    NamedList<Object> extract(File file) throws IOException, SolrServerException;

    boolean deleteAll() throws IOException, SolrServerException;
}
