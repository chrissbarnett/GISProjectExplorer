package edu.tufts.gis.projectexplorer.service.solr;

import edu.tufts.gis.projectexplorer.exception.UnrecognizedFileTypeException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by cbarne02 on 4/21/15.
 */
@Service
public class SolrServiceImpl implements SolrService {
    private static final int QUEUE_SIZE = 4;
    private static final int THREAD_COUNT = 2;

    @Value("${solr.url}")
    private String solrUrl; //inject from properties

    private HttpSolrClient solr = null;

    private HttpSolrClient getSolrClient(){
        //lazy load.
        if (solr == null) {
            solr = new HttpSolrClient(solrUrl);
        }

        return solr;

    }

    @Override
    public boolean updateRecord(SolrInputDocument solrInputDocument) throws IOException, SolrServerException {
        HttpSolrClient client = getSolrClient();

        UpdateResponse response = client.add(solrInputDocument);
        boolean status = checkResponse(response);
        //TODO: check on commit() options
        client.commit();
        return status;

    }

    @Override
    public boolean deleteRecord(String id) throws IOException, SolrServerException {
        HttpSolrClient client = getSolrClient();
        UpdateResponse response = client.deleteById(id);
        boolean status = checkResponse(response);
        client.commit();
        return status;
    }

    @Override
    public boolean deleteAll() throws IOException, SolrServerException {
        HttpSolrClient client = getSolrClient();
        UpdateResponse response = client.deleteByQuery("*:*");
        boolean status = checkResponse(response);

        client.commit();

        return status;
    }

    //TODO: should this be on a schedule, or after x updates?
    @Override
    public void optimize() throws IOException, SolrServerException {
        getSolrClient().optimize(); //TODO: use the conncurrentupdatesolrserver instead?
    }

    @Override
    public NamedList<Object> extract(File file) throws IOException, SolrServerException {
        HttpSolrClient client = getSolrClient();
        ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");

        String fileType = getFileType(file);
        req.addFile(file, fileType);
        req.setParam("extractOnly", "true");
        NamedList<Object> result = client.request(req);
        return result;
    }



    private String getFileType(File file) throws UnrecognizedFileTypeException {
        String name = file.getName();
        if (name.toLowerCase().endsWith(".pdf")){
            return "pdf";
        } else if (name.toLowerCase().endsWith(".doc")){
            return "doc";
        } else if (name.toLowerCase().endsWith(".docx")){
            return "docx";
        } else {
            throw new UnrecognizedFileTypeException("Problem analyzing" + name
                    + ": Unable to analyze files of this type.");
        }
    }


    private boolean checkResponse(UpdateResponse updateResponse){
        boolean status = false;

        if (updateResponse.getStatus() == 200){
            //TODO: does a 200 guarantee success of the update?
            status = true;
        }

        return status;
    }
}
