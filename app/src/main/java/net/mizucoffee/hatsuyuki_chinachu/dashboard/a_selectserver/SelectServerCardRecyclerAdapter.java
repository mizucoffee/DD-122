package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class SelectServerCardRecyclerAdapter extends RecyclerView.Adapter<SelectServerCardRecyclerAdapter.ViewHolder>{

    private ArrayList<ServerConnection> connections;
    private LayoutInflater mLayoutInflater;

    private final PublishSubject<ServerConnection> serverConnectionSubject = PublishSubject.create();
    final Observable<ServerConnection> serverConnection = (Observable<ServerConnection>) serverConnectionSubject;

    private final PublishSubject<MenuModel> menuSubject = PublishSubject.create();
    final Observable<MenuModel> menu = (Observable<MenuModel>) menuSubject;

    SelectServerCardRecyclerAdapter(Context context, ArrayList<ServerConnection> connections) {
        super();
        this.connections = connections;
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

        vh.card.setOnClickListener((view) -> serverConnectionSubject.onNext(connections.get(position)));

        vh.imageButton.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.select_popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                menuSubject.onNext(new MenuModel(item,vh.getAdapterPosition()));
                return false;
            });
            popup.show();
        });

    }

    @Override
    public SelectServerCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.card_select_server_layout, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView    serverName;
        private TextView    serverHost;
        private CardView    card;
        private ImageButton imageButton;

        private ViewHolder(View v) {
            super(v);
            serverName  = (TextView)    v.findViewById (R.id.server_name);
            serverHost  = (TextView)    v.findViewById (R.id.server_host);
            card        = (CardView)    v.findViewById (R.id.card_view);
            imageButton = (ImageButton) v.findViewById (R.id.image_button);
        }
    }
}
