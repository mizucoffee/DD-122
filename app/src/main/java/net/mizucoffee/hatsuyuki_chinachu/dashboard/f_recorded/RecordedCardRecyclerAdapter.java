package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import net.mizucoffee.hatsuyuki_chinachu.BR;
import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import java.util.List;

import static net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType.CARD_COLUMN1;

public class RecordedCardRecyclerAdapter extends RecyclerView.Adapter<RecordedCardRecyclerAdapter.ViewHolder>{

    private List<ProgramItem> mProgram;
    private LayoutInflater      mLayoutInflater;
    private ListType            mListType = CARD_COLUMN1;//TODO :標準
    private DashboardActivity mContext;

    public RecordedCardRecyclerAdapter(DashboardActivity context) {
        super();
        this.mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mProgram.size();
    }

    void setRecorded(List<ProgramItem> program){
        this.mProgram = program;
    }

    void setListType(ListType listType){
        this.mListType = listType;
    }

    ListType getListType() {
        return mListType;
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, int position) {
        ProgramItem program = mProgram.get(position);
        vh.getBinding().setVariable(BR.program, program);

        switch (mListType){
            case CARD_COLUMN1:
            case CARD_COLUMN2:
                vh.playBtn.setOnClickListener((v) -> {
                    DataManager d = new DataManager(mContext.getSharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
                    Uri uri = Uri.parse("http://" + d.getServerConnection().getAddress() + "/api/recorded/" + program.getId() + "/watch.mp4");
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW).setPackage("org.videolan.vlc").setDataAndTypeAndNormalize(uri, "video/*"));
                });
                vh.detailBtn.setOnClickListener((v) -> mContext.startActivity(new Intent().setClass(mContext, RecordedDetailActivity.class).putExtra("program",new Gson().toJson(program))));
                break;
            case LIST:
                vh.linearLayout.setOnClickListener((v) -> mContext.startActivity(new Intent().setClass(mContext, RecordedDetailActivity.class).putExtra("program", new Gson().toJson(program))));
                break;
        }
    }

    @Override
    public RecordedCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

    @Override
    public int getItemViewType(int position) {
        return mListType.ordinal();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;
        private View playBtn;
        private View detailBtn;
        private View deleteBtn;
        private LinearLayout linearLayout;

        ViewHolder(View itemView, ListType listType) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

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
        }

        ViewDataBinding getBinding() {
            return binding;
        }
    }
}
