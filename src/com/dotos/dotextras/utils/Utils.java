package com.dotos.dotextras.utils;

import android.app.Activity;
import android.content.Intent;

import com.dotos.R;

public class Utils {
    private static int sTheme;

    public final static int DEFAULT = 0;
    public final static int PIXEL = 1;
    public final static int LGREEN = 2;
    public final static int BLACK = 3;


    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case PIXEL:
                activity.setTheme(R.style.AppTheme_Blue);
                break;
            case LGREEN:
                activity.setTheme(R.style.AppTheme_LGreen);
                break;
            case BLACK:
                activity.setTheme(R.style.AppTheme_Black);
                break;
        }
    }
}
