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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import static android.content.ContentValues.TAG;

public class DownloadService extends IntentService {

    public static int ONGOING_NOTIFICATION_ID = 1;

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

        builder.setContentTitle("番組のダウンロード中");
        builder.setContentText(name);
        builder.setOngoing(true);

//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(1, builder.build());


        startForeground(ONGOING_NOTIFICATION_ID, builder.build());

        try{
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();

            File file = new File(downloadDir.getAbsolutePath() + "/" + programId+".mp4");
            FileOutputStream out = new FileOutputStream(file, false);
            int b;
            while((b = in.read()) != -1){
                out.write(b);
            }

            out.close();
            in.close();

//            notificationManager.cancel(1);

            stopForeground(true);

            NotificationCompat.Builder builder2 = new NotificationCompat.Builder(getApplicationContext());
            builder2.setSmallIcon(R.mipmap.ic_launcher);

            builder2.setContentTitle("番組のダウンロードが完了しました");
            builder2.setContentText(name);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(2, builder2.build());

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (ProtocolException e) {
            e.printStackTrace();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        Shirayuki.log("Download Finished");

//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(urlStr).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                notificationManager.cancel(1);
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                InputStream inputStream = null;
//                FileOutputStream outputStream = null;
//
//                try {
//                    if (response.code() == 200) {
//                        inputStream = response.body().byteStream();
//                        byte[] buff = new byte[4096];
//                        long readSize = 0L;
//                        long writtenSize = 0L;
//
//                        outputStream = new FileOutputStream(downloadDir.getAbsolutePath() + "/" + programId+".mp4");
//
//                        while((readSize = inputStream.read(buff)) != -1) {
//                            writtenSize += readSize;
//                            outputStream.write(buff);
//                        }
//                        outputStream.flush();
//                    } else {
//                        Log.e(TAG, response.toString());
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    inputStream.close();
//                    outputStream.close();
//                }
//                notificationManager.cancel(1);
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
//                builder.setSmallIcon(R.mipmap.ic_launcher);
//
//                builder.setContentTitle("番組のダウンロードが完了しました");
//                builder.setContentText(name);
//
//                notificationManager.notify(2, builder.build());
//            }
//        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Shirayuki.log("Destroy");
    }
}