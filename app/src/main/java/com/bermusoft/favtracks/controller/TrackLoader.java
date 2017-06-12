package com.bermusoft.favtracks.controller;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.bermusoft.favtracks.data.Track;

import java.util.List;

/**
 * Created by deepinboy on 6/11/17.
 */

public class TrackLoader extends AsyncTaskLoader<List<Track>> {
    private String url;

    public TrackLoader(Context context, String url) {
        super(context);
        this.url = url;
    }
    
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Track> loadInBackground() {
        if (url == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of tracks.
        List<Track> tracks = HttpManager.fetchTrackData(url);
        return tracks;
    }
}
