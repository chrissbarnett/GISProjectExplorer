package edu.tufts.gis.projectexplorer.domain.geocode;

/**
 * Created by cbarne02 on 5/12/15.
 * part of the response from the Clavin server
 */
public class Location {
    private Long position;
    private String text;

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
