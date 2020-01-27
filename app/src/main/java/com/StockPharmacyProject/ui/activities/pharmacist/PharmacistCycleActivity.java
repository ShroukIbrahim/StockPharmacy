package com.StockPharmacyProject.ui.activities.pharmacist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.helper.HelperMethod;
import com.StockPharmacyProject.ui.activities.ChooseActivity;
import com.StockPharmacyProject.ui.fragments.pharmacist.pharmacistcycle.LoginFragment;

import static com.StockPharmacyProject.helper.HelperMethod.replace;

public class PharmacistCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_cycle);

        LoginFragment loginFragment = new LoginFragment();
        replace(loginFragment,getSupportFragmentManager(),R.id.activity_pharmacist_cycle,null,null);


    }

    @Override
    public void onBackPressed() {

        Intent backToChooseActivity = new Intent(this, ChooseActivity.class);
        startActivity(backToChooseActivity);
        super.onBackPressed();
    }
}
