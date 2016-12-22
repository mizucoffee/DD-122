package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    public SelectServerCardRecyclerAdapter(Context context, ArrayList<ServerConnection> connections, View.OnClickListener onClickListener) {
        super();

        this.connections = connections;
        this.mOnClick = onClickListener;
        this.mLayoutInflater = LayoutInflater.from(context);
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
    }

    @Override
    public SelectServerCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.select_server_card_layout, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView serverName;
        TextView serverHost;
        Button card;

        public ViewHolder(View v) {
            super(v);
            serverName = (TextView)v.findViewById(R.id.server_name);
            serverHost = (TextView)v.findViewById(R.id.server_host);
            card = (Button)v.findViewById(R.id.card);
        }
    }
}
