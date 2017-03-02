package net.mizucoffee.hatsuyuki_chinachu.settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import net.mizucoffee.hatsuyuki_chinachu.R;

public class GeneralSettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.general_preferences);

        findPreference("realtime_encode").setOnPreferenceClickListener((Preference pref) -> {
            ((SettingsActivity)getActivity()).replaceRealTimeFragment();
            return true;
        });

        findPreference("download_encode").setOnPreferenceClickListener((Preference pref) -> {
            ((SettingsActivity)getActivity()).replaceDownloadFragment();
            return true;
        });
    }


}