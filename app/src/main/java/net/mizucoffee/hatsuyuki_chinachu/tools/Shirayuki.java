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

    public static int getBackgroundColorFromCategory(String category){
        int color = R.color.bg_etc;
        switch (category){
            case "anime":       color =  R.color.bg_anime;        break;
            case "information": color =  R.color.bg_information;  break;
            case "news":        color =  R.color.bg_news;         break;
            case "sports":      color =  R.color.bg_sports;       break;
            case "variety":     color =  R.color.bg_variety;      break;
            case "drama":       color =  R.color.bg_drama;        break;
            case "music":       color =  R.color.bg_music;        break;
            case "cinema":      color =  R.color.bg_cinema;       break;
            case "theater":     color =  R.color.bg_theater;      break;
            case "documentary": color =  R.color.bg_documentary;  break;
            case "hobby":       color =  R.color.bg_hobby;        break;
            case "welfare":     color =  R.color.bg_welfare;      break;
            case "etc":         color =  R.color.bg_etc;          break;
        }
        return color;
    }

    public static int getThemeFromCategory(String category){
        int style = R.style.etc;
        switch (category){
            case "anime":       style =  R.style.anime;        break;
            case "information": style =  R.style.information;  break;
            case "news":        style =  R.style.news;         break;
            case "sports":      style =  R.style.sports;       break;
            case "variety":     style =  R.style.variety;      break;
            case "drama":       style =  R.style.drama;        break;
            case "music":       style =  R.style.music;        break;
            case "cinema":      style =  R.style.cinema;       break;
            case "theater":     style =  R.style.theater;      break;
            case "documentary": style =  R.style.documentary;  break;
            case "hobby":       style =  R.style.hobby;        break;
            case "welfare":     style =  R.style.welfare;      break;
            case "etc":         style =  R.style.etc;          break;
        }
        return style;
    }

}
