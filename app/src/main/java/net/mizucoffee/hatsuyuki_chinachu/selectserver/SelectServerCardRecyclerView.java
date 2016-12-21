package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;

public class SelectServerCardRecyclerView extends RecyclerView {
    public SelectServerCardRecyclerView(Context context) {
        super(context);
    }

    public SelectServerCardRecyclerView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

//    public void setRecyclerAdapter(Context context, ArrayList<ServerConnection> connections){
//        setLayoutManager(new LinearLayoutManager(context));
//        setAdapter(new SelectServerCardRecyclerAdapter(context,connections));
//    }
}
