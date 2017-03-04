package net.mizucoffee.hatsuyuki_chinachu.dashboard.downloaded;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import java.util.List;

public class DownloadedInteractorImpl implements DownloadedInteractor {

    private DataManager mDataManager;

    DownloadedInteractorImpl(SharedPreferences sp){
        mDataManager = new DataManager(sp);
    }

    @Override
    public List<Program> getDownloadedList(){
        return mDataManager.getDownloadedList();
    }
}
