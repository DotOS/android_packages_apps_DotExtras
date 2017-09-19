package com.dotos.dotextras.tools.flasher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dotos.R;
import com.dotos.dotextras.dotsettings;

public class Tools_Flasher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tools_flasher_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToExtras();
            }
        });
    }
    @Override
    public void onBackPressed() {
        backToExtras();
    }

    public void backToExtras() {
        finish();
        startActivity(new Intent(getApplicationContext(), dotsettings.class));
    }

}
