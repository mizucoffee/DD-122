package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;

/**
 * Created by mizucoffee on 12/20/16.
 */

public class SelectServerInteractorImpl implements SelectServerInteractor {

    @Override
    public void load(SharedPreferences data,OnLoadFinishedListener listener){
        String con = data.getString("ServerConnections","" );
        if(con.equals("")){
            Log.i("FUBUKI", "load: notfound");
            listener.onNotFound();
            return;
        }
        Gson gson = new Gson();
        ArrayList sc = gson.fromJson(con, new TypeToken<ArrayList<ServerConnection>>(){}.getType());
        Log.i("FUBUKI", "load: success");
        listener.onSuccess(sc);
    }
}
