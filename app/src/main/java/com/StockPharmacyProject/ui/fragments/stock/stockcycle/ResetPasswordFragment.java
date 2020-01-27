package com.StockPharmacyProject.ui.fragments.stock.stockcycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.StockPharmacyProject.R;
import com.StockPharmacyProject.helper.HelperMethod;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {


    @BindView(R.id.fragment_reset_password_email)
    TextInputEditText fragmentResetPasswordEmail;
    @BindView(R.id.fragment_forget_password_step1_send_code)
    Button fragmentForgetPasswordStep1SendCode;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;
    Unbinder unbinder;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reset_password, container, false);
        unbinder= ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.fragment_forget_password_step1_send_code)
    public void onClick() {
        fragmentForgetPasswordStep1SendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPasswordFragment newPasswordFragment = new NewPasswordFragment();
                HelperMethod.replace(newPasswordFragment,getFragmentManager(),R.id.activity_store_cycle,null,null);

            }
        });

    }
}
