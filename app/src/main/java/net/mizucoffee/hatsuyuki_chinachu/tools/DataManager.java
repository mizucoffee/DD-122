package net.mizucoffee.hatsuyuki_chinachu.tools;

import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import java.util.ArrayList;

public class DataManager {

    private SharedPreferences mSharedPreferences;

    public DataManager(SharedPreferences sharedPreferences){
        mSharedPreferences = sharedPreferences;
    }

    public void addServerConnection(ServerConnection serverConnection){
        if(loadServerConnections().equals("")){
            ArrayList<ServerConnection> sc = new ArrayList<>();
            sc.add(serverConnection);
            saveServerConnections(sc);
        } else {
            ArrayList<ServerConnection> sc = string2Array(loadServerConnections());
            sc.add(serverConnection);
            saveServerConnections(sc);
        }
    }

    public void editServerConnection(ServerConnection serverConnection,int index){
        ArrayList<ServerConnection> sc = string2Array(loadServerConnections());
        sc.set(index,serverConnection);
        saveServerConnections(sc);
    }

    private void saveServerConnections(ArrayList<ServerConnection> sc){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("ServerConnections", array2String(sc));
        editor.apply();
    }

    public String loadServerConnections(){
        return mSharedPreferences.getString("ServerConnections","" );
    }

    public ServerConnection getServerConnections(int position){
        String s = mSharedPreferences.getString("ServerConnections","" );
        return string2Array(s).get(position);
    }

    public void deleteServerConnection(int index){
        ArrayList<ServerConnection> sc = string2Array(loadServerConnections());
        sc.remove(index);
        saveServerConnections(sc);
    }

    public static ArrayList<ServerConnection> string2Array(String s){
        return new Gson().fromJson(s, new TypeToken<ArrayList<ServerConnection>>(){}.getType());
    }

    public static String array2String(ArrayList<ServerConnection> sc){
        return new Gson().toJson(sc);
    }

    public static ServerConnection string2ServerConnection(String s){
        return new Gson().fromJson(s, new TypeToken<ArrayList<ServerConnection>>(){}.getType());
    }

    public static String serverConnection2String(ServerConnection sc){
        return new Gson().toJson(sc);
    }

    public void setConnection(ServerConnection sc){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("ServerConnection", serverConnection2String(sc));
        editor.apply();
    }

    public ServerConnection getConnection(){
        return new Gson().fromJson(mSharedPreferences.getString("ServerConnection",""),ServerConnection.class);
    }
}
