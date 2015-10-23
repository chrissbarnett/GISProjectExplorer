package edu.tufts.gis.projectexplorer.controller;

import edu.tufts.gis.projectexplorer.repository.CourseRepository;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by cbarne02 on 4/22/15.
 *
 * currently unused
 */
public class CoursesController {

    private final CourseRepository courseRepository;

    @Autowired
    public CoursesController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public ModelAndView getCourses() {
        // Specify the view name
        return new ModelAndView("courses")
                // Look up ALL courses and wrap each with related links
                .addObject("courses", courseRepository.findAll());
    }



}
