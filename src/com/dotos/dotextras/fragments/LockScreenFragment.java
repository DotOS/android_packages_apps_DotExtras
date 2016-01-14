package com.dotos.dotextras.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import cyanogenmod.providers.CMSettings;

import org.cyanogenmod.internal.util.CmLockPatternUtils;

import com.dotos.R;


public class LockScreenFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private static final String PREF_BLOCK_ON_SECURE_KEYGUARD = "block_on_secure_keyguard";
    private SwitchPreference mBlockOnSecureKeyguard;
 
    private static final int MY_USER_ID = UserHandle.myUserId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.dot_lockscreen);
        PreferenceScreen prefSet = getPreferenceScreen();
              Activity activity = getActivity();
  
            final ContentResolver resolver = getActivity().getContentResolver();
            final CmLockPatternUtils lockPatternUtils = new CmLockPatternUtils(getActivity());

            // Block QS on secure LockScreen
            mBlockOnSecureKeyguard = (SwitchPreference) findPreference(PREF_BLOCK_ON_SECURE_KEYGUARD);
            if (lockPatternUtils.isSecure(MY_USER_ID)) {
                mBlockOnSecureKeyguard.setChecked(Settings.Secure.getIntForUser(resolver,
                        Settings.Secure.STATUS_BAR_LOCKED_ON_SECURE_KEYGUARD, 1, UserHandle.USER_CURRENT) == 1);
                mBlockOnSecureKeyguard.setOnPreferenceChangeListener(this);
            } else if (mBlockOnSecureKeyguard != null) {
                prefSet.removePreference(mBlockOnSecureKeyguard);
            }

    }

    @Override
         public boolean onPreferenceChange(Preference preference, Object newValue) {
             ContentResolver resolver = getActivity().getContentResolver();
             if (preference == mBlockOnSecureKeyguard) {
                Settings.Secure.putInt(resolver,
                        Settings.Secure.STATUS_BAR_LOCKED_ON_SECURE_KEYGUARD,
                        (Boolean) newValue ? 1 : 0);
                return true;
              }
              return false;
          }
}