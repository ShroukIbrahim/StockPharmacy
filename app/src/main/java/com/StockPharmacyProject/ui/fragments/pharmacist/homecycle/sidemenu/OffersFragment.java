package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;


public class OffersFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pharmacist_offers, container, false);

        return root;
    }
}