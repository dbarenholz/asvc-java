package com.dbarenholz.asvc.vocabitem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// import javax.sound.sampled.Clip;
import java.io.File;

/**
 *
 */
public class VocabItem {
    private static final Logger logger = LogManager.getLogger();

    // VocabItem defaults
    private String kanji = "";
    private String kana = "";
    private String romaji = "";
    private String translation = "";
    private String sentenceJP = "";
    private String sentenceEN = "";
    private File audioLocation = null;
    private File accentLocation = null;

    // TODO Add clip and image in VocabItem directly.

    // private Clip audio = null;
    // private Image accent = null;

    // === constructors === //
    public VocabItem(String kanji, String kana, String romaji, String translation, String sentenceJP, String sentenceEN, File audioLocation, File accentLocation) {
        this.kanji = kanji;
        this.kana = kana;
        this.romaji = romaji;
        this.translation = translation;
        this.sentenceJP = sentenceJP;
        this.sentenceEN = sentenceEN;
        this.audioLocation = audioLocation;
        this.accentLocation = accentLocation;
        // this.audio = audio;
        // this.accent = accent;

        logger.info("Created VocabItem {}", this.toString());
    }

    public VocabItem(String kanji, String kana) {
        this.kanji = kanji;
        this.kana = kana;
    }

    // == getters == //
    public String getKanji() {
        return kanji;
    }

    public String getKana() {
        return kana;
    }

    public String getRomaji() {
        return romaji;
    }

    public String getTranslation() {
        return translation;
    }

    public String getSentenceJP() {
        return sentenceJP;
    }

    public String getSentenceEN() {
        return sentenceEN;
    }

    public File getAudioLocation() {
        return audioLocation;
    }

    public File getAccentLocation() {
        return accentLocation;
    }

    // == property values == //


    /**
     * Returns string version of a vocab item as (Kanji, Translation).
     *
     * @return string of vocab item
     */
    @Override
    public String toString() {
        return "(" + getKanji() + ", " + getTranslation() + ")";
    }

    /**
     * Tests equals based on Kanji.
     *
     * @param other object to test equality with
     * @return {@code true} if {@code other === this}, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof VocabItem) {
            return getKanji().equals(((VocabItem) other).getKanji());
        } else {
            return false;
        }
    }
}
