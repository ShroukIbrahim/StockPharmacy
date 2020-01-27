package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.cart;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;
import com.StockPharmacyProject.ui.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmOrderFragment extends BaseFragment {


    public ConfirmOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_confirm_order, container, false);
        PharmacistHomeActivity pharmacistHomeActivity= (PharmacistHomeActivity) getActivity();
        pharmacistHomeActivity.setToolBar(View.VISIBLE, "Complete Order", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pharmacistHomeActivity.setToolBar(View.GONE,null,null);
                onBack();

            }
        });
        return view;
    }

}
