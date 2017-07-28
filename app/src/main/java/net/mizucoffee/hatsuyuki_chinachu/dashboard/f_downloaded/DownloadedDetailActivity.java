package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_downloaded;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import butterknife.BindView;

public class DownloadedDetailActivity extends AppCompatActivity {

    private Program mProgram;

    @BindView(R.id.toolbar)             Toolbar mToolBar;
//    @BindView(R.id.img_cover)           ImageView mCoverIv;
//    @BindView(R.id.fab)                 FloatingActionButton mFab;
//
//    @BindView(R.id.detail_subtitle)     TextView mSubtitleHeadTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgram = new Gson().fromJson(getIntent().getStringExtra("program"), Program.class);

        setTheme(Shirayuki.getThemeFromCategory(mProgram.getCategory()));
        setContentView(R.layout.activity_detail);
        setSupportActionBar(mToolBar);
        Shirayuki.initActivity(this);

        setTitle(mProgram.getTitle());

        Bitmap bmImg = BitmapFactory.decodeFile(getExternalFilesDir(null).getAbsolutePath() + "/image/" + mProgram.getId() + ".png");
//        mCoverIv.setImageBitmap(bmImg);

//        mFab.setOnClickListener(v -> {
//            Uri uri = Uri.parse("file://" + getExternalFilesDir(null).getAbsolutePath() + "/video/" + mProgram.getId()+".mp4");
//            startActivity(new Intent(Intent.ACTION_VIEW).setPackage("org.videolan.vlc").setDataAndTypeAndNormalize(uri, "video/*"));
//        });

        ActionBar bar = getSupportActionBar();
        if (bar != null) bar.setDisplayHomeAsUpEnabled(true);

//        mDownloadBtn.setVisibility(View.GONE);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if(itemId == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
