package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.model.gamma.Recorded;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.detail.DetailActivity;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;

public class RecordedCardListRecyclerAdapter extends RecyclerView.Adapter<RecordedCardListRecyclerAdapter.ViewHolder>{

    private List<Recorded> recorded;
    private LayoutInflater mLayoutInflater;
    private DashboardActivity context;
    private DataManager mDataManager;

    public RecordedCardListRecyclerAdapter(DashboardActivity context, List<Recorded> recorded) {
        super();
        this.recorded = recorded;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        mDataManager = new DataManager(context.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
    }

    @Override
    public int getItemCount() {
        return recorded.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, int position) {
        Recorded program = recorded.get(vh.getAdapterPosition());
        vh.titleTv.setText(program.getTitle());
        vh.timeTv.setText(new SimpleDateFormat("yyyy/MM/dd E").format(program.getStart()) + " " + (program.getSeconds() / 60) + "min");
        vh.desTv.setText(program.getDetail());

        Picasso.with(context).load("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + program.getId() + "/preview.png").into(vh.imageView);

        if ((position % 2) != 0) vh.linearLayout.setBackgroundColor(Color.argb(255,235,235,235)); else vh.linearLayout.setBackgroundColor(Color.argb(255,255,255,255));

        vh.linearLayout.setOnClickListener((v) -> {
            Intent intent = new Intent();
            intent.setClass(context, DetailActivity.class);
            Shirayuki.log(new Gson().toJson(program));
            intent.putExtra("program",new Gson().toJson(program));
            context.startActivity(intent);
        });
    }

    @Override
    public RecordedCardListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.card_recorded_layout_list, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView  titleTv;
        private TextView  timeTv;
        private TextView  desTv;
        private LinearLayout linearLayout;

        private ViewHolder(View v) {
            super(v);
            imageView    = ButterKnife.findById(v,R.id.image_view);
            titleTv      = ButterKnife.findById(v,R.id.title_tv);
            timeTv       = ButterKnife.findById(v,R.id.time_tv);
            desTv        = ButterKnife.findById(v,R.id.des_tv);
            linearLayout = ButterKnife.findById(v,R.id.layout);
        }
    }
}
