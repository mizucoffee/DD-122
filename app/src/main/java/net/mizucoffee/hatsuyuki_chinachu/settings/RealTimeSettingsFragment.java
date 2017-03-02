package net.mizucoffee.hatsuyuki_chinachu.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import net.mizucoffee.hatsuyuki_chinachu.R;

public class RealTimeSettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.realtime_encode_preferences);
    }


}