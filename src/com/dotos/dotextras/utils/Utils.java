package com.dotos.dotextras.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;

import com.dotos.R;
import com.dotos.dotextras.dotsettings;

public class Utils {
    private static int sTheme;
    public final static int SELECT_THEME = 0;
    public final static int DEFAULT = 1;
    public final static int PIXEL = 2;
    public final static int RED = 3;
    public final static int DEFAULT_BLACK = 4;
    public final static int PIXEL_BLACK = 5;
    public final static int DPURPLE_BLACK = 6;


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
            case SELECT_THEME:
                break;
            case DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case PIXEL:
                activity.setTheme(R.style.AppTheme_Blue);
                break;
            case RED:
                activity.setTheme(R.style.AppTheme_Red);
                break;
            case DEFAULT_BLACK:
                activity.setTheme(R.style.AppTheme_Teal_Black);
                break;
            case PIXEL_BLACK:
                activity.setTheme(R.style.AppTheme_Blue_Black);
                break;
            case RED_BLACK:
                activity.setTheme(R.style.AppTheme_Red_Black);
                break;
        }
    }


}