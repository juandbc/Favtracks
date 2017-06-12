package com.bermusoft.favtracks.ui;

import android.content.Intent;
import android.content.SharedPreferences;
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

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://juanbdev.azurewebsites.net/dnt/login.php";

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    // UI references.
    private EditText user;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        user = (EditText) findViewById(R.id.usernameLoginEditText);
        password = (EditText) findViewById(R.id.passwordLoginEditText);

        Button logIn = (Button) findViewById(R.id.log_in_button);
        Button register = (Button) findViewById(R.id.register_button);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserLoginTask().execute(user.getText().toString(), password.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, AddUserActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<String, Void, Boolean> {
        private String pass;
        private String username;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            username = params[0];
            pass = params[1];
            HttpsURLConnection conn;
            InputStream inputStream;
            try {

                Uri uriBase = Uri.parse(BASE_URL);
                Uri.Builder uriBuilder = uriBase.buildUpon();
                uriBuilder.appendQueryParameter("username", username);
                uriBuilder.appendQueryParameter("password", pass);

                Log.e(LOG_TAG, uriBuilder.toString());

                URL url = new URL(uriBuilder.toString());
                String response = HttpManager.makeHttpRequest(url);
                Log.e(LOG_TAG, response);
                return response.contains(pass);
            } catch (MalformedURLException e) {
                Log.e("BAD URL", e.getMessage());
                return false;
            } catch (IOException e) {
                Log.e("BAD URL", e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                SharedPreferences prefs = getSharedPreferences("favtracksPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", username);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, ListaCancionesActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.error_failed_login), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

