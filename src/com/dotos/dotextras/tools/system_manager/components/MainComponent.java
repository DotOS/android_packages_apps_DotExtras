package com.dotos.dotextras.tools.system_manager.components;

import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dotos.dotextras.R;
import com.dotos.dotextras.utils.SystemProperties;

import static com.dotos.dotextras.tools.system_manager.ManagerMain.getAvailableExternalMemorySize;
import static com.dotos.dotextras.tools.system_manager.ManagerMain.getAvailableInternalMemorySize;
import static com.dotos.dotextras.tools.system_manager.ManagerMain.getTotalExternalMemorySize;
import static com.dotos.dotextras.tools.system_manager.ManagerMain.getTotalInternalMemorySize;

public class MainComponent extends Fragment implements OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_main_component, container, false);
        //TextView sd_memory = v.findViewById(R.id.sd_free);
        //sd_memory.setText("Free: " +getAvailableExternalMemorySize() + "\n" + "Total: " + getTotalExternalMemorySize());
        ImageButton show_more = v.findViewById(R.id.expand_more_info);
        show_more.setOnClickListener(this);
        ImageButton hide_more = v.findViewById(R.id.expand_less_info);
        hide_more.setOnClickListener(this);
        TextView android_ver = v.findViewById(R.id.text_androidver);
        android_ver.setText("Android Version : " + SystemProperties.get("ro.build.version.release"));
        TextView sdk_ver = v.findViewById(R.id.text_sdk_ver);
        sdk_ver.setText("SDK Version : " + SystemProperties.get("ro.build.version.sdk"));
        TextView chip = v.findViewById(R.id.text_chip);
        chip.setText("Chipset : " + SystemProperties.get("ro.product.board"));
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.expand_more_info:
                getContext();
                RelativeLayout memory_layout3 = getView().findViewById(R.id.expand_info);
                memory_layout3.setVisibility(View.VISIBLE);
                ImageButton internal_more3 = getView().findViewById(R.id.expand_more_info);
                internal_more3.setVisibility(View.GONE);
                ImageButton inthidee = getView().findViewById(R.id.expand_less_info);
                inthidee.setVisibility(View.VISIBLE);
                break;
            case R.id.expand_less_info:
                getContext();
                RelativeLayout memory_layout2 = getView().findViewById(R.id.expand_info);
                memory_layout2.setVisibility(View.GONE);
                ImageButton internal_more2 = getView().findViewById(R.id.expand_more_info);
                internal_more2.setVisibility(View.VISIBLE);
                ImageButton inthide = getView().findViewById(R.id.expand_less_info);
                inthide.setVisibility(View.GONE);
                break;
        }
    }
}
