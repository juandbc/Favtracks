package com.bermusoft.favtracks.data;

/**
 * Created by deepinboy on 6/11/17.
 */

public class Track {
    private String trackName;
    private String trackAlbum;
    private String trackInterpreter;
    private String trackYear;
    private String trackLanguage;
    private String trackUser;
    private int trackRhythm;
    private float trackRating;

    public Track(String trackName, String trackAlbum, String trackInterpreter, String trackYear, String trackLanguage, String trackUser,
                 int trackRhythm, float trackRating) {
        this.trackName = trackName;
        this.trackAlbum = trackAlbum;
        this.trackInterpreter = trackInterpreter;
        this.trackYear = trackYear;
        this.trackLanguage = trackLanguage;
        this.trackUser = trackUser;
        this.trackRhythm = trackRhythm;
        this.trackRating = trackRating;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getTrackAlbum() {
        return trackAlbum;
    }

    public String getTrackInterpreter() {
        return trackInterpreter;
    }

    public String getTrackYear() {
        return trackYear;
    }

    public String getTrackLanguage() {
        return trackLanguage;
    }

    public String getTrackUser() {
        return trackUser;
    }

    public int getTrackRhythm() {
        return trackRhythm;
    }

    public float getTrackRating() {
        return trackRating;
    }
}
