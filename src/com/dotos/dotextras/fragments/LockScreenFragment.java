package com.dotos.dotextras.fragments;

import android.os.Bundle;
import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.hardware.fingerprint.FingerprintManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;

import com.dotos.R;


public class LockScreenFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
	private FingerprintManager mFingerprintManager;
    private SwitchPreference mFingerprintVib;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.dot_lockscreen);

        PreferenceScreen prefSet = getPreferenceScreen();

        // Fingerprint vibration
        mFingerprintManager = (FingerprintManager) getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
            mFingerprintVib = (SwitchPreference) prefSet.findPreference("fingerprint_success_vib");
            if (!mFingerprintManager.isHardwareDetected()){
                prefSet.removePreference(mFingerprintVib);
            }

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }
}