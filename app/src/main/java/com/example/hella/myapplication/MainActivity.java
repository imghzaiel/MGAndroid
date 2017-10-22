package com.example.hella.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

        public class MainActivity extends AppCompatActivity {

            // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds

            public static final int CONNECTION_TIMEOUT=10000;
            public static final int READ_TIMEOUT=15000;
            private EditText etLogin;
            private EditText etPassword;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                // Get Reference to variables
                etLogin = (EditText) findViewById(R.id.login);
                etPassword = (EditText) findViewById(R.id.password);
            }

            // Triggers when LOGIN Button clicked
            public void checkLogin(View arg0) {

                // Get text from email and passord field
                final String login = etLogin.getText().toString();
                final String password = etPassword.getText().toString();

                // Initialize  AsyncLogin() class with email and password
                new AsyncLogin().execute(login,password);

            }

            private class AsyncLogin extends AsyncTask<String, String, String>
            {
                ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    //this method will be running on UI thread
                    pdLoading.setMessage("\tLoading...");
                    pdLoading.setCancelable(false);
                    pdLoading.show();

                }

                @Override
                protected String doInBackground(String... params) {

                    String result = new RestClient().process(params[0], params[1]);
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {



                    pdLoading.dismiss();

                    if(result.equalsIgnoreCase("true"))
                    {


                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        startActivity(intent);
                        MainActivity.this.finish();

                    }else if (result.equalsIgnoreCase("false")){

                        Toast.makeText(MainActivity.this, "Invalid login or password", Toast.LENGTH_LONG).show();

                    } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                        Toast.makeText(MainActivity.this, "Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                    }
                }

            }
        }










