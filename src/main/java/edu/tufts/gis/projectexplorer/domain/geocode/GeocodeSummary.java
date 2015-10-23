package edu.tufts.gis.projectexplorer.domain.geocode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cbarne02 on 5/12/15.
 */
public class GeocodeSummary {
    Set<CollatedGeocodeInfo> continents = new HashSet<>();
    Set<CollatedGeocodeInfo> countries = new HashSet<>();
    Set<CollatedGeocodeInfo> states = new HashSet<>();
    Set<CollatedGeocodeInfo> cities = new HashSet<>();

    public Set<CollatedGeocodeInfo> getContinents() {
        return continents;
    }

    public void setContinents(Set<CollatedGeocodeInfo> continents) {
        this.continents = continents;
    }

    public Set<CollatedGeocodeInfo> getCountries() {
        return countries;
    }

    public void setCountries(Set<CollatedGeocodeInfo> countries) {
        this.countries = countries;
    }

    public Set<CollatedGeocodeInfo> getStates() {
        return states;
    }

    public void setStates(Set<CollatedGeocodeInfo> states) {
        this.states = states;
    }

    public Set<CollatedGeocodeInfo> getCities() {
        return cities;
    }

    public void setCities(Set<CollatedGeocodeInfo> cities) {
        this.cities = cities;
    }
}
