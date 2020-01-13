package com.dbarenholz.asvc.cache;

import com.dbarenholz.asvc.App;
import com.dbarenholz.asvc.Settings;
import com.dbarenholz.asvc.exceptions.DownloadException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class implementing caching methods. APIs extend this class.
 *
 * @author Dani&euml;l Barenholz
 * @version %I%, %G%
 * @since 1.8
 */
public abstract class Cache {

    private static final Logger logger = LogManager.getLogger(); // logger

    /**
     * Attempts to connect to {@code URL} and receive a HTTP(S) response.
     *
     * @param URL website URL to connect to (e.g. http://www.google.com)
     * @return A HTTP(S) response from {@code URL}
     * @throws IOException if something went wrong with connecting to the website.
     */
    public Connection.Response getHTTPResponse(String URL) throws IOException {
        logger.debug("Connecting to {}...", URL);
        return Jsoup.connect(URL)
                .userAgent(Settings.agent)
                .timeout(Settings.timeout)
                .execute();
    }

    /**
     * Attempts to save the page by checking which HTTP(S) response has been returned by {@link #getHTTPResponse(String)}.
     *
     * @param response       HTTP(S) response to check
     * @param pageIdentifier unique identifier for which page was requested
     * @throws DownloadException when something went wrong during downloading (e.g. reponse code is not 200)
     */
    public void checkResponse(Connection.Response response, String pageIdentifier) throws DownloadException {
        // no response
        if (response == null) {
            throw new DownloadException("Cannot retrieve response from website with identifier: ", pageIdentifier);
        }

        // response OK
        if (response.statusCode() == 200) {
            try {
                savePage(response.parse(), pageIdentifier);
            } catch (IOException e) {
                throw new DownloadException(
                        "Cannot parse response from website.",
                        pageIdentifier,
                        e
                );
            }
        } else {
            // response not OK
            throw new DownloadException(
                    "Something went wrong with retrieving the response.",
                    pageIdentifier,
                    response
            );
        }
    }

    /**
     * Saves a page to disk located at {@code cachePath}.
     *
     * @param webpage  the page to save
     * @param fileName the name it should have
     * @throws IOException if an I/O error occurs writing to or creating the file
     */
    private void savePage(final Document webpage, final String fileName) throws IOException {
        final Path path = Paths.get(Settings.cachePath + fileName + ".html");
        Files.write(path, webpage.outerHtml().getBytes());
        logger.debug("Saving page to file {}...", path.getFileName().toString());
    }

    /**
     * Updates the internal representation of the cache.
     *
     * @param file the file for which the cache needs to be updated
     */
    public void updateCache(File file) {
        App.cache.add(file);
        logger.debug("Added file {} to cache", file.getName());
    }

    /**
     * Checks if a page for a specific identifier has been downloaded or not.
     *
     * @param pageIdentifier unique identifier for which page was requested
     * @return {@code true} if a file which contains {@code pageIdentifier} has not yet been downloaded, {@code false} otherwise.
     */
    public boolean notYetDownloaded(String pageIdentifier) {
        return App.cache.stream()
                .noneMatch(file -> file.getName().contains(pageIdentifier));
    }

    /**
     * Retrieves a (cached) page with a specific identifier.
     *
     * @param pageIdentifier unique identifier for which page was requested
     * @return A file with the pageIdentifier if it exists, {@code null} otherwise.
     */
    public File getPage(String pageIdentifier) {
        return App.cache.stream()
                .filter(file -> file.getName().contains(pageIdentifier))
                .findFirst().orElse(null);
    }

    /**
     * Retrieves a page from cache if it exists, or otherwise downloads it,
     * with {@code pageIdentifier} in its name, for some word {@code wordString}.
     *
     * @param pageIdentifier page for which a Document should be retrieved.
     * @param wordString     the word for which a Document should be retrieved.
     * @return A document from cache.
     * @throws DownloadException if something goes wrong when downloading
     */
    public abstract Document retrieve(String pageIdentifier, String wordString) throws DownloadException;
}
