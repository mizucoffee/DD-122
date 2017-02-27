package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.model.gamma.Recorded;

import java.util.List;

import butterknife.ButterKnife;

class RecordedCardRecyclerAdapter extends RecyclerView.Adapter<RecordedCardRecyclerAdapter.ViewHolder>{

    private List<Recorded> recorded;
    private LayoutInflater mLayoutInflater;
//    private View.OnClickListener mOnClick;
//    private OnListMenuItemClickListener mOnListMenuClickListener;

    RecordedCardRecyclerAdapter(Context context, List<Recorded> recorded) {
        super();

        this.recorded = recorded;
        this.mLayoutInflater = LayoutInflater.from(context);
//        this.mOnClick = onClickListener;
//        this.mOnListMenuClickListener = onListMenuItemClickListener;
    }

    @Override
    public int getItemCount() {
        return recorded.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, int position) {
        vh.titleTv.setText(recorded.get(vh.getAdapterPosition()).getTitle());
        vh.desTv.setText(recorded.get(vh.getAdapterPosition()).getDetail());
    }

    @Override
    public RecordedCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.card_recorded_layout, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView  titleTv;
        private TextView  desTv;
        private CardView  card;
        private Button    playBtn;
        private Button    detailBtn;
        private Button    deleteBtn;

        private ViewHolder(View v) {
            super(v);
            imageView  = ButterKnife.findById(v,R.id.image_view);
            titleTv    = ButterKnife.findById(v,R.id.title_tv);
            desTv      = ButterKnife.findById(v,R.id.des_tv);
            card       = ButterKnife.findById(v,R.id.card_view);
            playBtn    = ButterKnife.findById(v,R.id.play_btn);
            detailBtn  = ButterKnife.findById(v,R.id.detail_btn);
            deleteBtn  = ButterKnife.findById(v,R.id.delete_btn);
        }
    }
}
