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

}
