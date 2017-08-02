package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.ChinachuModel;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GuideViewModel {

    public final ObservableField<GuideRecyclerAdapter> list = new ObservableField<>();

    private GuideRecyclerAdapter mAdapter;
    private HashMap<String,ArrayList<ProgramItem>> mProgramLists = new HashMap<>();

    private ArrayList<RecyclerView> mRecyclerViews;
    ServerConnection currentSc;

    GuideViewModel(GuideFragment fragment){
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
//                ChinachuModel.INSTANCE.getAllPrograms(serverConnection.getAddress());
                currentSc = serverConnection;
            }

            @Override public void onError(Throwable e) {}

            @Override public void onComplete() {}
        });
        ChinachuModel.programIds.subscribe(list -> {
            for (String id:list) mProgramLists.put(id,new ArrayList<>());
            mAdapter.setRecorded(mProgramLists.get(list.get(0)));


            mRecyclerViews.get(0).setAdapter(mAdapter);
        });

        ChinachuModel.allPrograms.doOnError(Throwable::printStackTrace).subscribe(program -> {
            ArrayList<ProgramItem> a = mProgramLists.get(program.getChannelId());
            a.add(program);
            mProgramLists.put(program.getChannelId(),a);

            mAdapter.notifyDataSetChanged();
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

    @BindingAdapter("recyclerColumn")
    public static void setRecyclerColumn(LinearLayout ll, int num) {
        for (int i = 0; i < num; i++) {
            RecyclerView r = new RecyclerView(ll.getContext());
            r.setLayoutManager(new GridLayoutManager(ll.getContext(), 1));
            r.setLayoutParams(new LinearLayoutCompat.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.addView(r);
        }
    }
}