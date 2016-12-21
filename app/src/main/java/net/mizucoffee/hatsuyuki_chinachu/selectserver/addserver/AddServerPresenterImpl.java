package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

import android.content.Context;
import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;

/**
 * Created by mizucoffee on 12/20/16.
 */

public class AddServerPresenterImpl implements AddServerPresenter {

    AddServerView mAddServerView;
    AddServerInteractor mAddServerInteractor;

    public AddServerPresenterImpl(AddServerView addServerView) {
        this.mAddServerView = addServerView;
        this.mAddServerInteractor = new AddServerInteractorImpl();
    }

    @Override
    public void onDestroy() {
        mAddServerView = null;
    }

    @Override
    public void save(final ServerConnection sc) {
        final SharedPreferences data = mAddServerView.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE);
        mAddServerInteractor.load(data, new AddServerInteractor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(ArrayList<ServerConnection> asc) {
                asc.add(sc);
                mAddServerInteractor.save(asc,data);
                mAddServerView.finishActivity();
            }

            @Override
            public void onNotFound() {
                ArrayList<ServerConnection> asc = new ArrayList<>();
                asc.add(sc);
                mAddServerInteractor.save(asc,data);
                mAddServerView.finishActivity();
            }
        });
    }
}
