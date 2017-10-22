package com.example.hella.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.hella.myapplication.MainActivity.CONNECTION_TIMEOUT;
import static com.example.hella.myapplication.MainActivity.READ_TIMEOUT;

/**
 * Created by imen on 22/10/17.
 */

public class RestClient
{
    HttpURLConnection conn;
    URL url = null;


    public String process(String ... params){
        try {

            // Enter URL address where your php file resides
            url = new URL("http://192.168.1.110/MGManegement/web/app_dev.php/api/login_check");

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "exception";
        }
        try {
            // Setup HttpURLConnection class to send and receive data from php and mysql
            conn = (HttpURLConnection)url.openConnection();

            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // setDoInput and setDoOutput method depict handling of both send and receive
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // Append parameters to URL
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("_username", params[0])
                    .appendQueryParameter("_password", params[1]);
            String query = builder.build().getEncodedQuery();

            // Open connection for sending data
            /*OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("{'_username' : 'imen.ghzaiel', '_password': 'imen'}");
            writer.flush();
            writer.close();
            os.close();*/
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(query);
            out.close();
            conn.connect();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "exception";
        }

        try {

            int response_code = conn.getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                String token = result.toString();

                // Pass data to onPostExecute method
                return "true";

            }else{

                return "unsuccessful";
            }

        } catch (IOException e) {

            return "exception";

        } finally {
            conn.disconnect();
        }


    }
    }
