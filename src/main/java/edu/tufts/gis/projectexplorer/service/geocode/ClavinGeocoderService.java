package edu.tufts.gis.projectexplorer.service.geocode;

import edu.tufts.gis.projectexplorer.domain.geocode.GeocodeInfo;
import edu.tufts.gis.projectexplorer.service.AbstractJsonResponseService;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by cbarne02 on 4/21/15.
 */
@Service
public class ClavinGeocoderService extends AbstractJsonResponseService {
    @Value("${geocoder.url}")
    private String geocoder_url;

    /**
     * Geocodes the passed text string.
     *
     * This implementation POSTs text to a service that geocodes text using CLAVIN with a GeoNames index. The response
     * is a JSON object containing extracted metadata and associated GeoNames records.
     */
    public List<GeocodeInfo> geocode(String text) throws IOException, HttpException {
        return (List<GeocodeInfo>) sendPost(geocoder_url, text, GeocodeInfo.class);
    }


}
