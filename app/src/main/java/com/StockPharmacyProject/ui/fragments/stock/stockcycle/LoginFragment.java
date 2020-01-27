package com.StockPharmacyProject.ui.fragments.stock.stockcycle;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.data.modele.user.User.User;
import com.StockPharmacyProject.data.reset.ApiServices;
import com.StockPharmacyProject.helper.HelperMethod;
import com.StockPharmacyProject.ui.activities.store.StockHomeActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.StockPharmacyProject.data.reset.RetrofitClient.getClient;
import static com.StockPharmacyProject.data.reset.RetrofitClientNAuth.getClientNAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {


    @BindView(R.id.fragment_login_email)
    TextInputEditText fragmentLoginEmail;
    @BindView(R.id.fragment_login_password)
    TextInputEditText fragmentLoginPassword;
    @BindView(R.id.fragment_login_forget_password)
    TextView fragmentLoginForgetPassword;
    @BindView(R.id.fragment_login_login)
    Button fragmentLoginLogin;
    @BindView(R.id.fragment_login_open_register)
    Button fragmentLoginOpenRegister;
    //    @BindView(R.id.LoginFragment_Progress_Bar)
//    ProgressBar LoginFragmentProgressBar;
    Unbinder unbinder;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    @BindView(R.id.ProgressBar)
    android.widget.ProgressBar ProgressBar;

    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient.OnConnectionFailedListener Context;
    ApiServices ApiServices = getClientNAuth().create(ApiServices.class);

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), Context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        return view;
    }

    @OnClick({R.id.fragment_login_forget_password, R.id.fragment_login_login, R.id.fragment_login_open_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_login_forget_password:
                fragmentLoginForgetPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                        HelperMethod.replace(resetPasswordFragment, getFragmentManager(), R.id.activity_store_cycle, null, null);


                    }
                });
                break;
            case R.id.fragment_login_login:
                Login();
                break;
            case R.id.fragment_login_open_register:
                fragmentLoginOpenRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RegisterFragment registerFragment = new RegisterFragment();
                        HelperMethod.replace(registerFragment, getFragmentManager(), R.id.activity_store_cycle, null, null);
                    }
                });
                break;
        }
    }

    private void Login() {
        ProgressBar.setVisibility(View.VISIBLE);
        String phone = fragmentLoginEmail.getText().toString();
        String password = fragmentLoginPassword.getText().toString();
        ApiServices.Login(phone, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getStatus() == 1 &&
                        response.body().getData().getUser().getUserType().equals(1)) {
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                    ProgressBar.setVisibility(View.GONE);
                    Intent openHome = new Intent(getActivity(), StockHomeActivity.class);
                    startActivity(openHome);

                } else
                    ProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Please enter your pharmacist correct data", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Please enter your pharmacist correct data", Toast.LENGTH_SHORT).show();


            }
        });


    }

    @OnClick(R.id.sign_in_button)
    public void onClick() {

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            gotoProfile();
        } else {
            Toast.makeText(getActivity(), "Sign in cancel", Toast.LENGTH_LONG).show();
        }
    }

    private void gotoProfile() {
        Intent intent = new Intent(getActivity(), StockHomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}




