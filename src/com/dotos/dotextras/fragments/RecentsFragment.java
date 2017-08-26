package com.dotos.dotextras.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.preference.ListPreference;
import android.preference.ListPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.dotos.dotextras.R;


public class RecentsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.pref_view_layout, new RecentsFragment())
                .commit();
    }

    public static class RecentsFragment extends PreferenceFragment
            implements OnPreferenceChangeListener {

        public RecentsFragment() {
        }

        private static final String RECENTS_CLEAR_ALL_LOCATION = "recents_clear_all_location";
        private static final String IMMERSIVE_RECENTS = "immersive_recents";

        private SwitchPreference mRecentsClearAll;
        private ListPreference mRecentsClearAllLocation;
        private ListPreference mImmersiveRecents;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.dot_recents);

            PreferenceScreen prefSet = getPreferenceScreen();
            Activity activity = getActivity();
            ContentResolver resolver = getActivity().getContentResolver();

	    // Clear all location
            mRecentsClearAllLocation = (ListPreference) prefSet.findPreference(RECENTS_CLEAR_ALL_LOCATION);
            int location = Settings.System.getIntForUser(resolver,
                    Settings.System.RECENTS_CLEAR_ALL_LOCATION, 3, UserHandle.USER_CURRENT);
            mRecentsClearAllLocation.setValue(String.valueOf(location));
            mRecentsClearAllLocation.setSummary(mRecentsClearAllLocation.getEntry());
            mRecentsClearAllLocation.setOnPreferenceChangeListener(this);

	    // Immersive recents
            mImmersiveRecents = (ListPreference) prefSet.findPreference(IMMERSIVE_RECENTS);
            mImmersiveRecents.setValue(String.valueOf(Settings.System.getInt(
                    resolver, Settings.System.IMMERSIVE_RECENTS, 0)));
            mImmersiveRecents.setSummary(mImmersiveRecents.getEntry());
            mImmersiveRecents.setOnPreferenceChangeListener(this);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            ContentResolver resolver = getActivity().getContentResolver();
            if (preference == mRecentsClearAllLocation) {
                int location = Integer.parseInt((String) newValue);
                int index = mRecentsClearAllLocation.findIndexOfValue((String) newValue);
                Settings.System.putIntForUser(getActivity().getContentResolver(),
                        Settings.System.RECENTS_CLEAR_ALL_LOCATION, location, UserHandle.USER_CURRENT);
                mRecentsClearAllLocation.setSummary(mRecentsClearAllLocation.getEntries()[index]);
                return true;
            } else if (preference == mImmersiveRecents) {
                Settings.System.putInt(resolver, Settings.System.IMMERSIVE_RECENTS,
                        Integer.parseInt((String) newValue));
                mImmersiveRecents.setValue(String.valueOf(newValue));
                mImmersiveRecents.setSummary(mImmersiveRecents.getEntry());
                return true;
            }
            return false;
        }
    }
}
