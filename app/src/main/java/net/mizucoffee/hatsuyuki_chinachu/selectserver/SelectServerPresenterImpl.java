package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;

class SelectServerPresenterImpl implements SelectServerPresenter {

    private SelectServerView       mSelectServerView;
    private SelectServerInteractor mSelectServerInteractor;

    SelectServerPresenterImpl(SelectServerView selectServerView) {
        this.mSelectServerView       = selectServerView;
        this.mSelectServerInteractor = new SelectServerInteractorImpl(mSelectServerView.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
    }

    @Override
    public void onDestroy() {
        mSelectServerView = null;
    }

    @Override
    public void getList(){
        mSelectServerInteractor.loadServerConnections(new SelectServerInteractor.OnLoadFinishedListener() {
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

    @Override
    public boolean onMenuItemClick(MenuItem item,final int position) {
        switch (item.getItemId()){
            case R.id.menu_delete:
                mSelectServerView.showAlertDialog("Confirm", "Do you really want to deleteServerConnection this?", (dialogInterface, i) -> {
                    mSelectServerInteractor.deleteServerConnection(position);
                    getList();
                });
                break;

            case R.id.menu_edit:
                ServerConnection sc = mSelectServerInteractor.editServerConnection(position);
                mSelectServerView.intentEdit(sc,position);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {

    }


}
