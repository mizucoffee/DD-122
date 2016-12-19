package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardInteractor;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardInteractorImpl;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardView;

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
        mSelectServerInteractor.getList(new SelectServerInteractor.OnGetFinishedListener() {
            @Override
            public void onSuccess() {
                //mSelectServerView // リストに追加処理。View
            }
        });
    }

    @Override
    public void intentAdd(){
        mSelectServerView.intentAdd();
    }
}
