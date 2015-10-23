package edu.tufts.gis.projectexplorer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;

@SpringBootApplication(exclude = {SolrAutoConfiguration.class})
public class GisProjectsApplication {

    private static final Logger log = LoggerFactory.getLogger(GisProjectsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GisProjectsApplication.class, args);
    }

}
