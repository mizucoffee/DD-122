package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.view.MenuItem;
import android.view.View;

interface OnMenuItemClickListener {
    boolean onMenuItemClick(MenuItem item, int position);
}
interface OnCardClickListener {
    void onCardClick(View v, int position);
}