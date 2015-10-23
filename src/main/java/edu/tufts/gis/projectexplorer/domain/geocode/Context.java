package edu.tufts.gis.projectexplorer.domain.geocode;

/**
 * Created by cbarne02 on 5/12/15.
 */
public class Context extends Location {
    private String matchedName;


    public String getMatchedName() {
        return matchedName;
    }

    public void setMatchedName(String matchedName) {
        this.matchedName = matchedName;
    }
}
