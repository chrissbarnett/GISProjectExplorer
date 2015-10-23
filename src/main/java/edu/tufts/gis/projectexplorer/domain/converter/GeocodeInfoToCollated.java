package edu.tufts.gis.projectexplorer.domain.converter;

import edu.tufts.gis.projectexplorer.domain.geocode.CollatedGeocodeInfo;
import edu.tufts.gis.projectexplorer.domain.geocode.Context;
import edu.tufts.gis.projectexplorer.domain.geocode.GeocodeInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cbarne02 on 5/12/15.
 */
@Component
public class GeocodeInfoToCollated implements Converter<GeocodeInfo, CollatedGeocodeInfo> {

    @Override
    public CollatedGeocodeInfo convert(GeocodeInfo geocodeInfo) {
        CollatedGeocodeInfo collated = new CollatedGeocodeInfo();
        collated.setRecord(geocodeInfo.getRecord());
        Context context = new Context();
        context.setMatchedName(geocodeInfo.getMatchedName());
        context.setPosition(geocodeInfo.getLocation().getPosition());
        context.setText(geocodeInfo.getLocation().getText());
        Set<Context> contextSet = new HashSet<Context>();
        contextSet.add(context);
        collated.setContexts(contextSet);
        return collated;
    }
}
