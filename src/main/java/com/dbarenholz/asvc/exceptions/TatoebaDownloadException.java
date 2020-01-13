package com.dbarenholz.asvc.exceptions;

/**
 * Specific exception for Tatoeba related issues.
 */
public class TatoebaDownloadException extends DownloadException {
    public TatoebaDownloadException(String message) {
        super(message);
    }
}
