package edu.tufts.gis.projectexplorer.domain.geocode;

import java.util.Set;

/**
 * Created by cbarne02 on 4/21/15.
 *
 * a container for a geocode response where matches with the same geonameID are collated into a single object
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
public class CollatedGeocodeInfo {
    private Set<Context> contexts;
    private Record record;


    public Set<Context> getContexts() {
        return contexts;
    }

    public void setContexts(Set<Context> contexts) {
        this.contexts = contexts;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }



}




