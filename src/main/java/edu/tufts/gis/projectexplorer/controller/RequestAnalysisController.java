package edu.tufts.gis.projectexplorer.controller;

import edu.tufts.gis.projectexplorer.component.ResourceDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by cbarne02 on 4/22/15.
 *
 *
 */
@Controller
public class RequestAnalysisController {
    @Autowired
    private ResourceDirectory resourceDirectory;


    @RequestMapping(value = "/analyze/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public String analyze() {



        return "success";
    }



}
