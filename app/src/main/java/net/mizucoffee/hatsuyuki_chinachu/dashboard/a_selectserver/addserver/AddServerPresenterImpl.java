package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.addserver;

import android.content.Context;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

class AddServerPresenterImpl implements AddServerPresenter {

    AddServerView mAddServerView;
    AddServerInteractor mAddServerInteractor;

    AddServerPresenterImpl(AddServerView addServerView) {
        this.mAddServerView = addServerView;
        this.mAddServerInteractor = new AddServerInteractorImpl(mAddServerView.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
    }

    @Override
    public void onDestroy() {
        mAddServerView = null;
    }

    @Override
    public void addServerConnection(ServerConnection sc) {
        mAddServerInteractor.addServerConnection(sc);
        mAddServerView.finishActivity();
    }

    @Override
    public void editedServerconnection(ServerConnection sc, int position) {
        mAddServerInteractor.editedServerConnection(sc,position);
        mAddServerView.finishActivity();
    }
}
