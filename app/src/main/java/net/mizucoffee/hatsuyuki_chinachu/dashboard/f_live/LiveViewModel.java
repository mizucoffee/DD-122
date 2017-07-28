package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import net.mizucoffee.hatsuyuki_chinachu.App;
import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.broadcasting.Program;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class LiveViewModel {

    public final ObservableField<LiveCardRecyclerAdapter> list = new ObservableField<>();

    private final PublishSubject<String> snackSubject = PublishSubject.create();
    final Observable<String> snack = (Observable<String>) snackSubject;

    private LiveModel mLiveModel;
    private LiveCardRecyclerAdapter mAdapter;
    private List<Program> mProgramList;

    LiveViewModel(LiveFragment liveFragment){
        mLiveModel = new LiveModel(liveFragment.getActivity().getSharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
        mAdapter = new LiveCardRecyclerAdapter(liveFragment.getActivity());
        subscribe();
    }

    void subscribe(){
        mLiveModel.liveItems.subscribe(response -> {
            Shirayuki.log("success");
            mProgramList = response;
            if(response == null){
                snackSubject.onNext(App.getContext().getString(R.string.failed_connect));
                list.set(null);
                return;
            }
            mAdapter.setLiveList(mProgramList);
            mAdapter.notifyDataSetChanged();
            list.set(mAdapter);
        });
    }

    void reload(){
        mLiveModel.getLiveList();
    }

    SearchView.OnQueryTextListener onSearch = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String word) {
            ArrayList<Program> l = new ArrayList<>();
            for(Program r: mProgramList) {
                String t = Normalizer.normalize(r.getTitle(), Normalizer.Form.NFKC);
                String c = Normalizer.normalize(r.getChannel().getName(), Normalizer.Form.NFKC);
                if(r.getTitle().contains(word)
                        || r.getChannel().getName().contains(word)
                        || c.contains(word)
                        || t.contains(word)) l.add(r);
            }
            mAdapter.setLiveList(l);
            mAdapter.notifyDataSetChanged();

            list.set(mAdapter);
            return false;
        }
    };

    //=================================
    // DataBinding Custom Listener
    //=================================

    @BindingAdapter("adapterList")
    public static void setAdapterList(RecyclerView rv, LiveCardRecyclerAdapter adapter) {
        rv.setAdapter(adapter);
    }
}