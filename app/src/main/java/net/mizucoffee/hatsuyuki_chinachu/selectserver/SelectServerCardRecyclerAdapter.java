package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;

/**
 * Created by mizucoffee on 12/20/16.
 */

public class SelectServerCardRecyclerAdapter extends RecyclerView.Adapter<SelectServerCardRecyclerAdapter.ViewHolder>{
    private ArrayList<ServerConnection> connections;
    private LayoutInflater mLayoutInflater;
    private View.OnClickListener mOnClick;
    private OnListMenuItemClickListener mOnListMenuClickListener;

    public SelectServerCardRecyclerAdapter(Context context, ArrayList<ServerConnection> connections, View.OnClickListener onClickListener,OnListMenuItemClickListener onListMenuItemClickListener) {
        super();

        this.connections = connections;
        this.mOnClick = onClickListener;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mOnListMenuClickListener = onListMenuItemClickListener;
    }

    @Override
    public int getItemCount() {
        return connections.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, int position) {
        vh.serverName.setText(connections.get(vh.getAdapterPosition()).getName());
        vh.serverHost.setText(connections.get(vh.getAdapterPosition()).getHost());

        vh.card.setOnClickListener(mOnClick);
        vh.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(),view );
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.select_popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return mOnListMenuClickListener.onMenuItemClick(item,vh.getAdapterPosition());
                    }
                });
                popup.show();
            }
        });

    }

    @Override
    public SelectServerCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.select_server_card_layout, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView serverName;
        TextView serverHost;
        CardView card;
        ImageButton imageButton;

        public ViewHolder(View v) {
            super(v);
            serverName = (TextView)v.findViewById(R.id.server_name);
            serverHost = (TextView)v.findViewById(R.id.server_host);
            card = (CardView) v.findViewById(R.id.card_view);
            imageButton = (ImageButton)v.findViewById(R.id.imageButton);
        }
    }
}
