package net.mizucoffee.hatsuyuki_chinachu.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class DownloadService extends IntentService {

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle bundle = intent.getExtras();
        if(bundle == null){
            Log.d(TAG, "bundle == null");
            return;
        }
        String urlStr = bundle.getString("url");
        if(urlStr == null){
            Log.d(TAG, "url == null");
            return;
        }
        String pathStr = bundle.getString("path");
        if(pathStr == null){
            Log.d(TAG, "path == null");
            return;
        }
        String programId = bundle.getString("programid");
        if(programId == null){
            Log.d(TAG, "programid == null");
            return;
        }
        String name = bundle.getString("name");
        if(name == null){
            Log.d(TAG, "name == null");
            return;
        }
        Shirayuki.log(urlStr);
        Shirayuki.log(pathStr);
        Shirayuki.log(programId);

        File downloadDir = new File(pathStr + "/video/");

        try {
            if (!downloadDir.exists()) {
                downloadDir.mkdir();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);

        builder.setContentTitle("番組のダウンロード中"); // 1行目
        builder.setContentText(name);
        builder.setOngoing(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlStr).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                notificationManager.cancel(1);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = null;
                FileOutputStream outputStream = null;

                try {
                    if (response.code() == 200) {
                        inputStream = response.body().byteStream();
                        byte[] buff = new byte[4096];
                        long readSize = 0L;
                        long writtenSize = 0L;

                        outputStream = new FileOutputStream(downloadDir.getAbsolutePath() + "/" + programId+".mp4");

                        while((readSize = inputStream.read(buff)) != -1) {
                            writtenSize += readSize;
                            outputStream.write(buff);
                        }
                        outputStream.flush();
                    } else {
                        Log.e(TAG, response.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    inputStream.close();
                    outputStream.close();
                }
                notificationManager.cancel(1);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.mipmap.ic_launcher);

                builder.setContentTitle("番組のダウンロードが完了しました");
                builder.setContentText(name);

                notificationManager.notify(2, builder.build());
            }
        });

    }
}