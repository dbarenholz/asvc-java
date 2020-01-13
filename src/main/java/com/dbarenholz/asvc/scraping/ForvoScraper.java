package com.dbarenholz.asvc.scraping;

import com.dbarenholz.asvc.Settings;
import com.dbarenholz.asvc.cache.Cache;
import com.dbarenholz.asvc.exceptions.DownloadException;
import com.dbarenholz.asvc.exceptions.ForvoDownloadException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

public class ForvoScraper extends Cache {
    //<editor-fold desc="Singleton">
    private static final ForvoScraper fInstance = new ForvoScraper();

    private ForvoScraper() {
    }

    public static ForvoScraper getForvo() {
        return fInstance;
    }
    //</editor-fold>

    private static final String ID = "forvo";
    // TODO: Convert to local when done
    private static String PAGE_IDENTIFIER = ID + " audio page of ";
    private static String AUDIO_URL = "https://audio00.forvo.com/audios/mp3/";

    /**
     * Makes a connection with the Forvo servers to download an audiofile.
     *
     * @param eParamDecoded decoded name to download
     * @param fileName      filename to write to
     * @throws IOException when something goes wrong.
     */
    private void downloadAudio(String eParamDecoded, String fileName) throws IOException {
        // https://audio00.forvo.com/audios/mp3/b/6/b6_8998474_76_434248_171524.mp3
        String forvoMP3URL = AUDIO_URL + eParamDecoded;
        // TODO when it works, replace this with Settings.mediaPath + fileName
        File file = new File(Settings.cachePath + fileName);

        URLConnection conn = new URL(forvoMP3URL).openConnection();
        try (InputStream is = conn.getInputStream()) {
            try (OutputStream outstream = new FileOutputStream(file)) {
                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) > 0) {
                    outstream.write(buffer, 0, len);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateCache(file);
    }

    /**
     * Retrieves an MP3 for a given word.
     *
     * @param wordString word to find MP3 for
     * @return name of downloaded MP3 file
     * @throws ForvoDownloadException if download fails.
     */
    public String retrieveAudio(String wordString) throws ForvoDownloadException {
        String finalFileName = "";
        String audioPageIdentifier = PAGE_IDENTIFIER + wordString;

        Document pageDoc;
        try {
            pageDoc = retrieve(audioPageIdentifier, wordString);
        } catch (DownloadException e) {
            throw new ForvoDownloadException("Cannot retrieve data for word: " + wordString);
        }

        // START COPY FROM MARTIJN (MODIFIED)
        for (Element playElement : pageDoc.getElementsByClass("play")) {
            String onClickText = playElement.attr("onclick");

            if (onClickText != null && onClickText.startsWith("Play(")) {
                onClickText = onClickText.substring(5);

                StringTokenizer s = new StringTokenizer(onClickText, ",");
                List<String> onClickTokens = new ArrayList<>();

                while (s.hasMoreTokens()) {
                    onClickTokens.add(s.nextToken());
                }

                if (onClickTokens.size() >= 5) {
                    String aParam = onClickTokens.get(0);
                    String eParam = onClickTokens.get(4);

                    if (eParam.startsWith("'") && eParam.endsWith("'") && eParam.length() > 2) {
                        eParam = eParam.substring(1, eParam.length() - 1);
                    }

                    if (eParam.equals("''")) {
                        continue;
                    }

                    String eParamDecoded = new String(Base64.getDecoder().decode(eParam));

                    if (eParamDecoded.contains("_76_")) {
                        finalFileName = "forvo" + aParam + ".mp3";

                        try {
                            downloadAudio(eParamDecoded, finalFileName);
                        } catch (IOException e) {
                            throw new ForvoDownloadException(
                                    "Cannot download audio from Forvo",
                                    audioPageIdentifier,
                                    e
                            );
                        }

                        break;
                    }
                }
            }
        }
        // END COPY FROM MARTIJN (MODIFIED)

        return finalFileName;
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
        if (notYetDownloaded(pageIdentifier)) {
            Connection.Response response;

            try {
                response = getHTTPResponse(Settings.forvo + "word/" + wordString + "/#ja");
            } catch (IOException e) {
                throw new DownloadException(
                        "Cannot retrieve response from website.",
                        pageIdentifier,
                        e);
            }

            checkResponse(response, pageIdentifier);
        }

        Document pageDoc;

        try {
            pageDoc = Jsoup.parse(
                    getPage(pageIdentifier),
                    String.valueOf(StandardCharsets.UTF_8),
                    Settings.forvo
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
