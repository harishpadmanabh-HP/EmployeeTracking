package com.apps.employeetrackingl.employeetracking.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.employeetrackingl.employeetracking.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndoorFragment extends Fragment {


    public IndoorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_indoor, container, false);
    }

}
