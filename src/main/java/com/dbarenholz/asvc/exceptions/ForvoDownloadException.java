package com.dbarenholz.asvc.exceptions;

/**
 * Specific exception for Forvo related issues.
 */
public class ForvoDownloadException extends DownloadException {
    public ForvoDownloadException(String message) {
        super(message);
    }

    public ForvoDownloadException(String message, String page, Exception e) {
        super(message, page, e);
    }

    public ForvoDownloadException(String message, String decodedParam, String fileName, Exception e) {
        super(message + "\n\tparam: " + decodedParam + "\n\tfilename: " + fileName, e);
    }
}
