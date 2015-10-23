package edu.tufts.gis.projectexplorer.config.wro;

import edu.tufts.gis.projectexplorer.component.wro.ResourceWroManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ro.isdc.wro.http.ConfigurableWroFilter;

import javax.servlet.DispatcherType;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Java-based Configuration for wro4j Servlet Filter. A custom WroManagerFactory is set to allow config files to be
 * found according to classpath, rather than at default "WEB-INF/"
 */
@Configuration
public class WroWebConfig {
    @Value("${wro_config.url_pattern}")
    private String wroUrlPattern;

    @Value("${wro_config.model}")
    private String wroModelPath;

    @Value("${wro_config.properties}")
    private String propertiesPath;

    private ConfigurableWroFilter createFilter(){
        ConfigurableWroFilter filter = new ConfigurableWroFilter();

        Resource properties = new ClassPathResource(propertiesPath);
        Resource wroModel = new ClassPathResource(wroModelPath);

        ResourceWroManagerFactory wroManagerFactory = new ResourceWroManagerFactory(properties, wroModel);
        filter.setWroManagerFactory(wroManagerFactory);
        return filter;
    }

    @Bean
    public FilterRegistrationBean wro4jFilter() throws IOException {
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setFilter(createFilter());

        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        registration.addUrlPatterns(wroUrlPattern);

        return registration;
    }

}