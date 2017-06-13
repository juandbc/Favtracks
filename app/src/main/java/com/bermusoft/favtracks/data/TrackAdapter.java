package com.bermusoft.favtracks.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bermusoft.favtracks.R;

import java.util.List;

/**
 * Created by deepinboy on 6/11/17.
 */

public class TrackAdapter extends ArrayAdapter<Track> {

    public TrackAdapter(@NonNull Context context, @NonNull List<Track> tracks) {
        super(context, 0, tracks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.track_list_item, parent, false);
        }
        Track track = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.trackNameItemTextView);
        TextView interpreterTextView = (TextView) listItemView.findViewById(R.id.interpreterItemTextView);
        TextView rhythm = (TextView) listItemView.findViewById(R.id.rhythmItemTextView);

        name.setText(track.getTrackName());
        interpreterTextView.setText(track.getTrackInterpreter());
        rhythm.setText(getContext().getResources().getStringArray(R.array.rhythm_list)[track.getTrackRhythm()]);
        return listItemView;
    }
}
