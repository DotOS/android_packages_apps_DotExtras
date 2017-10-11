package com.dotos.dotextras.drill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dotos.R;
import com.dotos.dotextras.dotsettings;

public class TheDrill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._the_drill);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(TheDrill.this, dotsettings.class));
    }
}
