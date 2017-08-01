package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import net.mizucoffee.hatsuyuki_chinachu.App;
import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.Program;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.ChinachuModel;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataModel;
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

    private LiveCardRecyclerAdapter mAdapter;
    private List<Program> mProgramList;

    private ServerConnection currentSc;

    LiveViewModel(LiveFragment liveFragment){
        subscribe();
        DataModel.Companion.getInstance().getCurrentServerConnection();
        mAdapter = new LiveCardRecyclerAdapter(liveFragment.getActivity(),currentSc.getAddress());
    }

    void subscribe(){
        ChinachuModel.broadcast.subscribe(response -> {
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
        DataModel.Companion.getInstance().getCurrentServerConnection.subscribe(sc -> {
            currentSc = sc;
        });
    }

    void reload(){
        DataModel.Companion.getInstance().getCurrentServerConnection();
        ChinachuModel.INSTANCE.getBroadcastList(currentSc.getAddress());
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