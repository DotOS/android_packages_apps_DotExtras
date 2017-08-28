package com.dotos.dotextras.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.dotos.R;


public class MiscFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.dot_misc);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }
}