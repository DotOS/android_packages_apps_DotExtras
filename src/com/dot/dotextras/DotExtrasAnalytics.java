package com.dot.dotextras;

import android.app.Application;
 
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

import com.dot.dotextras.AnalyticsTrackers;
 
public class DotExtrasAnalytics extends Application {
    public static final String TAG = MyApplication.class
            .getSimpleName();
 
    private static DotExtrasAnalytics mInstance;
 
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
 
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
    }
 
    public static synchronized DotExtrasAnalytics getInstance() {
        return mInstance;
    }
 
    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }
 
    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();
 
        // Set screen name.
        t.setScreenName(screenName);
 
        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());
 
        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

}