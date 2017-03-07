package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver;

interface SelectServerPresenter extends OnCardClickListener,OnMenuItemClickListener {
    void onDestroy();
    void getList();
    void intentAdd();
    void checkStatus();
}
