package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.model.gamma.Recorded;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;

class RecordedCardRecyclerAdapter extends RecyclerView.Adapter<RecordedCardRecyclerAdapter.ViewHolder>{

    private List<Recorded> recorded;
    private LayoutInflater mLayoutInflater;
    private DashboardActivity context;
    private DataManager mDataManager;

    RecordedCardRecyclerAdapter(DashboardActivity context, List<Recorded> recorded) {
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
        vh.desTv.setText(program.getDetail());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd E HH:mm");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        vh.timeTv.setText(sdf.format(program.getStart())+"-"+sdf1.format(program.getEnd()) + " " + (program.getSeconds() / 60) + "min");
        Picasso.with(context).load("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + program.getId() + "/preview.png").into(vh.imageView);
        //TODO: IP変更
        vh.playBtn.setOnClickListener((v) -> {
            Uri uri = Uri.parse("http://" + mDataManager.getServerConnection().getAddress() + "/api/recorded/" + program.getId() + "/watch.mp4");
            Intent vlcIntent = new Intent(Intent.ACTION_VIEW);
            vlcIntent.setPackage("org.videolan.vlc");
            vlcIntent.setDataAndTypeAndNormalize(uri, "video/*");
            context.startActivity(vlcIntent);
        });
    }

    @Override
    public RecordedCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.card_recorded_layout, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView  titleTv;
        private TextView  timeTv;
        private TextView  desTv;
        private Button    playBtn;
        private Button    detailBtn;
        private Button    deleteBtn;

        private ViewHolder(View v) {
            super(v);
            imageView  = ButterKnife.findById(v,R.id.image_view);
            titleTv    = ButterKnife.findById(v,R.id.title_tv);
            timeTv     = ButterKnife.findById(v,R.id.time_tv);
            desTv      = ButterKnife.findById(v,R.id.des_tv);
            playBtn    = ButterKnife.findById(v,R.id.play_btn);
            detailBtn  = ButterKnife.findById(v,R.id.detail_btn);
            deleteBtn  = ButterKnife.findById(v,R.id.delete_btn);
        }
    }
}
