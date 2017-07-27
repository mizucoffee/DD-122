package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver;

import android.view.MenuItem;

public class MenuModel {
    private MenuItem item;
    private int position;

    public MenuModel(MenuItem i,int p){
        item = i;
        position = p;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MenuItem getItem() {

        return item;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }
}
