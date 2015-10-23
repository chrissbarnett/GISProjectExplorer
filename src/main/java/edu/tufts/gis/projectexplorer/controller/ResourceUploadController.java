package edu.tufts.gis.projectexplorer.controller;

import edu.tufts.gis.projectexplorer.component.StorageHandlerManager;
import edu.tufts.gis.projectexplorer.domain.*;

import edu.tufts.gis.projectexplorer.domain.entity.ProjectResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;


/**
 * Created by cbarne02 on 4/30/15.
 */
@Controller
public class ResourceUploadController {
    private static final Logger log = LoggerFactory.getLogger(ResourceUploadController.class);

    @Autowired
    private StorageHandlerManager storageHandlerManager;
///api/projectsResources/{resourceId}/{resourceName:.+}"

    @RequestMapping(value = "api/projects/{projectId}/projectResources/poster", method = RequestMethod.POST)
    public @ResponseBody
    ProjectResource handleFileUploadUpdate(@RequestParam("file") MultipartFile multipartFile,
                                           @PathVariable("projectId") UUID projectId) throws Exception {

        return handleResourceUpdate(multipartFile, ResourceType.poster_full_pdf, projectId);

    }

    @RequestMapping(value = "api/projectResources/posters", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,String> handlePosterUploadNew(@RequestParam("file") MultipartFile multipartFile) throws Exception {

        return handleResourceUpload(multipartFile, ResourceType.poster_full_pdf);

    }

    @RequestMapping(value = "api/projectResources/papers", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,String> handlePaperUploadNew(@RequestParam("file") MultipartFile multipartFile) throws Exception {

        return handleResourceUpload(multipartFile, ResourceType.getPaperType(getFileExtension(multipartFile)));

    }



    @RequestMapping(value = "api/projects/{projectId}/projectResources/paper", method = RequestMethod.POST)
    public @ResponseBody
    ProjectResource handleFileUpload(@RequestParam("file") MultipartFile multipartFile,
                                     @PathVariable("projectId") UUID projectId) throws Exception {

        return handleResourceUpdate(multipartFile, ResourceType.getPaperType(getFileExtension(multipartFile)), projectId);

    }

    private Map<String,String> handleResourceUpload(MultipartFile multipartFile, ResourceType resourceType) throws IOException {
        if (multipartFile == null) {
            throw new IOException("Uploaded file is null!");
        }

        ProjectResource projectResource = storageHandlerManager.createStorageHandler()
                .storeResource(multipartFile, resourceType);

        //return a json object with the resource id.
        Map<String,String> response = new HashMap<>();
        response.put("resourceId", projectResource.getId().toString());
        return response;

    }

    private ProjectResource handleResourceUpdate(MultipartFile multipartFile, ResourceType resourceType, UUID projectId) throws IOException {
        if (multipartFile == null) {
            throw new IOException("Uploaded file is null!");
        }

        return storageHandlerManager.createStorageHandler()
                .storeResource(multipartFile, resourceType, projectId);

    }


    private String getFileExtension(MultipartFile file){
        String name = file.getOriginalFilename();
        String ext = name.substring(name.lastIndexOf("."));
        return ext;
    }


}
