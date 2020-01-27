package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsconditionsFragment extends Fragment {


    public TermsconditionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_terms_conditions, container, false);
        return view;
    }

}
