package com.bermusoft.favtracks.ui;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bermusoft.favtracks.R;
import com.bermusoft.favtracks.controller.HttpManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AddTrackActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://juanbdev.azurewebsites.net/dnt/addTracK.php";

    private static final String LOG_TAG = AddTrackActivity.class.getSimpleName();

    private EditText trackName;
    private EditText trackAlbum;
    private EditText trackInterpreter;
    private EditText trackYear;
    private EditText trackLanguage;
    private Spinner trackRhythm;
    private RatingBar trackRating;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        SharedPreferences prefs = getSharedPreferences("favtracksPrefs", MODE_PRIVATE);
        user = prefs.getString("username", "anonimo");

        trackName = (EditText) findViewById(R.id.nameEditText);
        trackAlbum = (EditText) findViewById(R.id.albumEditText);
        trackInterpreter = (EditText) findViewById(R.id.interpreterEditText);
        trackYear = (EditText) findViewById(R.id.yearEditText);
        trackLanguage = (EditText) findViewById(R.id.languageEditText);
        trackRhythm = (Spinner) findViewById(R.id.rhythmSpinner);
        trackRating = (RatingBar) findViewById(R.id.ratingBar);
        Button addTrackButton = (Button) findViewById(R.id.addButton);

        ArrayAdapter<CharSequence> rhythmAdapter = ArrayAdapter.createFromResource(this, R.array.rhythm_list,
                android.R.layout.simple_spinner_dropdown_item);
        trackRhythm.setAdapter(rhythmAdapter);

        trackRating.setMax(5);
        trackRating.setRating(3.5f);

        addTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =  trackName.getText().toString();
                String album =  trackAlbum.getText().toString();
                String interpreter =  trackInterpreter.getText().toString();
                String year =  trackYear.getText().toString();
                String language =  trackLanguage.getText().toString();
                String rhythm = String.valueOf(trackRhythm.getSelectedItemPosition());
                String rating = String.valueOf(Math.round(trackRating.getRating()));

                new addTrackAsyncTask().execute(name, album, interpreter, year, language, rhythm, rating, user);
            }
        });
    }

    private class addTrackAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpsURLConnection conn;
            try {
                Uri uriBase = Uri.parse(BASE_URL);
                Uri.Builder uriBuilder = uriBase.buildUpon();
                uriBuilder.appendQueryParameter("name", params[0]);
                uriBuilder.appendQueryParameter("album", params[1]);
                uriBuilder.appendQueryParameter("interpreter", params[2]);
                uriBuilder.appendQueryParameter("year", params[3]);
                uriBuilder.appendQueryParameter("language", params[4]);
                uriBuilder.appendQueryParameter("rhythm", params[5]);
                uriBuilder.appendQueryParameter("rating", params[6]);
                uriBuilder.appendQueryParameter("user", params[7]);

                Log.e(LOG_TAG, uriBuilder.toString());

                URL url = new URL(uriBuilder.toString());
                return HttpManager.makeHttpPostRequest(url);

            } catch (MalformedURLException e) {
                Log.e("BAD URL", e.getMessage());
                return "failed";
            } catch (IOException e) {
                Log.e("BAD URL", e.getMessage());
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            switch (s) {
                case "failed":
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_failed_add_track), Toast.LENGTH_SHORT).show();
                    break;
                case "success":
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_track_success), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    }
}
