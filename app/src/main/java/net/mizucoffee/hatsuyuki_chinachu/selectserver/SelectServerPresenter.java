package net.mizucoffee.hatsuyuki_chinachu.selectserver;

interface SelectServerPresenter extends OnCardClickListener,OnMenuItemClickListener {
    void onDestroy();
    void getList();
    void intentAdd();
}
