package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistCycleActivity;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;

import static com.StockPharmacyProject.helper.SharedPreferencesManger.clean;


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

                clean(getActivity());
                startActivity(new Intent(getActivity(), PharmacistCycleActivity.class));
                Toast.makeText(getActivity(), "LogOut Successful", Toast.LENGTH_SHORT).show();


            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getActivity(), PharmacistHomeActivity.class));
                dialog.cancel();

            }
        });

        alertDialog.show();

    }
}
