package com.dotos.dotextras.tools.res_changer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dotos.R;

public class ResChanger_base extends Fragment {

    public ResChanger_base() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tools_res_changer_base, container, false);
    }

}
