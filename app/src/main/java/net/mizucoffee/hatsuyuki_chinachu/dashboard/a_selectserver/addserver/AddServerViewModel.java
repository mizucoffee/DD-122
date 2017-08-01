package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.addserver;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.EditText;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataModel;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class AddServerViewModel {

    public static final int FINISHED = 0;

    private final PublishSubject<Integer> finishedSubject = PublishSubject.create();
    final Observable<Integer> finished = (Observable<Integer>) finishedSubject;

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> host = new ObservableField<>();
    public final ObservableField<String> port = new ObservableField<>();
    public final ObservableField<String> username = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();

    public final ObservableField<String> nameError = new ObservableField<>();
    public final ObservableField<String> hostError = new ObservableField<>();
    public final ObservableField<String> portError = new ObservableField<>();

    private int mPosition = -1;

    AddServerViewModel(AddServerActivity activity) {
        name.set("Server");
        host.set("");
        port.set("10772");
        username.set("");
        password.set("");
    }

    void setData(ServerConnection sc,int pos){
        name.set(sc.getName());
        host.set(sc.getHost());
        port.set(sc.getPort());
        username.set(sc.getUsername());
        password.set(sc.getPassword());
        mPosition = pos;
    }

    public void onClickFab(View v){
        if(name.get().equals("")) {nameError.set("入力してください"); return;}
        if(host.get().equals("")) {hostError.set("入力してください"); return;}
        if(Integer.parseInt(port.get()) < 1 || Integer.parseInt(port.get()) > 65535) {portError.set("ポート番号は1-65535で指定される必要があります"); return;}

        ServerConnection sc = new ServerConnection();

        sc.setName(name.get().equals("") ? "Server" : name.get());
        sc.setHost(host.get());
        sc.setPort(port.get().equals("") ? "10772" : port.get());

        sc.setUsername(username.get());
        sc.setPassword(password.get());

        if(mPosition >= 0)
            DataModel.Companion.getInstance().setServerConnection(sc,mPosition);
        else
            DataModel.Companion.getInstance().addServerConnection(sc);

        finishedSubject.onNext(FINISHED);
    }

    //=================================
    // DataBinding Custom Listener
    //=================================

    @BindingAdapter("error")
    public static void setError(EditText et, String text) {
        if (et != null && text != null) {
                et.setError(text);
        }
    }
}
