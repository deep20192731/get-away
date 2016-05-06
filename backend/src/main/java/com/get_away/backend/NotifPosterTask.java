package com.get_away.backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rrk on 4/30/16.
 */
public class NotifPosterTask implements GetAwayTask {

    private static final String GCM_LINK = "https://gcm-http.googleapis.com/gcm/send";
    private static final String API_KEY = "AIzaSyAYRSY3SoneA41l7WYIF4SXUXtNQNFsjv8";

    private String data;

    public NotifPosterTask(String data){
        this.data = data;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Executing Notifposter");
            URL url = new URL(GCM_LINK);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(data.getBytes());
            System.out.println("Write done");

            // Read GCM response.
            InputStream inputStream = conn.getInputStream();


            //System.out.println(resp);

        } catch (IOException exp) {
            System.out.println("Unable to send GCM message.");
            System.out.println(exp.getMessage());
        }

    }

    @Override
    public void run() {
        execute();
    }
}
