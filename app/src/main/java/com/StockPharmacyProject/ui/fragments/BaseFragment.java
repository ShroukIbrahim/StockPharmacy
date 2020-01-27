package com.StockPharmacyProject.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.StockPharmacyProject.ui.activities.BaseActivity;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;


public class BaseFragment extends Fragment {
    public BaseActivity baseActivity;
    public PharmacistHomeActivity homeCycleActivity;

    public void setUpHomeActivity() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;

        try {
            homeCycleActivity = (PharmacistHomeActivity) getActivity();
        } catch (Exception e) {

        }
    }

    public void onBack() {
        baseActivity.superBackPressed();

    }
    public void onBack1(){
        baseActivity.onBackPressed();
    }


    @Override
    public void onStart() {
        super.onStart();
        setUpHomeActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setUpHomeActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

