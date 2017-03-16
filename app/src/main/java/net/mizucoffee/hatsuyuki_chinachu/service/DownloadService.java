package net.mizucoffee.hatsuyuki_chinachu.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;

import net.mizucoffee.hatsuyuki_chinachu.App;
import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static android.content.ContentValues.TAG;

public class DownloadService extends IntentService {

    private String mAddress;
    private Program mProgram;
    private DataManager mDataManager;
    private List<Program> mProgramList;
    public static int ONGOING_NOTIFICATION_ID = 1;

    public DownloadService() {
        super("DownloadService");
    }

    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle bundle = intent.getExtras();
        if(bundle == null){
            Log.d(TAG, "bundle == null");
            return;
        }
        mProgram = new Gson().fromJson(bundle.getString("recorded"),Program.class);
        if(mProgram == null){
            Log.d(TAG, "recorded == null");
            return;
        }
        mAddress = bundle.getString("address");
        if(mAddress == null){
            Log.d(TAG, "address == null");
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);

        builder.setContentTitle(App.getContext().getString(R.string.downloading_program));
        builder.setContentText(mProgram.getTitle());
        builder.setOngoing(true);
        startForeground(ONGOING_NOTIFICATION_ID, builder.build());

        mDataManager = new DataManager(getSharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
        Program downloaded = null;
//        mProgramList = mDataManager.getDownloadedList();
//        int i= 0;
//        if(mDataManager.getDownloadedList() != null)
//            for(Program r : mDataManager.getDownloadedList()) {
//                if (r.getId().equals(mProgram.getId())) {
//                    downloaded = r;
//                    mProgramList.remove(i);
//                    break;
//                }
//                i++;
//            }

        if(!imageDL()) {
            failed();
            return;
        }
        if(!videoDL()) {
            failed();
            return;
        }
        stopForeground(true);

//        downloaded.setDownloading(false);
        mProgramList.add(downloaded);
//        mDataManager.setDownloadedList(mProgramList);

//        mProgram.setDownloading(false);

        Intent bcIntent = new Intent();
        bcIntent.putExtra("isSuccess", true);
        bcIntent.putExtra("id", mProgram.getId());
        bcIntent.setAction("RETURN_STATUS");
        getBaseContext().sendBroadcast(bcIntent);

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(getApplicationContext());
        builder2.setSmallIcon(R.mipmap.ic_launcher);

        builder2.setContentTitle(App.getContext().getString(R.string.finished_download_program));
        builder2.setContentText(mProgram.getTitle());

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(getID(), builder2.build());

    }

    private void failed(){
        stopForeground(true);
        Intent bcIntent = new Intent();
        bcIntent.putExtra("isSuccess", false);
        bcIntent.putExtra("id", mProgram.getId());
        bcIntent.setAction("RETURN_STATUS");
        getBaseContext().sendBroadcast(bcIntent);
//        mDataManager.setDownloadedList(mProgramList);
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(getApplicationContext());
        builder2.setSmallIcon(R.mipmap.ic_launcher);

        builder2.setContentTitle(getString(R.string.failed_download));
        builder2.setContentText(mProgram.getTitle());

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(getID(), builder2.build());
    }


    private boolean imageDL(){
        File downloadDir = new File(getBaseContext().getExternalFilesDir(null).getAbsolutePath() + "/image/");

        try {
            if (!downloadDir.exists()) {
                downloadDir.mkdir();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            URL url = new URL("http://" + mAddress + "/api/recorded/" + mProgram.getId() + "/preview.png?pos=30");
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();

            File file = new File(downloadDir.getAbsolutePath() + "/" + mProgram.getId() +".png");
            FileOutputStream out = new FileOutputStream(file, false);
            int b;
            while((b = in.read()) != -1){
                out.write(b);
            }

            out.close();
            in.close();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }catch (ProtocolException e) {
            e.printStackTrace();
            return false;
        }catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean videoDL(){
        File downloadDir = new File(getBaseContext().getExternalFilesDir(null).getAbsolutePath() + "/video/");

        try {
            if (!downloadDir.exists()) {
                downloadDir.mkdir();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            URL url = new URL("http://" + mAddress + "/api/recorded/" + mProgram.getId() + "/watch.mp4" +
                    "?ext=mp4" +
                    "&c%3Av=h264" +
                    Shirayuki.getResolutionFromVideoSize(spf.getString("download_video_size", "720p (HD) (Recommended)")) +
                    Shirayuki.getVideoBitrate(spf.getString("download_video_bitrate", "1Mbps (Recommended)")) +
                    Shirayuki.getAudioBitrate(spf.getString("download_audio_bitrate", "128kbps (Recommended)")) +
                    "&ss=0");
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();

            File file = new File(downloadDir.getAbsolutePath() + "/" + mProgram.getId() + ".mp4");
            FileOutputStream out = new FileOutputStream(file, false);
            int b;
            while((b = in.read()) != -1){
                out.write(b);
            }

            out.close();
            in.close();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }catch (ProtocolException e) {
            e.printStackTrace();
            return false;
        }catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}