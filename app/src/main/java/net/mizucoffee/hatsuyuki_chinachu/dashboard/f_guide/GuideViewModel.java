package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.Program;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.ChinachuModel;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataModel;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GuideViewModel {

    public final ObservableField<GuideRecyclerAdapter> list = new ObservableField<>();

    private GuideRecyclerAdapter mAdapter;
    private ArrayList<ProgramItem> mProgramList = new ArrayList<>();

    private ArrayList<RecyclerView> mRecyclerViews;
    ServerConnection currentSc;

    GuideViewModel(GuideFragment fragment, ArrayList<RecyclerView> r){
        mRecyclerViews = r;
        mAdapter = new GuideRecyclerAdapter((DashboardActivity)fragment.getActivity());
        subscribe();
        DataModel.Companion.getInstance().getCurrentServerConnection();
        ChinachuModel.INSTANCE.getAllPrograms(currentSc.getAddress());
    }

    private void subscribe(){
        DataModel.Companion.getInstance().getCurrentServerConnection.subscribe(new Observer<ServerConnection>() {
            @Override public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(ServerConnection serverConnection) {
                ChinachuModel.INSTANCE.getAllPrograms(serverConnection.getAddress());
                currentSc = serverConnection;
            }

            @Override public void onError(Throwable e) {}

            @Override public void onComplete() {}
        });
        ChinachuModel.allPrograms.subscribe(programs -> {
            for (Program p:programs) {
                Shirayuki.log(p.getTitle());
            }
        });
    }

    private void setAdapterToRecyclerView(List<ProgramItem> programList){
        mAdapter.setRecorded(programList);

        mAdapter.notifyDataSetChanged();
        list.set(mAdapter);
    }

    void reload(){
        DataModel.Companion.getInstance().getCurrentServerConnection();
    }

    //=================================
    // DataBinding Custom Listener
    //=================================

    @BindingAdapter("adapterList")
    public static void setAdapterList(RecyclerView rv, GuideRecyclerAdapter adapter) {
        rv.setAdapter(adapter);
    }

    @BindingAdapter("gridColumn")
    public static void setGridColumn(RecyclerView rv, int i) {
        rv.setLayoutManager(new GridLayoutManager(rv.getContext(), i));
    }
}