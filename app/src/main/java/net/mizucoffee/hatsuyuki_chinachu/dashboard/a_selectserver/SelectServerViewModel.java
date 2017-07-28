package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerViewModel.ACTION_LIST.ALERT;
import static net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerViewModel.ACTION_LIST.FINISH;
import static net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerViewModel.ACTION_LIST.INTENT_ADD;
import static net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerViewModel.ACTION_LIST.INTENT_EDIT;
import static net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerViewModel.ACTION_LIST.SNACK_REGISTER;
import static net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerViewModel.ACTION_LIST.SNACK_SELECT;

public class SelectServerViewModel {

    enum ACTION_LIST {INTENT_ADD, INTENT_EDIT, FINISH, SNACK_REGISTER, SNACK_SELECT,ALERT}

    private final PublishSubject<ActionModel> actionSubject = PublishSubject.create();
    final Observable<ActionModel> action = (Observable<ActionModel>) actionSubject;

    private SelectServerModel mSelectServerModel;
    public final ObservableField<SelectServerCardRecyclerAdapter> adapter = new ObservableField<>();

    private SelectServerActivity activity;

    SelectServerViewModel(SelectServerActivity selectServerActivity) {
        this.mSelectServerModel = new SelectServerModel(selectServerActivity.getSharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
        activity = selectServerActivity;
        subscribe();
        mSelectServerModel.loadServerConnections();
    }

    private void subscribe() {
        mSelectServerModel.serverConnections.subscribe(serverConnections -> {
            Shirayuki.log("success");
            if (serverConnections.size() == 0) {
                actionSubject.onNext(new ActionModel(SNACK_REGISTER));
            } else {
                if (mSelectServerModel.getServerConnection() == null)
                    actionSubject.onNext(new ActionModel(SNACK_SELECT));
                adapter.set(new SelectServerCardRecyclerAdapter(activity, serverConnections));

                adapter.get().serverConnection.subscribe(serverConnection -> {
                    mSelectServerModel.setServerConnection(serverConnection);
                    actionSubject.onNext(new ActionModel(FINISH));
                });
                adapter.get().menu.subscribe(itemModel -> {
                    MenuItem item = itemModel.getItem();
                    int position = itemModel.getPosition();

                    switch (item.getItemId()) {
                        case R.id.menu_delete:
                            actionSubject.onNext(new ActionModel(ALERT,(dialogInterface, i) -> {
                                mSelectServerModel.deleteServerConnection(position);
                                mSelectServerModel.loadServerConnections();
                            }));
                            break;

                        case R.id.menu_edit:
                            actionSubject.onNext(new ActionModel(INTENT_EDIT,position,mSelectServerModel.editServerConnection(position)));
                            break;
                    }
                });
            }
        });
    }

    void reload(){
        mSelectServerModel.loadServerConnections();
    }

    public void onClickFab(View v) {
        actionSubject.onNext(new ActionModel(INTENT_ADD));
    }

    @BindingAdapter("adapterList")
    public static void setAdapterList(RecyclerView rv, SelectServerCardRecyclerAdapter adapter) {
        rv.setAdapter(adapter);
    }
}
