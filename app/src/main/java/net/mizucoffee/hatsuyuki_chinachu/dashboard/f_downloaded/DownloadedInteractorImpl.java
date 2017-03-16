package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_downloaded;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import java.util.List;

public class DownloadedInteractorImpl implements DownloadedInteractor {

    private DataManager mDataManager;

    DownloadedInteractorImpl(SharedPreferences sp){
        mDataManager = new DataManager(sp);
    }

    @Override
    public List<ProgramItem> getDownloadedList(){
        return mDataManager.getDownloadedList();
    }
}
