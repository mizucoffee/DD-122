package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.model.gamma.Recorded;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private Recorded mRecorded;
    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecorded = new Gson().fromJson(getIntent().getStringExtra("program"), Recorded.class);

        setTitle(mRecorded.getTitle());
        ImageView topIv = ButterKnife.findById(this,R.id.img_cover);
        ((TextView)ButterKnife.findById(this,R.id.detail_title_tv)).setText(mRecorded.getTitle());
        ((TextView)ButterKnife.findById(this,R.id.detail_subtitle_tv)).setText(mRecorded.getSubTitle());
        if(mRecorded.getSubTitle().equals("")){
            ButterKnife.findById(this,R.id.detail_subtitle).setVisibility(View.GONE);
            ButterKnife.findById(this,R.id.detail_subtitle_tv).setVisibility(View.GONE);
        }
        ((TextView)ButterKnife.findById(this,R.id.detail_des_tv)).setText(mRecorded.getDetail());
        ((TextView)ButterKnife.findById(this,R.id.detail_channel_tv)).setText(mRecorded.getChannel().getName()+" "+mRecorded.getChannel().getId());

        mDataManager = new DataManager(getSharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));

        Picasso.with(this).load("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + mRecorded.getId() + "/preview.png").into(topIv);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Uri uri = Uri.parse("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + mRecorded.getId() + "/watch.mp4");
            Intent vlcIntent = new Intent(Intent.ACTION_VIEW);
            vlcIntent.setPackage("org.videolan.vlc");
            vlcIntent.setDataAndTypeAndNormalize(uri, "video/*");
            startActivity(vlcIntent);
        });

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }

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
