package com.StockPharmacyProject.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistCycleActivity;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;
import com.StockPharmacyProject.ui.activities.store.StoreCycleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.StockPharmacyProject.helper.SharedPreferencesManger.IS_LOGN;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.LoadBooleanData;

public class ChooseActivity extends AppCompatActivity {

    @BindView(R.id.start_activity_pharmacist)
    Button startActivityPharmacist;
    @BindView(R.id.start_activity_store)
    Button startActivityStore;
    public int UserType;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        unbinder=ButterKnife.bind(this);
    }

    @OnClick({R.id.start_activity_pharmacist, R.id.start_activity_store})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_activity_pharmacist:
                if (LoadBooleanData(this,IS_LOGN)) {
                    startActivity(new Intent(this, PharmacistHomeActivity.class));
                }
                else {
                    startActivity(new Intent(this, PharmacistCycleActivity.class));
                }

                break;
            case R.id.start_activity_store:
                startActivityStore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openStockCycle = new Intent(ChooseActivity.this,
                                StoreCycleActivity.class);
                        startActivity(openStockCycle);
                    }
                });


                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}