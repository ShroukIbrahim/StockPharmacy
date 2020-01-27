package com.StockPharmacyProject.ui.fragments.pharmacist.pharmacistcycle;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.data.modele.user.User.User;
import com.StockPharmacyProject.data.reset.ApiServices;
import com.StockPharmacyProject.helper.network.InternetState;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.StockPharmacyProject.data.reset.RetrofitClient.getClient;
import static com.StockPharmacyProject.data.reset.RetrofitClientNAuth.getClientNAuth;
import static com.StockPharmacyProject.helper.HelperMethod.dismissProgressDialog;
import static com.StockPharmacyProject.helper.HelperMethod.replace;
import static com.StockPharmacyProject.helper.HelperMethod.showProgressDialog;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.PHARMACY_PASSWORD;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.PHARMACY_PHONE;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.SaveData;
import static com.StockPharmacyProject.helper.Validation.validationConfirmPassword;
import static com.StockPharmacyProject.helper.Validation.validationEmail;
import static com.StockPharmacyProject.helper.Validation.validationLength;
import static com.StockPharmacyProject.helper.Validation.validationPassword;
import static com.StockPharmacyProject.helper.Validation.validationPhone;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    @BindView(R.id.fragment_register_name)
    TextInputEditText fragmentRegisterName;
    @BindView(R.id.fragment_register_email)
    TextInputEditText fragmentRegisterEmail;
    @BindView(R.id.fragment_register_phone)
    TextInputEditText fragmentRegisterPhone;
    @BindView(R.id.fragment_register_password)
    TextInputEditText fragmentRegisterPassword;
    @BindView(R.id.fragment_register_cpassword)
    TextInputEditText fragmentRegisterCpassword;
    @BindView(R.id.fragment_register)
    Button fragmentRegister;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;
    Unbinder unbinder;


    ApiServices apiServices;
    @BindView(R.id.name_layout)
    TextInputLayout nameLayout;
    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;
    @BindView(R.id.phone_layout)
    TextInputLayout phoneLayout;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.confirm_password_layout)
    TextInputLayout confirmPasswordLayout;
    private int userType;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getClientNAuth().create(ApiServices.class);
        return view;
    }

    @OnClick(R.id.fragment_register)
    public void onClick() {
        fragmentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onValidation();
            }
        });
    }

    private void onValidation() {

        if (!validationLength(nameLayout, getString(R.string.invalid_user_name), 3)) {
            return;
        }

        if (!validationEmail(getActivity(), emailLayout)) {

            return;
        }
        if (!validationPhone(getActivity(), phoneLayout)) {
            return;
        }

        if (!validationPassword(passwordLayout, 5, getString(R.string.invalid_password))) {
            Toast.makeText(getActivity(), R.string.invalid_password, Toast.LENGTH_SHORT).show();
            return;
        }


        if (!validationConfirmPassword(getActivity(), passwordLayout, confirmPasswordLayout)) {
            return;
        }

        Register();
    }

    private void Register() {
        if (InternetState.isConnected(getActivity())) {
            //ProgressBar.setVisibility(View.VISIBLE);
            showProgressDialog(getActivity(),getResources().getString(R.string.please_wait));
            fragmentRegister.setVisibility(View.GONE);
            String name = fragmentRegisterName.getText().toString();
            String email = fragmentRegisterEmail.getText().toString();
            String phone = fragmentRegisterPhone.getText().toString();
            String password = fragmentRegisterPassword.getText().toString();
            apiServices.Register(name, email, phone, password, 1).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    try {

                        if (response.body().getStatus().equals(1)) {


                            //ProgressBar.setVisibility(View.GONE);
                            dismissProgressDialog();
                            Toast.makeText(getActivity(), "Register Successful", Toast.LENGTH_SHORT).show();
                            LoginFragment loginFragment = new LoginFragment();
                            SaveData(getActivity(), PHARMACY_PHONE, phone);
                            SaveData(getActivity(), PHARMACY_PASSWORD, password);
                            replace(loginFragment, getFragmentManager(), R.id.activity_pharmacist_cycle, null, null);


                        } else {
                            //ProgressBar.setVisibility(View.GONE);
                            dismissProgressDialog();
                            fragmentRegister.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Log.i(TAG, "Exception" + response.message() + "" + response.body().getMessageEnglish());

                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });


        }
        else {
            //ProgressBar.setVisibility(View.GONE);
            dismissProgressDialog();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_inter_net) + "", Toast.LENGTH_SHORT).show();
        }
    }

    }



