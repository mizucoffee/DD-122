package net.mizucoffee.hatsuyuki_chinachu.dashboard.downloaded;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.recorded.Recorded;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import java.util.List;

/**
 * Created by mizucoffee on 2/27/17.
 */

public class DownloadedInteractorImpl implements DownloadedInteractor {

    private DataManager mDataManager;

    DownloadedInteractorImpl(SharedPreferences sp){
        mDataManager = new DataManager(sp);
    }

    @Override
    public List<Recorded> getDownloadedList(){
        return mDataManager.getDownloadedList();
    }

    @Override
    public void setDownloadedList(List<Recorded> recorded){
        mDataManager.setDownloadedList(recorded);
    }
}
