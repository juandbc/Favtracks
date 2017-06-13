package com.bermusoft.favtracks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.bermusoft.favtracks.R;

public class FilterActivity extends AppCompatActivity {

    private EditText interpreterEditText;
    private Spinner rhythmSpinner;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        interpreterEditText = (EditText) findViewById(R.id.filterInterpreterEditText);
        rhythmSpinner = (Spinner) findViewById(R.id.filterRhythmSpinner);
        ratingBar = (RatingBar) findViewById(R.id.filterRatingBar);

        ArrayAdapter<CharSequence> rhythmAdapter = ArrayAdapter.createFromResource(this, R.array.rhythm_list,
                android.R.layout.simple_spinner_dropdown_item);
        rhythmSpinner.setAdapter(rhythmAdapter);

        Button filterButton = (Button) findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, TracksListActivity.class);
                intent.putExtra("interpreter", interpreterEditText.getText().toString());
                intent.putExtra("rhythm", rhythmSpinner.getSelectedItemPosition());
                intent.putExtra("rating", Math.round(ratingBar.getRating()));
                startActivity(intent);
            }
        });
    }
}
