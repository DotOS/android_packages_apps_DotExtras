package com.dotos.dotextras.utils;

import android.app.Activity;


public class StaticReload {
    public static void refreshActionBarMenu(Activity activity)
    {
        activity.invalidateOptionsMenu();
    }
}
