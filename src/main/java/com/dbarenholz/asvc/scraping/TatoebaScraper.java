package com.dbarenholz.asvc.scraping;

import com.dbarenholz.asvc.Settings;
import com.dbarenholz.asvc.cache.Cache;
import com.dbarenholz.asvc.exceptions.DownloadException;
import com.dbarenholz.asvc.exceptions.TatoebaDownloadException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class TatoebaScraper extends Cache {
    //<editor-fold desc="Singleton">
    private static final TatoebaScraper tInstance = new TatoebaScraper();

    private TatoebaScraper() {
    }

    public static TatoebaScraper getTatoeba() {
        return tInstance;
    }
    //</editor-fold>

    // Identifier for tatoeba scraper
    private static final String ID = "tatoeba";

    // page identifier used in retrieval
    private static final String PAGE_IDENTIFIER = ID + " page of ";

    // sentence number to download
    private static int sentenceNumber;

    // enum decides which query to do
    private enum queryType {
        SENTENCE, ID
    }

    // type is used to switch on
    private static queryType type;

    /**
     * For some word, retrieves the ID of a sentence.
     *
     * @param wordString word to retrieve sentence ID for
     * @return ID of sentence of given word
     * @throws TatoebaDownloadException if something goes wron with downloading
     */
    private Integer retrieveSentenceID(String wordString) throws TatoebaDownloadException {
        // Set query type
        type = queryType.ID;

        // Set pageIdentifier string
        String sentenceIDPageIdentifier = "ID of " + PAGE_IDENTIFIER + wordString;

        // Retrieve Document
        Document pageDoc;
        try {
            pageDoc = retrieve(sentenceIDPageIdentifier, wordString);
        } catch (DownloadException e) {
            throw new TatoebaDownloadException("Cannot retrieve data for word: " + wordString);
        }

        // Parse HTML and find integer to return.
        return Integer.parseInt(
                Objects.requireNonNull(pageDoc).select("md-subheader a")
                        .first()
                        .text()
                        .replaceAll("#", "")
        );
    }

    /**
     * Retrieves a sentence for a word.
     *
     * @param wordString word to retrieve sentence for
     * @return sentence
     * @throws TatoebaDownloadException if something goes wrong when downloading
     */
    public String retrieveSentence(String wordString) throws TatoebaDownloadException {

        // Set integer to correct number
        sentenceNumber = retrieveSentenceID(wordString);

        // Set query type
        type = queryType.SENTENCE;

        // Set pageIdentifier string
        String sentencePageIdentifier = "Sentence of " + PAGE_IDENTIFIER + wordString;

        // Retrieve Document
        Document pageDoc;
        try {
            pageDoc = retrieve(sentencePageIdentifier, wordString);
        } catch (DownloadException e) {
            throw new TatoebaDownloadException("Cannot retrieve data for word: " + wordString);
        }

        //JP div.sentence div.text
        //EN div.translation div.text

        return Objects.requireNonNull(pageDoc)
                .select("span.markup")
                .first().text()
                .replaceAll("｛", "\\[")
                .replaceAll("｝", "\\]");
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
                switch (type) {
                    case ID:
                        response = getHTTPResponse(
                                Settings.tatoeba + "eng/sentences/search?query=\"" + wordString +
                                        "\"&from=jpn&to=eng&orphans=no&unapproved=no&user=&tags=&list=&has_audio=" +
                                        "&trans_filter=limit&trans_to=eng&trans_link=&trans_user=&trans_orphan=no" +
                                        "&trans_unapproved=no&trans_has_audio=&sort=random"
                        );
                        break;
                    case SENTENCE:
                        response = getHTTPResponse(
                                Settings.tatoeba + "eng/sentences/show/" + sentenceNumber
                        );
                        break;
                    default:
                        response = null;
                        break;
                }
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
                    String.valueOf(StandardCharsets.UTF_8),
                    Settings.tatoeba
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
