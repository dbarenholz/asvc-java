# asvc -- AnkiSongVocabCreator

## Introduction
asvc, AnkiSongVocabCreator, is a piece of software that attempts to make it easier to create Anki vocabulary decks from (Japanese) songs. Essentially, one somehow gets lyrics, or other text, and then the program presents either a neatly formatted CSV for import into e.g. Akebi on Android, or an APKG for import into Anki.

## How-to use ASVC
### Step 1: Get some lyrics
You choose one of the following three:
1. Add a www.lyrical-nonsense.com link for lyrics.
2. Add a pathname to a local file.
3. Paste in lyrics in a text area.

### Step 2: Check words!
This step shows the list of vocabulary words for which it will in the next step try to scrape all kinds of information. It is created using Kuromoji. You can only remove items from this list (e.g. in case you don't want to add it to the deck), not add them.

### Step 3: Set scraping settings, and get to work.
Or rather, let asvc get to work. Currently, there's not many settings to choose from. Only between kana (built-in kuromoji vs scrape jisho), sentences (scrape jisho vs scrape tatoeba) and audio (scrape forvo vs scrape OJAD).

### Step 4: Verify scraped results
After scraping, asvc presents an editable table with all the information that will in the next step be exported to a deck. This is the last moment a user can edit what gets exported to the newly created apkg!

### Step 5: Export
Finally, time to export. For now, following options are available:
1. Export to CSV (e.g. for use in Akebi)
2. Create APKG
Others may follow possibly, when I see a use-case for them.
