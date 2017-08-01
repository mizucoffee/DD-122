package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver;

import android.app.AlertDialog;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

public class Action {

    private SelectServerViewModel.ACTION_LIST action;
    private int position;
    private ServerConnection sc;
    private AlertDialog.OnClickListener onClickListener;

    public Action(SelectServerViewModel.ACTION_LIST act){
        action = act;
    }

    public AlertDialog.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public Action(SelectServerViewModel.ACTION_LIST act, AlertDialog.OnClickListener onClick){
        action = act;
        onClickListener = onClick;

    }

    public Action(SelectServerViewModel.ACTION_LIST act, int pos, ServerConnection sc){
        action = act;
        position = pos;
        this.sc = sc;
    }

    public ServerConnection getSc() {
        return sc;
    }

    public SelectServerViewModel.ACTION_LIST getAction() {
        return action;
    }

    public int getPosition() {
        return position;
    }
}
