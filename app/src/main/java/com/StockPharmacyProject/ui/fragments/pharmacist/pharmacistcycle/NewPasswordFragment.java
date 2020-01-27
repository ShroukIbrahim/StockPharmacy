package com.StockPharmacyProject.ui.fragments.pharmacist.pharmacistcycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.StockPharmacyProject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPasswordFragment extends Fragment {


    @BindView(R.id.fragment_new_password_validation_code)
    TextInputEditText fragmentNewPasswordValidationCode;
    @BindView(R.id.fragment_new_password_password)
    TextInputEditText fragmentNewPasswordPassword;
    @BindView(R.id.fragment_new_password_cpassword)
    TextInputEditText fragmentNewPasswordCpassword;
    @BindView(R.id.fragment_forget_password_change_password)
    Button fragmentForgetPasswordChangePassword;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;
     Unbinder unbinder;

    public NewPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_password, container, false);
        unbinder= ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.fragment_forget_password_change_password)
    public void onClick() {
    }
}
