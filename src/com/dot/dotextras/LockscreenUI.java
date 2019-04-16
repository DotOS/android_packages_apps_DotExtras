/*
 * Copyright (C) 2018 Project dotOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dot.dotextras;

import android.app.ActivityManagerNative;
import android.content.Context;
import android.content.ContentResolver;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManagerGlobal;
import android.view.IWindowManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Locale;
import android.text.TextUtils;
import android.view.View;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.dot.dotextras.Utils;

import android.hardware.fingerprint.FingerprintManager;
import com.dot.dotextras.preference.SystemSettingSwitchPreference;
import com.dot.dotextras.preference.SystemSettingListPreference;
import com.android.internal.util.dotos.DOTUtils;

public class LockscreenUI extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String KEY_FACE_AUTO_UNLOCK = "face_auto_unlock";
    private static final String KEY_FACE_UNLOCK_PACKAGE = "com.android.facelock";
    private static final String FP_CAT = "lockscreen_ui_general_category";
    private static final String FINGERPRINT_VIB = "fingerprint_success_vib";
    private static final String FP_UNLOCK_KEYSTORE = "fp_unlock_keystore";
    private static final String WEATHER_LS_CAT = "weather_lockscreen_key";

    private static final String LOCK_CLOCK_FONTS = "lock_clock_fonts";
	private static final String LOCK_DATE_FONTS = "lock_date_fonts";
    private static final String LOCKSCREEN_CLOCK_SELECTION  = "lockscreen_clock_selection";

    private SwitchPreference mFaceUnlock;
    private SystemSettingSwitchPreference mFingerprintVib;
    private SystemSettingSwitchPreference mFpKeystore;
    private FingerprintManager mFingerprintManager;
	
    ListPreference mLockClockFonts;
	ListPreference mLockDateFonts;
    SystemSettingListPreference mLockClockStyle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.lockscreen_ui);

        final ContentResolver resolver = getActivity().getContentResolver();
        final PreferenceScreen prefSet = getPreferenceScreen();

        final PreferenceCategory weatherCategory = (PreferenceCategory) prefSet
                .findPreference(WEATHER_LS_CAT);

        if (!DOTUtils.isPackageInstalled(getActivity(), "org.pixelexperience.weather.client")) {
            prefSet.removePreference(weatherCategory);
        }

        mFaceUnlock = (SwitchPreference) findPreference(KEY_FACE_AUTO_UNLOCK);
        if (!Utils.isPackageInstalled(getActivity(), KEY_FACE_UNLOCK_PACKAGE)){
            prefSet.removePreference(mFaceUnlock);
        } else {
            mFaceUnlock.setChecked((Settings.Secure.getInt(getContext().getContentResolver(),
                    Settings.Secure.FACE_AUTO_UNLOCK, 0) == 1));
            mFaceUnlock.setOnPreferenceChangeListener(this);
        }

        PreferenceCategory fingerprintCategory = (PreferenceCategory) findPreference(FP_CAT);

        mFingerprintManager = (FingerprintManager) getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
        mFingerprintVib = (SystemSettingSwitchPreference) findPreference(FINGERPRINT_VIB);
        mFpKeystore = (SystemSettingSwitchPreference) findPreference(FP_UNLOCK_KEYSTORE);

        if (mFingerprintManager != null && mFingerprintManager.isHardwareDetected()){
        mFingerprintVib.setChecked((Settings.System.getInt(getContentResolver(),
                Settings.System.FINGERPRINT_SUCCESS_VIB, 1) == 1));
        mFingerprintVib.setOnPreferenceChangeListener(this);
        } else {
        fingerprintCategory.removePreference(mFingerprintVib);
        fingerprintCategory.removePreference(mFpKeystore);
        }

        // Lockscren Clock Fonts
        mLockClockFonts = (ListPreference) findPreference(LOCK_CLOCK_FONTS);
        mLockClockFonts.setValue(String.valueOf(Settings.System.getInt(
                getContentResolver(), Settings.System.LOCK_CLOCK_FONTS, 0)));
        mLockClockFonts.setSummary(mLockClockFonts.getEntry());
        mLockClockFonts.setOnPreferenceChangeListener(this);
		
        mLockClockStyle = (SystemSettingListPreference) findPreference(LOCKSCREEN_CLOCK_SELECTION);
        mLockClockStyle.setValue(String.valueOf(Settings.System.getInt(
                getContentResolver(), Settings.System.LOCKSCREEN_CLOCK_SELECTION, 0)));
        mLockClockStyle.setOnPreferenceChangeListener(this);
		
		// Lockscren Date Fonts
        mLockDateFonts = (ListPreference) findPreference(LOCK_DATE_FONTS);
        mLockDateFonts.setValue(String.valueOf(Settings.System.getInt(
                getContentResolver(), Settings.System.LOCK_DATE_FONTS, 0)));
        mLockDateFonts.setSummary(mLockDateFonts.getEntry());
        mLockDateFonts.setOnPreferenceChangeListener(this);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.DOTEXTRAS;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mFaceUnlock) {
            boolean value = (Boolean) objValue;
            Settings.Secure.putInt(getActivity().getContentResolver(),
                    Settings.Secure.FACE_AUTO_UNLOCK, value ? 1 : 0);
            return true;
			} else if (preference == mLockClockFonts) {
            Settings.System.putInt(getContentResolver(), Settings.System.LOCK_CLOCK_FONTS,
                    Integer.valueOf((String) objValue));
            mLockClockFonts.setValue(String.valueOf(objValue));
            mLockClockFonts.setSummary(mLockClockFonts.getEntry());
            return true;
		} else if (preference == mLockDateFonts) {
            Settings.System.putInt(getContentResolver(), Settings.System.LOCK_DATE_FONTS,
                    Integer.valueOf((String) objValue));
            mLockDateFonts.setValue(String.valueOf(objValue));
            mLockDateFonts.setSummary(mLockDateFonts.getEntry());
            return true;
        } else if (preference == mFingerprintVib) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.FINGERPRINT_SUCCESS_VIB, value ? 1 : 0);
            return true;
        } else if (preference == mLockClockStyle) {
            int val = Integer.valueOf((String) objValue);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCKSCREEN_CLOCK_SELECTION, val);
            if (val == 15) {
                Settings.System.putInt(getContentResolver(), Settings.System.LOCKSCREEN_INFO, 0);
            } else {
                Settings.System.putInt(getContentResolver(), Settings.System.LOCKSCREEN_INFO, 1);
            }
            return true;
        }
        return false;
    }
}