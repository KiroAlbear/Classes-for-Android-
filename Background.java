package com.example.admin2.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLEncoder;

/**
 * Created by admin2 on 06/08/17.
 */

public class Background extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;

    public Background(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... voids) {

        String type = voids[0];
        ///Location Of Login.php File On the Host Server
        String Login_url = "http://sea-green-residues.000webhostapp.com/Login.php";

        if (type.equals("Login")) {
            try {
                String username = voids[1];
                String password = voids[2];

                URL url = new URL(Login_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                 /// Set POST Method To Send data to Host Server
                httpURLConnection.setRequestMethod("POST");

                //httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                //Send username and password to the host server
                bufferedWriter.write(post_data);


                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));


                String result = "";
                String line = "";


                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
