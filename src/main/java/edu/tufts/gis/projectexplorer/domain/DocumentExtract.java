package edu.tufts.gis.projectexplorer.domain;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.Link;
import org.xml.sax.ContentHandler;

import java.util.List;

/**
 * Created by cbarne02 on 5/8/15.
 */
public class DocumentExtract {
    private Metadata metadata;
    private final ContentHandler contentHandler;

    private String text;
    private List<Link> links;

    public DocumentExtract(ContentHandler contentHandler){
        this.contentHandler = contentHandler;
        this.metadata = new Metadata();
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    /*public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }*/

    public ContentHandler getContentHandler() {
        return contentHandler;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText(){
        return text;
    }


}
