package net.mizucoffee.hatsuyuki_chinachu.dashboard.downloaded;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.recorded.Recorded;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class DownloadedCardRecyclerAdapter extends RecyclerView.Adapter<DownloadedCardRecyclerAdapter.ViewHolder>{

    private List<Recorded>      mRecorded;
    private LayoutInflater      mLayoutInflater;
    private DashboardActivity   mContext;
    private DataManager         mDataManager;
    private ListType            mListType = ListType.CARD_COLUMN1;//TODO :標準

    public DownloadedCardRecyclerAdapter(DashboardActivity context) {
        super();
        this.mContext        = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mDataManager    = new DataManager(context.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
    }

    @Override
    public int getItemCount() {
        return mRecorded.size();
    }

    public void setRecorded(List<Recorded> recorded){
        this.mRecorded = recorded;
    }

    public void setListType(ListType listType){
        this.mListType = listType;
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, int position) {
        Recorded program = mRecorded.get(vh.getAdapterPosition());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd E HH:mm", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

        vh.titleTv.setText(program.getTitle());
        vh.timeTv.setText(sdf1.format(program.getStart())+"-"+sdf2.format(program.getEnd()) + " " + (program.getSeconds() / 60) + "min");
        Bitmap bmImg = BitmapFactory.decodeFile(mContext.getExternalFilesDir(null).getAbsolutePath() + "/image/" + program.getId() + ".png");
        vh.imageView.setImageBitmap(bmImg);
        vh.linearLayout.setBackgroundColor(ContextCompat.getColor(mContext, Shirayuki.getBackgroundColorFromCategory(program.getCategory())));

        switch (mListType){
            case CARD_COLUMN1:
                vh.desTv.setText(program.getDetail());
            case CARD_COLUMN2:
                vh.playBtn.setOnClickListener((v) -> {
                    Uri uri = Uri.parse("file://" + mContext.getExternalFilesDir(null).getAbsolutePath() + "/video/" + program.getId()+".mp4");
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW).setPackage("org.videolan.vlc").setDataAndTypeAndNormalize(uri, "video/*"));
                });
                vh.detailBtn.setOnClickListener((v) -> mContext.startActivity(new Intent().setClass(mContext, DownloadedDetailActivity.class).putExtra("program",new Gson().toJson(program))));
                break;
            case LIST:
                vh.linearLayout.setOnClickListener((v) -> mContext.startActivity(new Intent().setClass(mContext, DownloadedDetailActivity.class).putExtra("program", new Gson().toJson(program))));
                vh.desTv.setText(program.getDetail());
                break;
        }
    }

    @Override
    public DownloadedCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = mListType == ListType.CARD_COLUMN1 ? R.layout.card_recorded_layout_1column : mListType == ListType.CARD_COLUMN2 ? R.layout.card_recorded_layout_2column : R.layout.card_recorded_layout_list;
        return new ViewHolder(mLayoutInflater.inflate(layout, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView  titleTv;
        private TextView  timeTv;
        private TextView  desTv;
        private View    playBtn;
        private View    detailBtn;
        private View    deleteBtn;
        private LinearLayout linearLayout;

        private ViewHolder(View v) {
            super(v);
            imageView = ButterKnife.findById(v,R.id.image_view);
            titleTv   = ButterKnife.findById(v,R.id.title_tv);
            timeTv    = ButterKnife.findById(v,R.id.time_tv);
            if (mListType == ListType.CARD_COLUMN1 || mListType == ListType.LIST)
                desTv = ButterKnife.findById(v,R.id.des_tv);
            if (mListType == ListType.CARD_COLUMN1 || mListType == ListType.CARD_COLUMN2) {
                playBtn = ButterKnife.findById(v, R.id.play_btn);
                detailBtn = ButterKnife.findById(v, R.id.detail_btn);
                deleteBtn = ButterKnife.findById(v, R.id.delete_btn);
            }
            linearLayout = ButterKnife.findById(v,R.id.linearLayout);
        }
    }
}
