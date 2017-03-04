package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program;
import net.mizucoffee.hatsuyuki_chinachu.service.DownloadBroadcastReceiver;
import net.mizucoffee.hatsuyuki_chinachu.service.DownloadService;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecordedDetailActivity extends AppCompatActivity {

    private Program mProgram;
    private DataManager mDataManager;

    @BindView(R.id.toolbar) Toolbar mToolBar;
    @BindView(R.id.img_cover) ImageView mCoverIv;
    @BindView(R.id.fab) FloatingActionButton mFab;

    @BindView(R.id.detail_title_tv)     TextView mTitleTv;
    @BindView(R.id.detail_subtitle_tv)  TextView mSubtitleTv;
    @BindView(R.id.detail_subtitle)     TextView mSubtitleHeadTv;
    @BindView(R.id.detail_des_tv)       TextView mDescriptionTv;
    @BindView(R.id.detail_channel_tv)   TextView mChannelTv;
    @BindView(R.id.channel_iv)          ImageView mChannelIv;
    @BindView(R.id.download_btn)        Button mDownloadBtn;

    private Program downloaded; // if NOT downloaded then NULL
    private DownloadBroadcastReceiver receiver;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(!msg.getData().getString("id").equals(mProgram.getId())) return;
            if (msg.getData().getBoolean("isSuccess"))
                mDownloadBtn.setText("Downloaded");
            else
                mDownloadBtn.setText("Download");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgram = new Gson().fromJson(getIntent().getStringExtra("program"), Program.class);
        mDataManager = new DataManager(getSharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));

        setTheme(Shirayuki.getThemeFromCategory(mProgram.getCategory()));
        setContentView(R.layout.activity_detail);
        setSupportActionBar(mToolBar);
        Shirayuki.initActivity(this);

        setTitle(mProgram.getTitle());

        mTitleTv.setText(mProgram.getTitle());
        mSubtitleTv.setText(mProgram.getSubTitle());

        if(mProgram.getSubTitle().equals("")){
            mSubtitleTv.setVisibility(View.GONE);
            mSubtitleHeadTv.setVisibility(View.GONE);
        }

        mDescriptionTv.setText(mProgram.getDetail());
        mChannelTv.setText(mProgram.getChannel().getName()+" "+ mProgram.getChannel().getId());

        Picasso.with(this).load("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + mProgram.getId() + "/preview.png?pos=30").into(mCoverIv);
        Picasso.with(this).load("http://" + mDataManager.getServerConnection().getAddress() + "/api/channel/" + mProgram.getChannel().getId() + "/logo.png").into(mChannelIv);

        ViewTreeObserver observer = mChannelIv.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(() -> {
            if(mChannelIv.getDrawable() != null) {
                ViewGroup.LayoutParams params = mChannelIv.getLayoutParams();
                params.width = mChannelIv.getHeight() / 9 * 16;
                mChannelIv.setLayoutParams(params);
            }
        });

        mFab.setOnClickListener(v -> {
            Uri uri = Uri.parse("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + mProgram.getId() + "/watch.mp4");
            startActivity(new Intent(Intent.ACTION_VIEW).setPackage("org.videolan.vlc").setDataAndTypeAndNormalize(uri, "video/*"));
        });

        ActionBar bar = getSupportActionBar();
        if (bar != null) bar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);

        mDownloadBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,Shirayuki.getBackgroundColorFromCategory(mProgram.getCategory()))));

        downloaded = null;
        if(mDataManager.getDownloadedList() != null)
            for(Program r : mDataManager.getDownloadedList())
                if(r.getId().equals(mProgram.getId()))
                    downloaded = r;

        if(downloaded != null) {
            if (downloaded.getDownloading())
                mDownloadBtn.setText("Downloaded");
            else
                mDownloadBtn.setText("Downloading...");
            mDownloadBtn.setEnabled(false);
        }

        mDownloadBtn.setOnClickListener((v) -> {
            if(downloaded == null)
                new AlertDialog.Builder(this)
                        .setTitle("確認")
                        .setMessage("以下のエンコード設定でダウンロードします。\n" +
                                "\n" +
                                "Video Size:" + spf.getString("download_video_size", "720p (HD) (Recommended)") + "\n" +
                                "Video Bitrate:" + spf.getString("download_video_bitrate", "1Mbps (Recommended)") + "\n" +
                                "Audio Bitrate:" + spf.getString("download_audio_bitrate", "128kbps (Recommended)") + "\n")
                        .setPositiveButton("OK", (DialogInterface dialogInterface, int i) -> {
                            mProgram.setDownloading(true);
                            mDownloadBtn.setText("Downloading...");
                            mDownloadBtn.setEnabled(false);

                            List<Program> list = mDataManager.getDownloadedList();
                            if(list == null){
                                list = new ArrayList<>();
                            }
                            list.add(mProgram);
                            mDataManager.setDownloadedList(list);


                            Intent intent = new Intent(this, DownloadService.class);
                            intent.putExtra("recorded",new Gson().toJson(mProgram));
                            intent.putExtra("address",mDataManager.getServerConnection().getAddress());
                            this.startService(intent);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
        });

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
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
