package com.StockPharmacyProject.ui.activities.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.helper.HelperMethod;
import com.StockPharmacyProject.ui.activities.ChooseActivity;
import com.StockPharmacyProject.ui.fragments.stock.stockcycle.LoginFragment;

public class StoreCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_cycle);

        LoginFragment loginFragment = new LoginFragment();
        HelperMethod.replace(loginFragment,getSupportFragmentManager(),R.id.activity_store_cycle,null,null);



    }

    @Override
    public void onBackPressed() {

        Intent backToChooseActivity = new Intent(this, ChooseActivity.class);
        startActivity(backToChooseActivity);
        super.onBackPressed();
    }
}
