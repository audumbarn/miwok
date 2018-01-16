package com.example.android.miwok;

/**
 * Created by nevareka on 7/20/2017.
 */

public class Word {

    private static final int NO_IMAGE_PROVIDED = -1;

    /* Default translation for the word */
    private String defaultTranslation;

    /* Miwok translation for the word */
    private String miwokTranslation;

    /* Image associated with this word */
    private int imageResourceId;

    /* Music associated with this word */
    private int musicResourceId;

    @Override
    public String toString() {
        return "Word{" +
                "defaultTranslation='" + defaultTranslation + '\'' +
                ", miwokTranslation='" + miwokTranslation + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", musicResourceId=" + musicResourceId +
                '}';
    }

    /* Constructor to create an object without image resource */
    public Word(String defaultTranslation, String miwokTranslation, int musicResourceId) {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        //for better understanding, I'm initializing image resource id here
        this.imageResourceId = NO_IMAGE_PROVIDED;
        this.musicResourceId = musicResourceId;
    }

    /* Constructor to create an object WITH image resource */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int musicResourceId) {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.imageResourceId = imageResourceId;
        this.musicResourceId = musicResourceId;
    }

    /* Get the default translation of the word */
    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    /* Get the miwok translation of the word*/
    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    /* Get image id associated with this word */
    public int getImageResourceId() {
        return imageResourceId;
    }

    /* Get music resource associated with this word */
    public int getMusicResourceId() {
        return musicResourceId;
    }
    /* Returns whether or not there is an image for this word.
     * One thing to notice is that with new android studio, its not breaking the code.
      * This is probably not required*/
    public boolean hasImage () {
        return imageResourceId != NO_IMAGE_PROVIDED;
    }
}
