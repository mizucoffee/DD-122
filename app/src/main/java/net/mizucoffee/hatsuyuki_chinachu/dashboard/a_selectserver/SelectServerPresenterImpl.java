package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;

import net.mizucoffee.hatsuyuki_chinachu.App;
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
                mSelectServerView.showAlertDialog(App.getContext().getString(R.string.confirm), App.getContext().getString(R.string.confirm_delete), (dialogInterface, i) -> {
                    mSelectServerInteractor.deleteServerConnection(position);
                    mSelectServerInteractor.loadServerConnections(new SelectServerInteractor.OnLoadFinishedListener() {
                        @Override
                        public void onSuccess(ArrayList<ServerConnection> sc) {
                            if(sc.size() != 0)
                                mSelectServerInteractor.setServerConnection(sc.get(0));
                        }

                        @Override
                        public void onNotFound() {

                        }
                    });
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
    public void onCardClick(View view,int position) {
        mSelectServerInteractor.loadServerConnections(new SelectServerInteractor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(ArrayList<ServerConnection> sc) {
                mSelectServerInteractor.setServerConnection(sc.get(position));
                mSelectServerView.finishActivity();
            }

            @Override
            public void onNotFound() {

            }
        });
    }

    @Override
    public void checkStatus() {

        mSelectServerInteractor.loadServerConnections(new SelectServerInteractor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(ArrayList<ServerConnection> sc) {
                if(sc.size() == 0){
                    mSelectServerView.showSnackbar(App.getContext().getString(R.string.lets_register));
                }else if(mSelectServerInteractor.getServerConnection() == null) {
                    mSelectServerView.showSnackbar(App.getContext().getString(R.string.lets_select_server));
                }
            }

            @Override
            public void onNotFound() {
                mSelectServerView.showSnackbar(App.getContext().getString(R.string.lets_register));
            }
        });

    }

}
