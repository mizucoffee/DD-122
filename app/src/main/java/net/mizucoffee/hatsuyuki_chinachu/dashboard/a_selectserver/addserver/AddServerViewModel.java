package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.addserver;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.EditText;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded.RecordedCardRecyclerAdapter;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

class AddServerViewModel {

    AddServerInteractor mAddServerInteractor;

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> host = new ObservableField<>();
    public final ObservableField<String> port = new ObservableField<>();
    public final ObservableField<String> username = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();

    AddServerViewModel(AddServerView addServerView) {
        this.mAddServerInteractor = new AddServerInteractorImpl(mAddServerView.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
    }


    public void addServerConnection(ServerConnection sc) {
        mAddServerInteractor.addServerConnection(sc);
        mAddServerView.finishActivity();
    }

    public void editedServerconnection(ServerConnection sc, int position) {
        mAddServerInteractor.editedServerConnection(sc,position);
        mAddServerView.finishActivity();
    }

    public void onClickFab(View v){
        if(host.get().equals("")) {hostEt.setError("入力してください"); return;}
        if(Integer.parseInt(port.getText().toString()) < 1 || Integer.parseInt(portEt.getText().toString()) > 65535) {portEt.setError("ポート番号は1-65535で指定される必要があります"); return;}

        ServerConnection sc = new ServerConnection();

        if(nameEt.getText().toString().equals(""))
            sc.setName("Server");
        else
            sc.setName(nameEt.getText().toString());

        sc.setHost(hostEt.getText().toString());

        if(portEt.getText().toString().equals(""))
            sc.setPort("10772");
        else
            sc.setPort(portEt.getText().toString());

        sc.setUsername(usernameEt.getText().toString());
        sc.setPassword(passwordEt.getText().toString());

        if(isEdit)
            mPresenter.editedServerconnection(sc,position);
        else {
            sc.setId(System.currentTimeMillis());
            mPresenter.addServerConnection(sc);
        }
    }

    //=================================
    // DataBinding Custom Listener
    //=================================

    @BindingAdapter("error")
    public static void setAdapterList(EditText et, String text) {
        if (adapter != null) {
            Shirayuki.log(adapter.getListType().name());
            rv.setLayoutManager(new GridLayoutManager(rv.getContext(), adapter.getListType() == ListType.CARD_COLUMN2 ? 2 : 1));
        }
        rv.setAdapter(adapter);
    }
}
