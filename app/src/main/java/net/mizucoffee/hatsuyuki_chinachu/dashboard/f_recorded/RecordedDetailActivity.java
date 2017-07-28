package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.databinding.ActivityDetailBinding;
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;
import net.mizucoffee.hatsuyuki_chinachu.service.DownloadBroadcastReceiver;
import net.mizucoffee.hatsuyuki_chinachu.service.DownloadService;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.util.List;

public class RecordedDetailActivity extends AppCompatActivity {

    private ProgramItem mProgram;
    private ProgramItem mDownloaded = null; // if NOT mDownloaded then NULL
    private DataManager mDataManager;
    private DownloadBroadcastReceiver receiver;

    private ActivityDetailBinding binding;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(!msg.getData().getString("id").equals(mProgram.getId())) return;
            binding.downloadBtn.setText(getString(msg.getData().getBoolean("isSuccess") ? R.string.downloaded : R.string.download));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgram = new Gson().fromJson(getIntent().getStringExtra("program"), ProgramItem.class);
        setTheme(Shirayuki.getThemeFromCategory(mProgram.getCategory()));

        mDataManager = new DataManager(getSharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail);
        binding.setProgram(mProgram);
        binding.setDetail(this);

        setSupportActionBar(binding.toolbar);
        setTitle(mProgram.getTitle());

        ActionBar bar = getSupportActionBar();
        if (bar != null) bar.setDisplayHomeAsUpEnabled(true);

        ViewCompat.setBackgroundTintList(binding.downloadBtn,ColorStateList.valueOf(ContextCompat.getColor(this,Shirayuki.getBackgroundColorFromCategory(mProgram.getCategory()))));

        if(mDataManager.getDownloadedList() != null)
            for(ProgramItem r : mDataManager.getDownloadedList())
                if(r.getId().equals(mProgram.getId()))
                    mDownloaded = r;

        if(mDownloaded != null) {
            binding.downloadBtn.setText(  getString(mDownloaded.getDownloading() ? R.string.downloaded : R.string.downloading));
            binding.downloadBtn.setEnabled(false);
        }

        receiver = new DownloadBroadcastReceiver(mHandler);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("RETURN_STATUS");
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    public void onClickFab(View v){
        Uri uri = Uri.parse("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + mProgram.getId() + "/watch.mp4");
        startActivity(new Intent(Intent.ACTION_VIEW).setPackage("org.videolan.vlc").setDataAndTypeAndNormalize(uri, "video/*"));
    }

    public void onClickDownload(View v){
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);

        if(mDownloaded == null)
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.confirm))
                    .setMessage(getString(R.string.confirm_encode) + "\n" +
                            "\n" +
                            "Video Size:" + spf.getString("download_video_size", "720p (HD) (Recommended)") + "\n" +
                            "Video Bitrate:" + spf.getString("download_video_bitrate", "1Mbps (Recommended)") + "\n" +
                            "Audio Bitrate:" + spf.getString("download_audio_bitrate", "128kbps (Recommended)") + "\n")
                    .setPositiveButton("OK", (DialogInterface dialogInterface, int i) -> {
                        mProgram.setDownloading(true);
                        binding.downloadBtn.setText(getString(R.string.downloading));
                        binding.downloadBtn.setEnabled(false);

                        List<ProgramItem> list = mDataManager.getDownloadedList();
                        list.add(mProgram);
                        mDataManager.setDownloadedList(list);

                        Intent intent = new Intent(this, DownloadService.class);
                        intent.putExtra("recorded",new Gson().toJson(mProgram));
                        intent.putExtra("address",mDataManager.getServerConnection().getAddress());
                        this.startService(intent);
                    })
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show();
    }
}
