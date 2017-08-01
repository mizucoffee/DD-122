package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataModel;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerViewModel.ACTION_LIST.ALERT;
import static net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerViewModel.ACTION_LIST.FINISH;
import static net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerViewModel.ACTION_LIST.INTENT_ADD;
import static net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerViewModel.ACTION_LIST.INTENT_EDIT;
import static net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerViewModel.ACTION_LIST.SNACK_REGISTER;

public class SelectServerViewModel {

    enum ACTION_LIST {INTENT_ADD, INTENT_EDIT, FINISH, SNACK_REGISTER, SNACK_SELECT,ALERT}

    private final PublishSubject<Action> actionSubject = PublishSubject.create();
    final Observable<Action> action = (Observable<Action>) actionSubject;

    public final ObservableField<SelectServerCardRecyclerAdapter> adapter = new ObservableField<>();

    private SelectServerActivity activity;

    SelectServerViewModel(SelectServerActivity selectServerActivity) {
        activity = selectServerActivity;
        subscribe();
        DataModel.Companion.getInstance().getAllServerConnection();
    }

    private void subscribe() {
        DataModel.Companion.getInstance().getAllServerConnection.subscribe(sc -> {
            Shirayuki.log("success");
            if (sc.size() == 0) {
                actionSubject.onNext(new Action(SNACK_REGISTER));
            } else {
                adapter.set(new SelectServerCardRecyclerAdapter(activity, sc));

                adapter.get().cardOnClick.subscribe(serverConnection -> {
                    DataModel.Companion.getInstance().setCurrentServerConnection(serverConnection);
                    actionSubject.onNext(new Action(FINISH));
                });
                adapter.get().menuOnItemClick.subscribe(itemModel -> {
                    MenuItem item = itemModel.getItem();
                    int position = itemModel.getPosition();

                    switch (item.getItemId()) {
                        case R.id.menu_delete:
                            actionSubject.onNext(new Action(ALERT,(dialogInterface, i) -> {
                                DataModel.Companion.getInstance().removeServerConnectionWithIndex(i);
                                DataModel.Companion.getInstance().getAllServerConnection();
                            }));
                            break;

                        case R.id.menu_edit:
                            DataModel.Companion.getInstance().getServerConnectionWithIndex(position);
                            break;
                    }
                });
            }
        });
        DataModel.Companion.getInstance().getServerConnectionWithIndex.subscribe(sc -> actionSubject.onNext(new Action(INTENT_EDIT,sc.getIndex(),sc)));
    }

    void reload(){
        DataModel.Companion.getInstance().getAllServerConnection();
    }

    public void onClickFab(View v) {
        actionSubject.onNext(new Action(INTENT_ADD));
    }

    @BindingAdapter("adapterList")
    public static void setAdapterList(RecyclerView rv, SelectServerCardRecyclerAdapter adapter) {
        rv.setAdapter(adapter);
    }
}
