package edu.tufts.gis.projectexplorer.controller;

import edu.tufts.gis.projectexplorer.component.ResourceDirectory;
import edu.tufts.gis.projectexplorer.domain.entity.ProjectResource;
import edu.tufts.gis.projectexplorer.repository.ProjectResourceRepository;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Created by cbarne02 on 5/1/15.
 */
@Controller
public class ProjectResourceController {

    @Autowired
    private ResourceDirectory resourceDirectory;

    @Autowired
    private ProjectResourceRepository resourceRepository;

//http://localhost:8080/api/projectResources/32f6d340-fb36-11e4-b939-0800200c9a66/poster.pdf
    @RequestMapping(value = "/api/projectResources/{resourceId}/{resourceName:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> getProjectResource(@PathVariable("resourceId") UUID resourceId,
                                                                  @PathVariable("resourceName") String resourceName) throws Exception {


        Path path = resourceDirectory.getResourcePath(resourceId, resourceName);
        if (!Files.exists(path)){

        }
        return ResponseEntity.ok()
                .contentLength( Files.size(path))
                .contentType(MediaType.parseMediaType(getMimeType(resourceName)))
                .body(new InputStreamResource(Files.newInputStream(path)));


    }

    private String getMimeType(String resourceName) throws IOException {
        String mimeType = "";
        if (resourceName.endsWith(".png")){
            mimeType = "image/png";
        } else if (resourceName.endsWith(".pdf")){
            mimeType = "application/pdf";
        } else {
            throw new IOException("Unrecognized Media type");
        }

        return mimeType;
    }

}
