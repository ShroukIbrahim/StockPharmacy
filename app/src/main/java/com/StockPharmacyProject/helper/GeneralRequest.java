package com.StockPharmacyProject.helper;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;


import com.StockPharmacyProject.R;
import com.StockPharmacyProject.adapter.SpinnerAdapter;
import com.StockPharmacyProject.data.modele.Governorates.Governorates;
import com.StockPharmacyProject.data.modele.Governorates.Governorates;
import com.StockPharmacyProject.data.modele.user.User.User;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.StockPharmacyProject.helper.HelperMethod.progressDialog;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.IS_LOGN;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.PHARMACY_PHONE;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.SaveData;

public class GeneralRequest {

    public static void getSpinnerData(Activity activity,
                                      final Spinner spinner,
                                      final SpinnerAdapter adapter, final String hint,
                                      Call<Governorates> method, final View view, final int selectedId, final boolean show) {

        if (show) {
            if (progressDialog == null) {
                HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
            } else {
                if (!progressDialog.isShowing()) {
                    HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
                }
            }
        }


        method.enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                try {
                    if (show) {
                        HelperMethod.dismissProgressDialog();
                    }

                    if (response.body().getStatus() == 1) {
                        if (view != null) {
                            view.setVisibility(View.VISIBLE);
                        }
                        adapter.setData(response.body().getData().getData(), hint);

                        spinner.setAdapter(adapter);

                        spinner.setSelection(selectedId);


                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {
                if (show) {
                    HelperMethod.dismissProgressDialog();
                }

            }
        });
    }

    public static void getSpinnerData(final Activity activity, final Spinner spinner, final SpinnerAdapter adapter, final String hint
            , final Call<Governorates> method, final int selectedId1, final AdapterView.OnItemSelectedListener listener) {

        if (progressDialog == null) {
            HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
        } else {
            if (!progressDialog.isShowing()) {
                HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
            }
        }

        method.enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                try {

                    HelperMethod.dismissProgressDialog();
                    if (response.body().getStatus() == 1) {

                        adapter.setData(response.body().getData().getData(), hint);

                        spinner.setAdapter(adapter);

                        spinner.setSelection(selectedId1);

                        spinner.setOnItemSelectedListener(listener);

                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {
                HelperMethod.dismissProgressDialog();
            }
        });
    }

}
