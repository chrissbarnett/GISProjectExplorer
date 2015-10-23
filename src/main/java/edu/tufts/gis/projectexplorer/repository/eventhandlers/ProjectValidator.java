package edu.tufts.gis.projectexplorer.repository.eventhandlers;

import edu.tufts.gis.projectexplorer.domain.entity.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by cbarne02 on 5/13/15.
 */
public class ProjectValidator implements Validator {
    private static final Logger log = LoggerFactory.getLogger(ProjectValidator.class);

    @Override
    public boolean supports(Class<?> aClass) {
        return Project.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {
        log.info("attempting to validate project");
        ValidationUtils.rejectIfEmpty(errors, "title", "title.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isSearchable", "isSearchable.empty");

    }
}
