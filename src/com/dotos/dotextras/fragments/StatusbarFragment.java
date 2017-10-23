package com.dotos.dotextras.fragments;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.ListPreference;
import android.text.format.DateFormat;
import android.view.View;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import com.dotos.dotextras.preferences.CMSystemSettingListPreference;

import com.dotos.R;


public class StatusbarFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
     private static final String STATUS_BAR_CLOCK_STYLE = "status_bar_clock";
    private static final String STATUS_BAR_AM_PM = "status_bar_am_pm";
    private static final String STATUS_BAR_BATTERY_STYLE = "status_bar_battery_style";
    private static final String STATUS_BAR_SHOW_BATTERY_PERCENT = "status_bar_show_battery_percent";

    private static final int STATUS_BAR_BATTERY_STYLE_HIDDEN = 4;
    private static final int STATUS_BAR_BATTERY_STYLE_TEXT = 6;

    private CMSystemSettingListPreference mStatusBarClock;
    private CMSystemSettingListPreference mStatusBarAmPm;
    private CMSystemSettingListPreference mStatusBarBattery;
    private CMSystemSettingListPreference mStatusBarBatteryShowPercent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.dot_statusbar);

	mStatusBarClock = (CMSystemSettingListPreference) findPreference(STATUS_BAR_CLOCK_STYLE);
        mStatusBarBatteryShowPercent =
                (CMSystemSettingListPreference) findPreference(STATUS_BAR_SHOW_BATTERY_PERCENT);

        mStatusBarAmPm = (CMSystemSettingListPreference) findPreference(STATUS_BAR_AM_PM);
        if (DateFormat.is24HourFormat(getActivity())) {
            mStatusBarAmPm.setEnabled(false);
            mStatusBarAmPm.setSummary(R.string.status_bar_am_pm_info);
        }

        mStatusBarBattery =
                (CMSystemSettingListPreference) findPreference(STATUS_BAR_BATTERY_STYLE);
        mStatusBarBattery.setOnPreferenceChangeListener(this);
        enableStatusBarBatteryDependents(mStatusBarBattery.getIntValue(2));
    }

    @Override
    public void onResume() {
        super.onResume();

        // Adjust status bar preferences for RTL
        if (getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            mStatusBarClock.setEntries(R.array.status_bar_clock_position_entries_rtl);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        int value = Integer.parseInt((String) newValue);
        if (preference == mStatusBarBattery) {
            enableStatusBarBatteryDependents(value);
        } 
        return true;
    }

    private void enableStatusBarBatteryDependents(int batteryIconStyle) {
        mStatusBarBatteryShowPercent.setEnabled(
                batteryIconStyle != STATUS_BAR_BATTERY_STYLE_HIDDEN
                && batteryIconStyle != STATUS_BAR_BATTERY_STYLE_TEXT);
    }
}
