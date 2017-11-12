package com.dotos.dotextras.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.Preference.OnPreferenceChangeListener;
import android.content.ComponentName;
import android.content.Intent;

import com.dotos.R;


public class NavbarFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.dot_navbar);

        Preference navpreference = getPreferenceManager().findPreference(getString(R.string.button_pref_title));
        navpreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

        	public boolean onPreferenceClick(Preference navpreference) {
		
		final Intent intent = new Intent(Intent.ACTION_MAIN, null);

		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		final ComponentName cn = new ComponentName("org.cyanogenmod.cmparts", "org.cyanogenmod.cmparts.input.ButtonSettings");

		intent.setComponent(cn);

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity( intent);

		return true;
        	}
        });

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }
}
