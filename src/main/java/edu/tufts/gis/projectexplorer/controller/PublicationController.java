package edu.tufts.gis.projectexplorer.controller;

import edu.tufts.gis.projectexplorer.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by cbarne02 on 4/22/15.
 *
 * should be admin to access
 */
@Controller
public class PublicationController {

    private final PublicationService publicationService;

    @Autowired
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @RequestMapping(value = "/publish-all", method = RequestMethod.GET)
    @ResponseBody
    public String publish() {
        publicationService.unpublishAll();
        publicationService.publishAll();

        return "success";
    }

    @RequestMapping(value = "/unpublish-all", method = RequestMethod.GET)
    @ResponseBody
    public String unpublish() {
        publicationService.unpublishAll();

        return "success";
    }



}
