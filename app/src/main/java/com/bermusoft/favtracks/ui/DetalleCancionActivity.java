package com.bermusoft.favtracks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.bermusoft.favtracks.R;

/**
 * Created by deepinboy on 6/10/17.
 */

public class DetalleCancionActivity extends AppCompatActivity {
    private EditText trackName;
    private EditText trackAlbum;
    private EditText trackInterpreter;
    private EditText trackYear;
    private EditText trackLanguage;
    private Spinner trackRhythm;
    private RatingBar trackRating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);
        setTitle(R.string.track_details_activity);

        Intent intent = getIntent();

        trackName = (EditText) findViewById(R.id.nameEditText);
        trackAlbum = (EditText) findViewById(R.id.albumEditText);
        trackInterpreter = (EditText) findViewById(R.id.interpreterEditText);
        trackYear = (EditText) findViewById(R.id.yearEditText);
        trackLanguage = (EditText) findViewById(R.id.languageEditText);
        trackRhythm = (Spinner) findViewById(R.id.rhythmSpinner);
        trackRating = (RatingBar) findViewById(R.id.ratingBar);
        Button addTrackButton = (Button) findViewById(R.id.addButton);
        addTrackButton.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> rhythmAdapter = ArrayAdapter.createFromResource(this, R.array.rhythm_list,
                android.R.layout.simple_spinner_dropdown_item);
        trackRhythm.setAdapter(rhythmAdapter);
        trackRating.setMax(5);
        trackRating.setRating(intent.getFloatExtra("rating", 3f));

        trackName.setText(intent.getStringExtra("name"));
        trackAlbum.setText(intent.getStringExtra("album"));
        trackInterpreter.setText(intent.getStringExtra("interpreter"));
        trackYear.setText(intent.getStringExtra("year"));
        trackLanguage.setText(intent.getStringExtra("language"));
        trackRhythm.setSelection(intent.getIntExtra("rhythm", 0));
    }
}
