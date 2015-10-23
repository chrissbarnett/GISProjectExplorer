package edu.tufts.gis.projectexplorer.domain.geocode;

/**
 * Created by cbarne02 on 4/21/15.
 *
 * GeoCode info as it is returned from the clavin server
 */

/*

[
    {
        "matchedName": "Texas",
        "location": {
            "position": 1239,
            "text": "Texas"
        },
        "record": {
            "preferredName": "Texas",
            "admin1Code": "TX",
            "admin2Code": "",
            "admin3Code": "",
            "admin4Code": "",
            "latitude": 31.25044,
            "name": "Texas",
            "primaryCountryCode": "US",
            "featureCode": null,
            "asciiName": "Texas",
            "longitude": -99.25060999999999,
            "timezone": "America/Chicago",
            "geonameID": 4736286,
            "featureClass": "A",
            "population": 22875689
        }
    }
]


 */
public class GeocodeInfo {
    private String matchedName;
    private Location location;
    private Record record;

    public String getMatchedName() {
        return matchedName;
    }

    public void setMatchedName(String matchedName) {
        this.matchedName = matchedName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }


}




