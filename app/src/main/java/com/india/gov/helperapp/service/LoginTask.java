package com.india.gov.helperapp.service;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.india.gov.helperapp.UpdateListActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Shubham Maheshwari on 31-03-2017.
 */

public class LoginTask extends AsyncTask<Void, Void, Boolean> {

    ProgressDialog pg;
    Context context;
    String aadhar;
    String password;
    Activity activity;

    public LoginTask(String ano ,String pass, Context context, Activity activity){
        aadhar = ano;
        password = pass;
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        pg = new ProgressDialog(context);
        pg.setMessage("Loading...");
        pg.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        String url = "http://172.104.51.13:8080/candidate/id/";
        Request request = new Request.Builder()
                .url(url+aadhar)
                .addHeader("Skip","yes")/*Base64.encodeToString((aadhar+":"+password).getBytes(),Base64.DEFAULT))*/
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.body().string() == "1") {
                return true;
            }
        } catch (IOException e) {
            Toast.makeText(context,"Error Logging in",Toast.LENGTH_LONG).show();
        }

        return false;
    }

    protected void onPostExecute(Boolean Status) {
        if (Status == true) {
            activity.startActivity(new Intent(activity, UpdateListActivity.class));
        }
        else {
            Toast.makeText(context,"Error Logging in",Toast.LENGTH_LONG).show();
        }
    }

}
