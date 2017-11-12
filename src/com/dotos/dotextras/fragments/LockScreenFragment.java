package com.dotos.dotextras.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.hardware.fingerprint.FingerprintManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.preference.PreferenceScreen;
import android.content.ContentResolver;
import android.provider.Settings;
import android.preference.PreferenceCategory;

import com.dotos.R;


public class LockScreenFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
	private static final String FP_SUCCESS_VIBRATION = "fingerprint_success_vib";
    private static final String FP_UNLOCK_KEYSTORE = "fp_unlock_keystore";

	private FingerprintManager mFingerprintManager;
    private SwitchPreference mFingerprintVib;
    private SwitchPreference mFpKeystore;
    private PreferenceCategory mMiscCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.dot_lockscreen);

        PreferenceScreen prefSet = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();

        mMiscCategory = (PreferenceCategory) prefSet.findPreference("lockscreen_misc_category");

        // Fingerprint vibration
        mFingerprintManager = (FingerprintManager) getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
            mFingerprintVib = (SwitchPreference) prefSet.findPreference(FP_SUCCESS_VIBRATION);
            if (!mFingerprintManager.isHardwareDetected()){
                mMiscCategory.removePreference(mFingerprintVib);
            }
        // Fingerprint unlock keystore
            mFpKeystore = (SwitchPreference) findPreference(FP_UNLOCK_KEYSTORE);
            if (!mFingerprintManager.isHardwareDetected()){
                mMiscCategory.removePreference(mFpKeystore);
            } else {
                mFpKeystore.setChecked((Settings.System.getInt(resolver,
                    Settings.System.FP_UNLOCK_KEYSTORE, 0) == 1));
            }

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }
}