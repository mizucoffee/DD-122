package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.broadcasting.Program;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class LiveCardRecyclerAdapter extends RecyclerView.Adapter<LiveCardRecyclerAdapter.ViewHolder>{

    private List<Program> mProgram;
    private LayoutInflater      mLayoutInflater;
    private DashboardActivity   mContext;
    private DataManager         mDataManager;

    public LiveCardRecyclerAdapter(DashboardActivity context) {
        super();
        this.mContext        = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mDataManager    = new DataManager(context.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
    }

    @Override
    public int getItemCount() {
        return mProgram.size();
    }

    public void setLiveList(List<Program> program){
        this.mProgram = program;
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, int position) {
        Program program = mProgram.get(vh.getAdapterPosition());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd E HH:mm", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

        vh.channelTv.setText(program.getChannel().getName());
        vh.titleTv.setText(program.getTitle());
//        vh.detailTv.setText(program.getDetail());
        Picasso.with(mContext).load("http://" + mDataManager.getServerConnection().getAddress() + "/api/channel/" + program.getChannel().getId() + "/logo.png").into(vh.imageView);
        vh.linearLayout.setBackgroundColor(ContextCompat.getColor(mContext, Shirayuki.getBackgroundColorFromCategory(program.getCategory())));


    }

    @Override
    public LiveCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.card_live_layout, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView  channelTv;
        private TextView  titleTv;
//        private TextView detailTv;
        private LinearLayout linearLayout;

        private ViewHolder(View v) {
            super(v);
            imageView = ButterKnife.findById(v,R.id.channel_iv);
            channelTv   = ButterKnife.findById(v,R.id.channel_tv);
            titleTv   = ButterKnife.findById(v,R.id.title_tv);
//            detailTv = ButterKnife.findById(v,R.id.detail_tv);
            linearLayout = ButterKnife.findById(v,R.id.linearLayout);
        }
    }
}
