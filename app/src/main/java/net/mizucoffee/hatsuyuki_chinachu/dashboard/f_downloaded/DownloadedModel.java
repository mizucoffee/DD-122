package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_downloaded;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class DownloadedModel{

    private final PublishSubject<ArrayList<ProgramItem>> programItemsSubject = PublishSubject.create();
    final Observable<ArrayList<ProgramItem>> programItems = (Observable<ArrayList<ProgramItem>>) programItemsSubject;

    private DataManager mDataManager;

    DownloadedModel(SharedPreferences sp){
        mDataManager = new DataManager(sp);
    }

    public void getDownloadedList(){
        programItemsSubject.onNext(mDataManager.getDownloadedList());
    }
}
