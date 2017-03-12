package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded;

import android.content.Context;
import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

import static net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType.CARD_COLUMN1;
import static net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType.CARD_COLUMN2;
import static net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType.LIST;

public class RecordedCardRecyclerAdapter extends RecyclerView.Adapter<RecordedCardRecyclerAdapter.ViewHolder>{

    private List<Program> mProgram;
    private LayoutInflater      mLayoutInflater;
    private DashboardActivity   mContext;
    private DataManager         mDataManager;
    private ListType            mListType = CARD_COLUMN1;//TODO :標準

    public RecordedCardRecyclerAdapter(DashboardActivity context) {
        super();
        this.mContext        = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mDataManager    = new DataManager(context.getSharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
    }

    @Override
    public int getItemCount() {
        return mProgram.size();
    }

    public void setRecorded(List<Program> program){
        this.mProgram = program;
    }

    public void setListType(ListType listType){
        this.mListType = listType;
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, int position) {
        Program program = mProgram.get(vh.getAdapterPosition());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd E HH:mm", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

        vh.titleTv.setText(program.getTitle());
        vh.timeTv.setText(sdf1.format(program.getStart())+"-"+sdf2.format(program.getEnd()) + " " + (program.getSeconds() / 60) + "min");

        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.listener((Picasso picasso, Uri uri, Exception exception) ->
            Picasso.with(mContext).load("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + program.getId() + "/preview.png?pos=0").into(vh.imageView)
        );
        builder.build().load("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + program.getId() + "/preview.png?pos=30").into(vh.imageView);

        vh.linearLayout.setBackgroundColor(ContextCompat.getColor(mContext, Shirayuki.getBackgroundColorFromCategory(program.getCategory())));

        switch (mListType){
            case CARD_COLUMN1:
                vh.desTv.setText(program.getDetail());
            case CARD_COLUMN2:
                vh.playBtn.setOnClickListener((v) -> {
                    Uri uri = Uri.parse("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + program.getId() + "/watch.mp4");
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW).setPackage("org.videolan.vlc").setDataAndTypeAndNormalize(uri, "video/*"));
                });
                vh.detailBtn.setOnClickListener((v) -> mContext.startActivity(new Intent().setClass(mContext, RecordedDetailActivity.class).putExtra("program",new Gson().toJson(program))));
                break;
            case LIST:
                vh.linearLayout.setOnClickListener((v) -> mContext.startActivity(new Intent().setClass(mContext, RecordedDetailActivity.class).putExtra("program", new Gson().toJson(program))));
                vh.desTv.setText(program.getDetail());
                break;
        }
    }

    @Override
    public RecordedCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = mListType == CARD_COLUMN1 ? R.layout.card_program_layout_1column : mListType == CARD_COLUMN2 ? R.layout.card_program_layout_2column : R.layout.card_program_layout_list;
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
            if (mListType == CARD_COLUMN1 || mListType == LIST)
                desTv = ButterKnife.findById(v,R.id.des_tv);
            if (mListType == CARD_COLUMN1 || mListType == CARD_COLUMN2) {
                playBtn = ButterKnife.findById(v, R.id.play_btn);
                detailBtn = ButterKnife.findById(v, R.id.detail_btn);
                deleteBtn = ButterKnife.findById(v, R.id.delete_btn);
            }
            linearLayout = ButterKnife.findById(v,R.id.linearLayout);
        }
    }
}
