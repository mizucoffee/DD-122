package net.mizucoffee.hatsuyuki_chinachu.tools;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import net.mizucoffee.hatsuyuki_chinachu.R;

import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;

/**
 * Shirayuki Module
 * しらゆきモジュール
 *
 * Shirayuki Tool Module
 * 汎用ツールモジュール
 */

public class Shirayuki {

    public static void initActivity(AppCompatActivity activity){
        activity.setSupportActionBar(findById(activity,R.id.toolbar));
        ButterKnife.bind(activity);
    }

    public static void initFragment(Fragment f,View view){
        ButterKnife.bind(f,view);
    }

    public static void log(String msg){
        Log.i("SHIRAYUKI",msg);
    }

    public static int getColorFromCategory(String category){
        int color = R.color.etc;
        switch (category){
            case "anime":       color =  R.color.anime;        break;
            case "information": color =  R.color.information;  break;
            case "news":        color =  R.color.news;         break;
            case "sports":      color =  R.color.sports;       break;
            case "variety":     color =  R.color.variety;      break;
            case "drama":       color =  R.color.drama;        break;
            case "music":       color =  R.color.music;        break;
            case "cinema":      color =  R.color.cinema;       break;
            case "theater":     color =  R.color.theater;      break;
            case "documentary": color =  R.color.documentary;  break;
            case "hobby":       color =  R.color.hobby;        break;
            case "welfare":     color =  R.color.welfare;      break;
            case "etc":         color =  R.color.etc;          break;
        }
        return color;
    }

}
