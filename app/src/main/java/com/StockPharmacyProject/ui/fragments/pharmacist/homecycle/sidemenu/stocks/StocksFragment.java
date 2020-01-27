package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.stocks;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.StockPharmacyProject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StocksFragment extends Fragment {


    public StocksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pharmacist_stocks, container, false);
    }

}
