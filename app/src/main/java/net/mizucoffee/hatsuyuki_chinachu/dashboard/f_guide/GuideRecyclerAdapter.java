package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.mizucoffee.hatsuyuki_chinachu.BR;
import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;

import java.util.List;

public class GuideRecyclerAdapter extends RecyclerView.Adapter<GuideRecyclerAdapter.ViewHolder>{

    private List<ProgramItem> mProgram;
    private LayoutInflater      mLayoutInflater;
    private DashboardActivity mContext;

    public GuideRecyclerAdapter(DashboardActivity context) {
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

    @Override
    public void onBindViewHolder(final ViewHolder vh, int position) {
        ProgramItem program = mProgram.get(position);
        vh.getBinding().setVariable(BR.program, program);

        vh.root.setOnClickListener((v) -> {
        });

        vh.titleTv.setText(program.getSimpleDate() + " " + program.getTitle());
        vh.desctiptionTv.setText(program.getDescription());

    }

    @Override
    public GuideRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.recycler_program_list, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;
        private TextView titleTv;
        private TextView desctiptionTv;
        private LinearLayout root;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

            titleTv = (TextView) itemView.findViewById(R.id.title_tv);
            desctiptionTv = (TextView) itemView.findViewById(R.id.des_tv);
            root = (LinearLayout) itemView.findViewById(R.id.root_ll);
        }

        ViewDataBinding getBinding() {
            return binding;
        }
    }
}
