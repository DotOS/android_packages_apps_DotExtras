package com.dotos.dotextras.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.content.res.Resources;
import android.preference.PreferenceScreen;
import android.content.ContentResolver;
import android.provider.Settings;

import com.dotos.R;


public class DisplayFragment extends PreferenceFragment {
	private int mDeviceHardwareKeys;
	private static final String KEY_ANBI = "anbi_enabled";


	private SwitchPreference mAnbiPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.dot_display);

        PreferenceScreen prefScreen = getPreferenceScreen();
        Resources res = getActivity().getResources();
        ContentResolver resolver = getActivity().getContentResolver();

        mDeviceHardwareKeys = res.getInteger(
                 com.android.internal.R.integer.config_deviceHardwareKeys);

        /* Accidental navigation button interaction */
        mAnbiPreference = (SwitchPreference) findPreference(KEY_ANBI);
        /*if (mAnbiPreference != null) {
            if (mDeviceHardwareKeys != 0) {
                mAnbiPreference.setChecked((Settings.System.getInt(resolver,
                    Settings.System.KEY_ANBI, 0) == 1));
            } else {
                prefScreen.removePreference(mAnbiPreference);
            }
        }*/

        if (mDeviceHardwareKeys == 0)
            prefScreen.removePreference(mAnbiPreference);

    }
}