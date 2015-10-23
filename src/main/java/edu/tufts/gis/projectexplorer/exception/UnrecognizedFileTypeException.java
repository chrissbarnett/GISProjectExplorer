package edu.tufts.gis.projectexplorer.exception;

import java.io.IOException;

/**
 * Created by cbarne02 on 4/21/15.
 */
public class UnrecognizedFileTypeException extends IOException {
    public UnrecognizedFileTypeException(String s) {
        super(s);
    }
}
