package com.StockPharmacyProject.ui.activities;


import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;
import com.StockPharmacyProject.ui.fragments.BaseFragment;

import java.util.List;

public class BaseActivity extends AppCompatActivity {


    public BaseFragment baseFragment;


    public void superBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() < 0) {
            getSupportFragmentManager().popBackStack();
            baseFragment.homeCycleActivity.setToolBar(View.GONE,null,null);
        } else {
            super.onBackPressed();
//            baseFragment.homeCycleActivity.setToolBar(View.GONE,null,null);

        }
    }
}
