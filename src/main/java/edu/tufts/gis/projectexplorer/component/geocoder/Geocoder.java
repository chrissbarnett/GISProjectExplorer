package edu.tufts.gis.projectexplorer.component.geocoder;

import edu.tufts.gis.projectexplorer.domain.converter.GeocodeInfoToCollated;
import edu.tufts.gis.projectexplorer.domain.geocode.*;
import edu.tufts.gis.projectexplorer.service.geocode.ClavinGeocoderService;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

/**
 * Created by cbarne02 on 5/11/15.
 */

/**
 * sample response:
 *
 * [{"record":{"geonameID":3670218,"name":"San Andrés","asciiName":"San Andres","preferredName":"San Andrés",
 * "latitude":12.58472,"longitude":-81.70056,"featureClass":"P","featureCode":null,"primaryCountryCode":"CO",
 * "admin1Code":"25","admin2Code":"","admin3Code":"","admin4Code":"","population":58257,"timezone":"America/Bogota"},
 * "location":{"text":"San Andres","position":191},"matchedName":"San Andres"},
 * {"record":{"geonameID":3670218,"name":"San Andrés","asciiName":"San Andres","preferredName":"San Andrés",
 * "latitude":12.58472,"longitude":-81.70056,"featureClass":"P","featureCode":null,"primaryCountryCode":"CO",
 * "admin1Code":"25","admin2Code":"","admin3Code":"","admin4Code":"","population":58257,"timezone":"America/Bogota"},
 * "location":{"text":"San Andres","position":926},"matchedName":"San Andres"},
 * {"record":{"geonameID":3686110,"name":"Republic of Colombia","asciiName":"Republic of Colombia",
 * "preferredName":"Colombia","latitude":4.0,"longitude":-73.25,"featureClass":"A","featureCode":null,
 * "primaryCountryCode":"CO","admin1Code":"00","admin2Code":"","admin3Code":"","admin4Code":"","population":47790000,
 * "timezone":"America/Bogota"},"location":{"text":"Colombia","position":4505},"matchedName":"Colombia"},
 * {"record":{"geonameID":3686110,"name":"Republic of Colombia","asciiName":"Republic of Colombia",
 * "preferredName":"Colombia","latitude":4.0,"longitude":-73.25,"featureClass":"A","featureCode":null,
 * "primaryCountryCode":"CO","admin1Code":"00","admin2Code":"","admin3Code":"","admin4Code":"","population":47790000,
 * "timezone":"America/Bogota"},"location":{"text":"Colombia","position":6132},"matchedName":"Colombia"},
 * {"record":{"geonameID":3686110,"name":"Republic of Colombia","asciiName":"Republic of Colombia",
 * "preferredName":"Colombia","latitude":4.0,"longitude":-73.25,"featureClass":"A","featureCode":null,
 * "primaryCountryCode":"CO","admin1Code":"00","admin2Code":"","admin3Code":"","admin4Code":"","population":47790000,
 * "timezone":"America/Bogota"},"location":{"text":"Colombia","position":6307},"matchedName":"Colombia"},
 * {"record":{"geonameID":3686110,"name":"Republic of Colombia","asciiName":"Republic of Colombia",
 * "preferredName":"Colombia","latitude":4.0,"longitude":-73.25,"featureClass":"A","featureCode":null,
 * "primaryCountryCode":"CO","admin1Code":"00","admin2Code":"","admin3Code":"","admin4Code":"","population":47790000,
 * "timezone":"America/Bogota"},"location":{"text":"Colombia","position":8176},"matchedName":"Colombia"}]
 */



public class Geocoder {
    @Autowired
    private ClavinGeocoderService clavinGeocoderService;

    @Autowired
    private GeocodeInfoToCollated geocodeInfoToCollated;

    private Set<CollatedGeocodeInfo> collatedGeocodeInfo;
    private List<GeocodeInfo> geocodeInfo;
    private boolean duplicatesCollated = false;

    private static final Logger log = LoggerFactory.getLogger(Geocoder.class);


    public void geocode(String text) throws IOException, HttpException {
        geocodeInfo =  clavinGeocoderService.geocode(text);
    }

    public List<GeocodeInfo> getRawGeocodeInfo(){
        return geocodeInfo;
    }

    public Set<CollatedGeocodeInfo> getGeocodeInfo(){
        if (!duplicatesCollated){
            handleDuplicateIDs();
        }

        return collatedGeocodeInfo;
    }

    public GeocodeSummary summarize(){
        //should cluster results by admin code, so that Bogata, Columbia & Columbia aren't necessarily separate results
        Set<CollatedGeocodeInfo> collated = getGeocodeInfo();
        GeocodeSummary summary = new GeocodeSummary();

        for (CollatedGeocodeInfo info: collated){
            if (isCountry(info)){
                summary.getCountries().add(info);
            } else if (isCity(info)){
                summary.getCities().add(info);
            } else if (isState(info)){
                summary.getStates().add(info);
            } else if (isContinent(info)){
                summary.getContinents().add(info);
            }
        }

        return summary;


    }

    private boolean isContinent(CollatedGeocodeInfo info) {
        return (Continent.getContinent(info.getRecord().getGeonameID().intValue()) != null);
    }

    private boolean isState(CollatedGeocodeInfo info) {
        Record record = info.getRecord();
        String admin1 = record.getAdmin1Code();
        return !admin1.equals("00") && !admin1.isEmpty() && record.getFeatureClass().equals("A");
    }

    private boolean isCity(CollatedGeocodeInfo info) {
        return info.getRecord().getFeatureClass().equals("P");
    }

    private boolean isCountry(CollatedGeocodeInfo info) {
        return info.getRecord().getAdmin1Code().equals("00");
    }

    private void handleDuplicateIDs(){
        collatedGeocodeInfo = new HashSet<>();
        //cluster by geonames id
        for (GeocodeInfo info: geocodeInfo){
            int matches = 0;
            Long currentId = info.getRecord().getGeonameID();
            for (CollatedGeocodeInfo collated: collatedGeocodeInfo){

                if (collated.getRecord().getGeonameID().compareTo(currentId) == 0){

                    Context context = getContextFromInfo(info);
                    collated.getContexts().add(context);

                    matches++;
                }
            }
            //no match
            if (matches == 0) {
                collatedGeocodeInfo.add(geocodeInfoToCollated.convert(info));
            }
        }
        duplicatesCollated = true;
    }

    private Context getContextFromInfo(GeocodeInfo info) {
        Context context = new Context();
        context.setMatchedName(info.getMatchedName());
        context.setPosition(info.getLocation().getPosition());
        context.setText(info.getLocation().getText());
        return context;
    }



}
