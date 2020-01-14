package com.dbarenholz.asvc.scraping;

import com.dbarenholz.asvc.Settings;
import com.dbarenholz.asvc.cache.Cache;
import com.dbarenholz.asvc.exceptions.DownloadException;
import com.dbarenholz.asvc.exceptions.JishoDownloadException;
import com.dbarenholz.asvc.exceptions.LyricsDownloadException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

public class LyricalNonsenseScraper extends Cache {

    private static final LyricalNonsenseScraper lnInstance = new LyricalNonsenseScraper();

    private LyricalNonsenseScraper() {
    }

    public static LyricalNonsenseScraper getLyricalNonsense() {
        return lnInstance;
    }

    private static final String ID = "lyrical-nonsense";
    private static String PAGE_IDENTIFIER = ID + " lyrics page of ";

    public String getLyrics(String url) throws LyricsDownloadException {
        String urlSongName = url.split("/")[url.split("/").length - 1];
        String meaningPageIdentifier = PAGE_IDENTIFIER + urlSongName;
        Document pageDoc;

        try {
            pageDoc = retrieve(meaningPageIdentifier, url);
        } catch (DownloadException e) {
            throw new LyricsDownloadException("Cannot retrieve data for word: " + url);
        }

        return Objects.requireNonNull(pageDoc)
                .select(".ln-lyrics-content > div:nth-child(1)").first().text();
    }

    @Override
    public Document retrieve(String pageIdentifier, String url) throws DownloadException {
        // If data has not yet been downloaded
        if (notYetDownloaded(pageIdentifier)) {

            // Retrieve a response based on which site
            Connection.Response response;

            try {
                response = getHTTPResponse(url);
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
