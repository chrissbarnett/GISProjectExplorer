package edu.tufts.gis.projectexplorer.component.wro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import ro.isdc.wro.extensions.model.factory.SmartWroModelFactory;
import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.model.factory.WroModelFactory;

import java.io.IOException;
import java.util.Properties;


/**
 * Created by cbarne02 on 4/24/15.
 *
 * Modifies wro4j to find configuration files using the Spring Resource abstraction.
 *
 * Spring Boot uses a non-standard path structure, so we need wro4j to find config files as Spring Resources, rather than
 * the default 'WEB-INF/'. See also { @edu.tufts.gis.projectexplorer.config.wro.WroWebConfig} to see where the
 * WroManagerFactory is plugged in.
 */
public class ResourceWroManagerFactory extends ConfigurableWroManagerFactory {
    private static final Logger log = LoggerFactory.getLogger(ResourceWroManagerFactory.class);
    private final Resource wroModel;
    private final Resource properties;

    public ResourceWroManagerFactory(Resource properties, Resource wroModel){
        this.wroModel = wroModel;
        this.properties = properties;
    }

    @Override
    protected WroModelFactory newModelFactory() {
        SmartWroModelFactory factory =  new SmartWroModelFactory();
        try {
            factory.setWroFile(wroModel.getFile());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failed to set wro.xml file!!!");
        }
        return (WroModelFactory) factory;
    }

    @Override
    protected Properties newConfigProperties() {
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(properties);
            return props;
        } catch (final Exception e) {
            log.warn("No configuration property file found. Using default values.", e);
        }
        return new Properties();
    }
}
