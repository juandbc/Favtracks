package com.bermusoft.favtracks.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bermusoft.favtracks.R;
import com.bermusoft.favtracks.controller.TrackLoader;
import com.bermusoft.favtracks.data.Track;
import com.bermusoft.favtracks.data.TrackAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaCancionesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Track>> {

    private static final String TRACKS_REQUEST_URL = "https://juanbdev.azurewebsites.net/dnt/getTracksList.php";

    private static final int TRACK_LOADER_ID = 1;

    private String interpreter;
    private int rhythm;
    private float rating;

    private TrackAdapter adapter;

    private TextView emptyListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canciones);

        interpreter = getIntent().getStringExtra("interpreter");
        rhythm = getIntent().getIntExtra("rhythm", -1);
        rating = getIntent().getFloatExtra("rating", -1f);

        emptyListTextView = (TextView) findViewById(R.id.emptyListTextView);

        adapter = new TrackAdapter(this, new ArrayList<Track>());

        ListView listView = (ListView) findViewById(R.id.tracksList);
        listView.setEmptyView(emptyListTextView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaCancionesActivity.this, DetalleCancionActivity.class);
                //intent.putExtra();
                startActivity(intent);
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(TRACK_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyListTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tracks_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                Intent i = new Intent(this, FilterActivity.class);
                startActivity(i);
                break;
            case R.id.action_log_out:
                SharedPreferences prefs = getSharedPreferences("favtracksPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear().apply();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Track>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(TRACKS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("rhythm", String.valueOf(rhythm));
        uriBuilder.appendQueryParameter("rating", String.valueOf(rating));
        uriBuilder.appendQueryParameter("interpreter", interpreter);

        return new TrackLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Track>> loader, List<Track> tracks) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        emptyListTextView.setText(R.string.empty_list);

        adapter.clear();

        if (tracks != null && !tracks.isEmpty()) {
            adapter.addAll(tracks);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Track>> loader) {
        adapter.clear();
    }
}
