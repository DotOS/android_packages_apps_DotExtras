package com.dotos.dotextras;

import android.content.Context;
import android.os.AsyncTask;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dotos.dotextras.fragments.AboutExtrasFragment;
import com.dotos.dotextras.fragments.ButtonFragment;
import com.dotos.dotextras.fragments.DisplayFragment;
import com.dotos.dotextras.fragments.LockScreenFragment;
import com.dotos.dotextras.fragments.MiscFragment;
import com.dotos.dotextras.fragments.PowerMenuFragment;
import com.dotos.dotextras.fragments.RecentsFragment;
import com.dotos.dotextras.fragments.StatusbarFragment;

import eu.chainfire.libsuperuser.Shell;

public class dotsettings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
		
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dotsettings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sysreboot) {
            SuShell.run("pkill -TERM -f com.android.systemui");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

if (id == R.id.nav_dot_main) {
            CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.app_bar_dot);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.content_dotsettings, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
        } else if (id == R.id.nav_dot_statusbar) {
            CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.app_bar_dot);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new StatusbarFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_buttons) {
            CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.app_bar_dot);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new ButtonFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_display) {
            CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.app_bar_dot);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new DisplayFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_lockscreen) {
            CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.app_bar_dot);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new LockScreenFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_powermenu) {
            CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.app_bar_dot);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new PowerMenuFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_misc) {
            CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.app_bar_dot);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new MiscFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_recents) {
            CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.app_bar_dot);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new RecentsFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_about_extras) {
            CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.app_bar_dot);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new AboutExtrasFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_misc) {
            CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.app_bar_dot);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new MiscFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_navbar) {
            CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.app_bar_dot);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new MiscFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_about_extras) {
            CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.app_bar_dot);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_about, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_about_layout, new MiscFragment())
                    .commitAllowingStateLoss();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
