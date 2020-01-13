package com.dbarenholz.asvc.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;

/**
 * Exception class for downloading pages.
 */
public class DownloadException extends Exception {

    private static final Logger logger = LogManager.getLogger();

    DownloadException(String message) {
        super(message);
    }

    public DownloadException(String message, String page) {
        super(message + page);
    }


    public DownloadException(String message, String page, Connection.Response response) {
        logger.warn("{}\n\t" +
                "Queried page: {}\n\t" +
                "Response {}",
                message,
                page,
                response == null ? "null" : response.statusCode() + ": " + response.statusMessage());
    }

    public DownloadException(String message, String page, Exception e) {
        logger.warn("{}\n\t" +
                "Queried page: {}\n\t" +
                "Exception: {}\n\t" +
                "StackTrace: {}",
                message,
                page,
                e,
                e.getStackTrace()
        );
    }
}
