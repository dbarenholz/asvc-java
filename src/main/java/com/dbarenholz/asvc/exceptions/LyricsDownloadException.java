package com.dbarenholz.asvc.exceptions;

/**
 * Specific exception for LyricalNonsense related issues.
 */
public class LyricsDownloadException extends DownloadException {
    public LyricsDownloadException(String message) {
        super(message);
    }
}
