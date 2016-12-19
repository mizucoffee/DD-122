package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

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
    public void save(ServerConnection sc) {

    }
}
