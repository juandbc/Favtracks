package com.bermusoft.favtracks.ui;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bermusoft.favtracks.R;
import com.bermusoft.favtracks.controller.HttpManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AddUserActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://juanbdev.azurewebsites.net/dnt/addUser.php";

    private static final String LOG_TAG = AddUserActivity.class.getSimpleName();

    private EditText username;
    private EditText password;
    private Button addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        addUser = (Button) findViewById(R.id.addUserButton);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new addUserAsyncTask().execute(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private class addUserAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpsURLConnection conn;
            InputStream inputStream;
            try {

                Uri uriBase = Uri.parse(BASE_URL);
                Uri.Builder uriBuilder = uriBase.buildUpon();
                uriBuilder.appendQueryParameter("username", params[0]);
                uriBuilder.appendQueryParameter("password", params[1]);

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
                    Log.e(LOG_TAG, s);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_add_user), Toast.LENGTH_SHORT).show();
                    break;
                case "success":
                    Log.e(LOG_TAG, s);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_user_success), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    }
}
