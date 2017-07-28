package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_downloaded;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class DownloadedCardRecyclerAdapter extends RecyclerView.Adapter<DownloadedCardRecyclerAdapter.ViewHolder>{

    private List<ProgramItem>       mProgramList;
    private LayoutInflater      mLayoutInflater;
    private Context mContext;
    private ListType            mListType = ListType.CARD_COLUMN1;//TODO :標準

    public DownloadedCardRecyclerAdapter(Context context) {
        super();
        this.mContext        = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mProgramList.size();
    }

    public void setDownloaded(List<ProgramItem> program){
        this.mProgramList = program;
    }

    public void setListType(ListType listType){
        this.mListType = listType;
    }

    ListType getListType() {
        return mListType;
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, int position) {
        ProgramItem program = mProgramList.get(vh.getAdapterPosition());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd E HH:mm", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

        vh.titleTv.setText(program.getTitle());
        vh.timeTv.setText(sdf1.format(program.getStart())+"-"+sdf2.format(program.getEnd()) + " " + (program.getSeconds() / 60) + "min");
        vh.imageView.setImageBitmap(BitmapFactory.decodeFile(mContext.getExternalFilesDir(null).getAbsolutePath() + "/image/" + program.getId() + ".png"));
        vh.linearLayout.setBackgroundColor(ContextCompat.getColor(mContext, Shirayuki.getBackgroundColorFromCategory(program.getCategory())));

        switch (mListType){
            case CARD_COLUMN1:
                vh.desTv.setText(program.getDescription());
            case CARD_COLUMN2:
                vh.playBtn.setOnClickListener((v) -> {
                    Uri uri = Uri.parse("file://" + mContext.getExternalFilesDir(null).getAbsolutePath() + "/video/" + program.getId()+".mp4");
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW).setPackage("org.videolan.vlc").setDataAndTypeAndNormalize(uri, "video/*"));
                });
                vh.detailBtn.setOnClickListener((v) -> mContext.startActivity(new Intent().setClass(mContext, DownloadedDetailActivity.class).putExtra("program",new Gson().toJson(program))));
                break;
            case LIST:
                vh.linearLayout.setOnClickListener((v) -> mContext.startActivity(new Intent().setClass(mContext, DownloadedDetailActivity.class).putExtra("program", new Gson().toJson(program))));
                vh.desTv.setText(program.getDescription());
                break;
        }
    }

    @Override
    public DownloadedCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (ListType.values()[viewType]){
            case CARD_COLUMN1:
                return new ViewHolder(mLayoutInflater.inflate(R.layout.card_program_layout_1column, parent, false),ListType.values()[viewType]);
            case CARD_COLUMN2:
                return new ViewHolder(mLayoutInflater.inflate(R.layout.card_program_layout_2column, parent, false),ListType.values()[viewType]);
            case LIST:
                return new ViewHolder(mLayoutInflater.inflate(R.layout.card_program_layout_list, parent, false),ListType.values()[viewType]);
        }
        return new ViewHolder(mLayoutInflater.inflate(R.layout.card_program_layout_1column, parent, false),ListType.values()[viewType]);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;
        private ImageView imageView;
        private TextView  titleTv;
        private TextView  timeTv;
        private TextView  desTv;
        private View    playBtn;
        private View    detailBtn;
        private View    deleteBtn;
        private LinearLayout linearLayout;

        private ViewHolder(View v, ListType listType) {
            super(v);
            binding = DataBindingUtil.bind(v);

            imageView = ButterKnife.findById(v,R.id.image_view);
            titleTv   = ButterKnife.findById(v,R.id.title_tv);
            timeTv    = ButterKnife.findById(v,R.id.time_tv);
            switch (listType){
                case CARD_COLUMN1:
                case CARD_COLUMN2:
                    playBtn = itemView.findViewById(R.id.play_btn);
                    detailBtn = itemView.findViewById(R.id.detail_btn);
                    deleteBtn = itemView.findViewById(R.id.delete_btn);
                    break;
                case LIST:
                    linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
                    break;
            }

            if (mListType == ListType.CARD_COLUMN1 || mListType == ListType.LIST)
                desTv = ButterKnife.findById(v,R.id.des_tv);
            if (mListType == ListType.CARD_COLUMN1 || mListType == ListType.CARD_COLUMN2) {
                playBtn = ButterKnife.findById(v, R.id.play_btn);
                detailBtn = ButterKnife.findById(v, R.id.detail_btn);
                deleteBtn = ButterKnife.findById(v, R.id.delete_btn);
            }
            linearLayout = ButterKnife.findById(v,R.id.linearLayout);
        }

        ViewDataBinding getBinding() {
            return binding;
        }
    }
}
