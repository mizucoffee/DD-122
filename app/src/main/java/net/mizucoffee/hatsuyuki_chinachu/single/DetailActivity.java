package net.mizucoffee.hatsuyuki_chinachu.single;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
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
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.recorded.Recorded;
import net.mizucoffee.hatsuyuki_chinachu.service.DownloadService;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.io.File;

import butterknife.BindView;

public class DetailActivity extends AppCompatActivity {

    private Recorded    mRecorded;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecorded    = new Gson().fromJson(getIntent().getStringExtra("program"), Recorded.class);
        mDataManager = new DataManager(getSharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));

        setTheme(Shirayuki.getThemeFromCategory(mRecorded.getCategory()));
        setContentView(R.layout.activity_detail);
        setSupportActionBar(mToolBar);
        Shirayuki.initActivity(this);

        setTitle(mRecorded.getTitle());

        mTitleTv.setText(mRecorded.getTitle());
        mSubtitleTv.setText(mRecorded.getSubTitle());

        if(mRecorded.getSubTitle().equals("")){
            mSubtitleTv.setVisibility(View.GONE);
            mSubtitleHeadTv.setVisibility(View.GONE);
        }

        mDescriptionTv.setText(mRecorded.getDetail());
        mChannelTv.setText(mRecorded.getChannel().getName()+" "+mRecorded.getChannel().getId());

        Picasso.with(this).load("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + mRecorded.getId() + "/preview.png?pos=30").into(mCoverIv);
        Picasso.with(this).load("http://" + mDataManager.getServerConnection().getAddress() + "/api/channel/" + mRecorded.getChannel().getId() + "/logo.png").into(mChannelIv);

        ViewTreeObserver observer = mChannelIv.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(() -> {
            if(mChannelIv.getDrawable() != null) {
                ViewGroup.LayoutParams params = mChannelIv.getLayoutParams();
                params.width = mChannelIv.getHeight() / 9 * 16;
                mChannelIv.setLayoutParams(params);
            }
        });

        mFab.setOnClickListener(v -> {
            Uri uri = Uri.parse("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + mRecorded.getId() + "/watch.mp4");
            startActivity(new Intent(Intent.ACTION_VIEW).setPackage("org.videolan.vlc").setDataAndTypeAndNormalize(uri, "video/*"));
        });

        ActionBar bar = getSupportActionBar();
        if (bar != null) bar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);

        mDownloadBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,Shirayuki.getBackgroundColorFromCategory(mRecorded.getCategory()))));
        if(new File(getFilesDir().getAbsolutePath() + "/video/" + mRecorded.getId()+".mp4").exists()){
            mDownloadBtn.setText("Play");
        }

        mDownloadBtn.setOnClickListener((v) -> {
            if(new File(getExternalFilesDir(null).getAbsolutePath() + "/video/" + mRecorded.getId()+".mp4").exists()){
                Uri uri = Uri.parse(getExternalFilesDir(null).getAbsolutePath() + "/video/" + mRecorded.getId()+".mp4");
                startActivity(new Intent(Intent.ACTION_VIEW).setPackage("org.videolan.vlc").setDataAndTypeAndNormalize(uri, "video/*"));
            }else {
                new AlertDialog.Builder(this)
                        .setTitle("確認")
                        .setMessage("以下のエンコード設定でダウンロードします。\n" +
                                "\n" +
                                "Video Size:" + spf.getString("download_video_size", "") + "\n" +
                                "Video Bitrate:" + spf.getString("download_video_bitrate", "") + "\n" +
                                "Audio Bitrate:" + spf.getString("download_audio_bitrate", "") + "\n")
                        .setPositiveButton("OK", (DialogInterface dialogInterface, int i) -> {
                            Intent intent = new Intent(this, DownloadService.class);
                            intent.putExtra("url", "http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + mRecorded.getId() + "/watch.mp4" +
                                    "?ext=mp4" +
                                    "&c%3Av=h264" +
                                    Shirayuki.getResolutionFromVideoSize(spf.getString("download_video_size", "")) +
                                    Shirayuki.getVideoBitrate(spf.getString("download_video_bitrate", "")) +
                                    Shirayuki.getAudioBitrate(spf.getString("download_audio_bitrate", "")) +
                                    "&ss=0");
                            intent.putExtra("path", getExternalFilesDir(null).getAbsolutePath());
                            intent.putExtra("programid", mRecorded.getId());
                            intent.putExtra("name", mRecorded.getTitle());
                            this.startService(intent);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
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
