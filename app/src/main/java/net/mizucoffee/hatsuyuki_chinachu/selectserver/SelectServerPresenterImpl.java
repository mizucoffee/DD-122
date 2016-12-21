package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.Context;
import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;

/**
 * Created by mizucoffee on 12/20/16.
 */

public class SelectServerPresenterImpl implements SelectServerPresenter {

    SelectServerView mSelectServerView;
    SelectServerInteractor mSelectServerInteractor;

    public SelectServerPresenterImpl(SelectServerView selectServerView) {
        this.mSelectServerView = selectServerView;
        this.mSelectServerInteractor = new SelectServerInteractorImpl();
    }

    @Override
    public void onDestroy() {
        mSelectServerView = null;
    }

    @Override
    public void getList(){
        final SharedPreferences data = mSelectServerView.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE);
        mSelectServerInteractor.load(data,new SelectServerInteractor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(ArrayList<ServerConnection> sc) {
                mSelectServerView.setRecyclerView(sc);
            }

            @Override
            public void onNotFound() {

            }
        });
    }

    @Override
    public void intentAdd(){
        mSelectServerView.intentAdd();
    }
}
