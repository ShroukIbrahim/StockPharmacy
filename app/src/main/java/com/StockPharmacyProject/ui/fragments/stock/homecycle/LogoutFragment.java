package com.StockPharmacyProject.ui.fragments.stock.homecycle;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistCycleActivity;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;
import com.StockPharmacyProject.ui.activities.store.StockHomeActivity;
import com.StockPharmacyProject.ui.activities.store.StoreCycleActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends Fragment {


    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ShowDialog();
        return null;
    }
    private void ShowDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Logout...");

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "LogOut Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), StoreCycleActivity.class));

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getActivity(), StockHomeActivity.class));
                dialog.cancel();

            }
        });

        alertDialog.show();

    }
}
