package com.dbarenholz.asvc.scraping;

import com.dbarenholz.asvc.Settings;
import com.dbarenholz.asvc.cache.Cache;
import com.dbarenholz.asvc.exceptions.DownloadException;
import com.dbarenholz.asvc.exceptions.JishoDownloadException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Jisho scraper.
 *
 * TODO: Explain what is scraped with this scraper, and how it works.
 */
public class JishoScraper extends Cache {
    private static final JishoScraper jInstance = new JishoScraper();

    private JishoScraper() {
    }

    public static JishoScraper getJisho() {
        return jInstance;
    }


    private static final String ID = "jisho";
    private static String PAGE_IDENTIFIER = ID + " vocabulary page of ";

    /**
     * Retrieves the meaning of a word.
     *
     * @param wordString word to retrieve meaning for.
     * @return meaning of said word
     * @throws JishoDownloadException if something goes wrong when downloading
     */
    public String retrieveMeaning(String wordString) throws JishoDownloadException {
        String meaningPageIdentifier = PAGE_IDENTIFIER + wordString;
        Document pageDoc;

        try {
            pageDoc = retrieve(meaningPageIdentifier, wordString);
        } catch (DownloadException e) {
            throw new JishoDownloadException("Cannot retrieve data for word: " + wordString);
        }

        return Objects.requireNonNull(pageDoc).select("span.meaning-meaning").first().text();
    }

    /**
     * Retrieves the reading of a word.
     *
     * @param wordString word to retrieve reading for
     * @return reading of said word
     * @throws JishoDownloadException if something goes wrong when downloading
     */
    public String retrieveReading(String wordString) throws JishoDownloadException {
        String readingPageIdentifier = PAGE_IDENTIFIER + wordString;
        Document pageDoc;

        try {
            pageDoc = retrieve(readingPageIdentifier, wordString);
        } catch (DownloadException e) {
            throw new JishoDownloadException("Cannot retrieve data for word: " + wordString);
        }

        return Objects.requireNonNull(pageDoc).select("span.furigana").first().text();
    }

    /**
     * Retrieves a page from cache if it exists, or otherwise downloads it,
     * with {@code pageIdentifier} in its name, for some word {@code wordString}.
     *
     * @param pageIdentifier page for which a Document should be retrieved.
     * @param wordString     the word for which a Document should be retrieved.
     * @return Document from cache.
     * @throws DownloadException if something goes wrong
     */
    @Override
    public Document retrieve(String pageIdentifier, String wordString) throws DownloadException {
        // If data has not yet been downloaded
        if (notYetDownloaded(pageIdentifier)) {

            // Retrieve a response based on which site
            Connection.Response response;

            try {
                response = getHTTPResponse(Settings.jisho + "word/" + wordString);
            } catch (IOException e) {
                throw new DownloadException(
                        "Cannot retrieve response from website.",
                        pageIdentifier,
                        e
                );
            }

            checkResponse(response, pageIdentifier);

        }

        Document pageDoc;

        try {
            pageDoc = Jsoup.parse(
                    getPage(pageIdentifier),
                    String.valueOf(UTF_8),
                    Settings.jisho
            );
        } catch (IOException e) {
            throw new DownloadException(
                    "Cannot parse response from website.",
                    pageIdentifier,
                    e
            );
        }

        return pageDoc;
    }
}