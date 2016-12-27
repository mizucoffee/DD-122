package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.view.View;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;

/**
 * Created by mizucoffee on 12/20/16.
 */

public class SelectServerPresenterImpl implements SelectServerPresenter {

    private SelectServerView mSelectServerView;
    private SelectServerInteractor mSelectServerInteractor;

    public SelectServerPresenterImpl(SelectServerView selectServerView) {
        this.mSelectServerView = selectServerView;
        this.mSelectServerInteractor = new SelectServerInteractorImpl(mSelectServerView.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
    }

    @Override
    public void onDestroy() {
        mSelectServerView = null;
    }

    @Override
    public void getList(){
        mSelectServerInteractor.load(new SelectServerInteractor.OnLoadFinishedListener() {
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
        if(item.getItemId() == R.id.menu_delete){
            mSelectServerView.showAlertDialog("Confirm", "Do you really want to delete this?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mSelectServerInteractor.delete(position);
                    getList();
                }
            });
        }
        return false;
    }

    @Override
    public void onClick(View view) {

    }


}
