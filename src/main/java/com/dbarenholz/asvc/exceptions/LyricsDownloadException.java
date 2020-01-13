package com.dbarenholz.asvc.exceptions;

/**
 * Specific exception for LyricalNonsense related issues.
 */
public class LyricsDownloadException extends DownloadException {
    LyricsDownloadException(String message) {
        super(message);
    }
}
