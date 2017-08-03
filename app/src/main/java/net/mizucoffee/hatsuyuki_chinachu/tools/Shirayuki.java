package net.mizucoffee.hatsuyuki_chinachu.tools;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

    public static void log(String msg){
        StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
        if (steArray.length > 3) {
            StringBuilder sb = new StringBuilder();
            StackTraceElement ste = steArray[3];
            sb.append(msg)
                    .append(" | Called from ")
                    .append(ste.getMethodName())
                    .append("(")
                    .append(ste.getFileName())
                    .append(":")
                    .append(ste.getLineNumber())   // 行番号取得
                    .append(")");
            Log.i("SHIRAYUKI",sb.toString());
        }else{
            Log.i("SHIRAYUKI",msg);
        }
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

    public static String getResolutionFromVideoSize(String text){
        switch (text){
            case "576p (WSVGA)": return "&s=1024×576";
            case "720p (HD) (Recommended)": return "&s=1280x720";
            case "1080p (FHD)": return "&s=1920x1080";
        }
        return "";
    }

    public static String getVideoBitrate(String text){
        switch (text){
            case "256kbps": return "&b%3Av=256k";
            case "512kbps": return "&b%3Av=512k";
            case "1Mbps (Recommended)": return "&b%3Av=1M";
            case "2Mbps": return "&b%3Av=2M";
            case "3Mbps": return "&b%3Av=3M";
        }
        return "";
    }

    public static String getAudioBitrate(String text){
        switch (text){
            case "64kbps": return "&b%3Aa=64k";
            case "128kbps (Recommended)": return "&b%3Aa=128k";
            case "192bps": return "&b%3Aa=192k";
        }
        return "";
    }
}
