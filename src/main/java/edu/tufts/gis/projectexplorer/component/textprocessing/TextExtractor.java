package edu.tufts.gis.projectexplorer.component.textprocessing;

import edu.tufts.gis.projectexplorer.domain.DocumentExtract;
import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by cbarne02 on 5/8/15.
 */
public class TextExtractor {
    private static final Logger log = LoggerFactory.getLogger(TextExtractor.class);

    public DocumentExtract parse(Path path) throws IOException, TikaException, SAXException {
        BodyContentHandler bHandler = new BodyContentHandler();
        LinkContentHandler lHandler = new LinkContentHandler();

        TeeContentHandler tHandler = new TeeContentHandler(bHandler, lHandler);

        DocumentExtract extract = new DocumentExtract(tHandler);
        AutoDetectParser parser = new AutoDetectParser();

        try (InputStream stream = Files.newInputStream(path)){
            parser.parse(stream, tHandler, extract.getMetadata());
            extract.setText(bHandler.toString());
            extract.setLinks(lHandler.getLinks());
            return extract;
        }
    }


}
