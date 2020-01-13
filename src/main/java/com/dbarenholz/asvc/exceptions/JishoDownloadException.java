package com.dbarenholz.asvc.exceptions;

/**
 * Specific exception for Jisho related issues.
 */
public class JishoDownloadException extends DownloadException {
    public JishoDownloadException(String message) {
        super(message);
    }
}
