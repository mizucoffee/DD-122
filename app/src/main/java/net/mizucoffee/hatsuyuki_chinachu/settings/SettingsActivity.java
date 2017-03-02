package net.mizucoffee.hatsuyuki_chinachu.settings;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private FragmentName mFragmentName = FragmentName.GENERAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new GeneralSettingsFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if (mFragmentName == FragmentName.REALTIME || mFragmentName == FragmentName.DOWNLOAD) {
            getFragmentManager().beginTransaction().replace(android.R.id.content, new GeneralSettingsFragment()).commit();
            mFragmentName = FragmentName.GENERAL;
        } else
            super.onBackPressed();
    }

    public void replaceRealTimeFragment(){
        getFragmentManager().beginTransaction().replace(android.R.id.content, new RealTimeSettingsFragment()).commit();
        mFragmentName = FragmentName.REALTIME;
    }

    public void replaceDownloadFragment(){
        getFragmentManager().beginTransaction().replace(android.R.id.content, new DownloadSettingsFragment()).commit();
        mFragmentName = FragmentName.DOWNLOAD;
    }


}
