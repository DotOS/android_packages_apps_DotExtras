/*
 * Copyright (C) 2014-2016 The Dirty Unicorns Project
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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dot.dotextras.fragments.System;
import com.dot.dotextras.fragments.Lockscreen;
import com.dot.dotextras.fragments.PowerMenu;
import com.dot.dotextras.fragments.QuickSettings;
import com.dot.dotextras.fragments.Recents;
import com.dot.dotextras.fragments.Statusbar;
import com.dot.dotextras.fragments.Navigation;
import com.dot.dotextras.views.HeadCards;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.dashboard.SummaryLoader;
import com.dot.dotextras.dotstats.Constants;
import com.dot.dotextras.dotstats.RequestInterface;
import com.dot.dotextras.dotstats.models.ServerRequest;
import com.dot.dotextras.dotstats.models.ServerResponse;
import com.dot.dotextras.dotstats.models.StatsData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import android.text.TextUtils;

public class DotExtrasFragment extends SettingsPreferenceFragment {

    ViewGroup mContainer;
	private SharedPreferences pref;
    private CompositeDisposable mCompositeDisposable;

    static Bundle mSavedState;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainer = container;
        View view = inflater.inflate(R.layout.dotextras, container, false);
		headCardsListener(view);
		goBackLayout(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
		mCompositeDisposable = new CompositeDisposable();
        pref=getActivity().getSharedPreferences("dotStatsPrefs", Context.MODE_PRIVATE);
        if (!pref.getString(Constants.LAST_BUILD_DATE, "null").equals(SystemProperties.get(Constants.KEY_BUILD_DATE)) || pref.getBoolean(Constants.IS_FIRST_LAUNCH, true)) {
            pushStats();
        }
    }

private void pushStats() {
        //Anonymous Stats

        if (!TextUtils.isEmpty(SystemProperties.get(Constants.KEY_DEVICE))) { //Push only if installed ROM is DotOS
            RequestInterface requestInterface = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RequestInterface.class);

            StatsData stats = new StatsData();
            stats.setDevice(stats.getDevice());
            stats.setModel(stats.getModel());
            stats.setVersion(stats.getVersion());
            stats.setBuildType(stats.getBuildType());
            stats.setCountryCode(stats.getCountryCode(getActivity()));
            stats.setBuildDate(stats.getBuildDate());
            ServerRequest request = new ServerRequest();
            request.setOperation(Constants.PUSH_OPERATION);
            request.setStats(stats);
            mCompositeDisposable.add(requestInterface.operation(request)
                    .observeOn(AndroidSchedulers.mainThread(),false,100)
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Log.d(Constants.TAG, "This ain't dotOS, Back Off!");
        }

    }

    private void handleResponse(ServerResponse resp) {

        if (resp.getResult().equals(Constants.SUCCESS)) {

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(Constants.IS_FIRST_LAUNCH, false);
            editor.putString(Constants.LAST_BUILD_DATE, SystemProperties.get(Constants.KEY_BUILD_DATE));
            editor.apply();
            Log.d(Constants.TAG, "push successful");

        } else {
            Log.d(Constants.TAG, resp.getMessage());
        }

    }

    private void handleError(Throwable error) {

        Log.d(Constants.TAG, error.toString());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.DOTEXTRAS;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return false;
        }
    }
	
	public void goBackLayout(final View view) {
        RelativeLayout go_back_layout = view.findViewById(R.id.go_back);
        go_back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout frag_layout = view.findViewById(R.id.fragment_head_container);
                frag_layout.setVisibility(View.GONE);
                LinearLayout head_layout = view.findViewById(R.id.features_select);
                head_layout.setVisibility(View.VISIBLE);
            }
        });
    }
	
	public void headCardsListener(final View view) {
		Fragment frags[] = new Fragment[] {
            new Statusbar(),
            new Navigation(),
            new QuickSettings(),
            new Recents(),
            new Lockscreen(),
			new System()
        };
        HeadCards statusbar = view.findViewById(R.id.statusbar_card);
        statusbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(view, frags[0]);
            }
        });
        HeadCards navbar = view.findViewById(R.id.navigation_bar_card);
        navbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(view, frags[1]);
            }
        });
        HeadCards qs = view.findViewById(R.id.quick_settings_card);
        qs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(view, frags[2]);
            }
        });
        HeadCards recents = view.findViewById(R.id.recents_card);
        recents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(view, frags[3]);
            }
        });
        HeadCards lockscreen = view.findViewById(R.id.lockscreen_card);
        lockscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(view, frags[4]);
            }
        });
        HeadCards system = view.findViewById(R.id.system_card);
        system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(view, frags[5]);
            }
        });
    }
	
	public void showFragment(View view, Fragment frag) {
        LinearLayout frag_layout = view.findViewById(R.id.fragment_head_container);
        if (frag_layout.getVisibility() != View.VISIBLE) {
            frag_layout.setVisibility(View.VISIBLE);
        }
        LinearLayout head_layout = view.findViewById(R.id.features_select);
		head_layout.setVisibility(View.GONE);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, frag)
                .commitAllowingStateLoss();
    }
    
    private static class SummaryProvider implements SummaryLoader.SummaryProvider {

        private final Context mContext;
        private final SummaryLoader mSummaryLoader;

        public SummaryProvider(Context context, SummaryLoader summaryLoader) {
            mContext = context;
            mSummaryLoader = summaryLoader;
        }

        @Override
        public void setListening(boolean listening) {
            if (listening) {
                mSummaryLoader.setSummary(this, mContext.getString(R.string.build_tweaks_summary_title));
            }
        }
    }

    public static final SummaryLoader.SummaryProviderFactory SUMMARY_PROVIDER_FACTORY
            = new SummaryLoader.SummaryProviderFactory() {
        @Override
        public SummaryLoader.SummaryProvider createSummaryProvider(Activity activity,
                                                                   SummaryLoader summaryLoader) {
            return new SummaryProvider(activity, summaryLoader);
        }
    };

}