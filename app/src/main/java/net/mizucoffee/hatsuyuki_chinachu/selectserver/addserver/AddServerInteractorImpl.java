package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

import android.content.Context;
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
        SharedPreferences.Editor editor = data.edit();
        Gson gson = new Gson();
        editor.putString("ServerConnections", gson.toJson(sc));
        editor.apply();
    }

    @Override
    public void load(SharedPreferences data,OnLoadFinishedListener listenr){
        String con = data.getString("ServerConnections","" );
        if(con.equals("")){
            listenr.onNotFound();
            return;
        }
        Gson gson = new Gson();
        ArrayList<ServerConnection> sc = gson.fromJson(con, new TypeToken<ArrayList<ServerConnection>>(){}.getType());
        listenr.onSuccess(sc);
    }
}
