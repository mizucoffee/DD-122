package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;

/**
 * Created by mizucoffee on 12/20/16.
 */

public class AddServerInteractorImpl implements AddServerInteractor {

    @Override
    public void save(ArrayList<ServerConnection> sc, SharedPreferences data){

    }

    @Override
    public void load(SharedPreferences data,OnLoadFinishedListener listenr){
        String source = data.getString("ServerConnections","" );
        if(source.equals("")){
            listenr.onNotFound();
            return;
        }
        Gson gson = new Gson();
        ArrayList<ServerConnection> sc = gson.fromJson(source, new TypeToken<ArrayList<ServerConnection>>(){}.getType());
        listenr.onSuccess(sc);
    }
}
