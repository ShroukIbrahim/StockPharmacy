package com.StockPharmacyProject.ui.fragments.pharmacist.pharmacistcycle;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.data.modele.user.User.User;
import com.StockPharmacyProject.data.reset.ApiServices;
import com.StockPharmacyProject.helper.HelperMethod;
import com.StockPharmacyProject.helper.network.InternetState;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.StockPharmacyProject.data.reset.RetrofitClientNAuth.getClientNAuth;
import static com.StockPharmacyProject.helper.HelperMethod.dismissProgressDialog;
import static com.StockPharmacyProject.helper.HelperMethod.showProgressDialog;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.IS_LOGN;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.LoadData;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.PHARMACY_API_TOKEN;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.PHARMACY_PASSWORD;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.PHARMACY_PHONE;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.SaveData;
import static com.StockPharmacyProject.helper.Validation.validationPassword;
import static com.StockPharmacyProject.helper.Validation.validationPhone;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


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
    Unbinder unbinder;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;

    int userType;

    ApiServices ApiServices;

    private static final String TAG = "AndroidClarified";
    @BindView(R.id.ProgressBar)
    android.widget.ProgressBar ProgressBar;
    @BindView(R.id.phone_layout)
    TextInputLayout phoneLayout;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;


    private GoogleSignInClient googleSignInClient;
    private String msg;
    List<TextInputEditText> textInputLayoutList = new ArrayList<>();
    private int lock_icon;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = getClientNAuth().create(ApiServices.class);
        //RegisterToken();
        dataUserShrPreferences();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        return view;
    }

    private void dataUserShrPreferences() {
        fragmentLoginEmail.setText(LoadData(getActivity(), PHARMACY_PHONE));
        fragmentLoginPassword.setText(LoadData(getActivity(), PHARMACY_PASSWORD));
    }

    @OnClick({R.id.fragment_login_forget_password, R.id.fragment_login_login,
            R.id.fragment_login_open_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_login_forget_password:
                fragmentLoginForgetPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                        HelperMethod.replace(resetPasswordFragment, getFragmentManager(), R.id.activity_pharmacist_cycle, null, null);


                    }
                });
                break;
            case R.id.fragment_login_login:
                onValidData();
                break;
            case R.id.fragment_login_open_register:
                fragmentLoginOpenRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RegisterFragment registerFragment = new RegisterFragment();
                        HelperMethod.replace(registerFragment, getFragmentManager(), R.id.activity_pharmacist_cycle, null, null);
                    }
                });
                break;

            //                showPassword.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showPassword(showPassword,fragmentLoginPassword);
//                    }
//                });
        }
    }

    private void onValidData() {
        if (!validationPhone(getActivity(), phoneLayout)) {
            return;
        }

        if (!validationPassword(passwordLayout, 5, getString(R.string.invalid_password))) {
            Toast.makeText(getActivity(), R.string.invalid_password, Toast.LENGTH_SHORT).show();
            return;
        }

        Login();
    }

    private void Login() {
        if (InternetState.isConnected(getActivity())) {
            //ProgressBar.setVisibility(View.VISIBLE);
            showProgressDialog(getActivity(), getResources().getString(R.string.please_wait));
            fragmentLoginLogin.setVisibility(View.GONE);
            String phone = fragmentLoginEmail.getText().toString();
            String password = fragmentLoginPassword.getText().toString();
            ApiServices.Login(phone, password).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    try {

                        if (response.body().getStatus().equals(1) &&
                                response.body().getData().getUser().getUserType().equals(1)) {
                            SaveData(getActivity(), PHARMACY_API_TOKEN, response.body().getData().getUser().getUserToken());
                            SaveData(getActivity(), PHARMACY_PHONE, phone);
                            SaveData(getActivity(), PHARMACY_PASSWORD, password);
                            SaveData(getActivity(), IS_LOGN, true);
                            // ProgressBar.setVisibility(View.GONE);
                            dismissProgressDialog();
                            Intent openHome = new Intent(getActivity(), PharmacistHomeActivity.class);
                            startActivity(openHome);
                            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            //ProgressBar.setVisibility(View.GONE);
                            dismissProgressDialog();
                            fragmentLoginLogin.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Please enter your pharmacist correct data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                        Log.i(TAG, "Exception" + response.message() + "" + response.body().getMessageEnglish());

                    }
                }


                @Override
                public void onFailure(Call<User> call, Throwable t) {


                }
            });

        } else {
            //ProgressBar.setVisibility(View.GONE);
            dismissProgressDialog();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_inter_net) + "", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.sign_in_button)
    public void onClick() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });
    }


//    public void RegisterToken() {
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
//                            return;
//                        }
//
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
//
//                        // Log and toast
//                        msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//                        Log.w(TAG, "Token>>" + msg);
//                    }
//                });
//        ApiServices.RegisterToken(msg, LoadData(getActivity(), PHARMACY_API_TOKEN),
//                "android").enqueue(new Callback<NotificationsToken>() {
//            @Override
//            public void onResponse(Call<NotificationsToken> call, Response<NotificationsToken> response) {
//
////                try {
////                    if (response.body().getStatus().equals(1)) {
////                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
////
////                    } else {
////                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
////                    }
////                } catch (Exception e) {
////                    e.getMessage();
////                }
//            }
//
//            @Override
//            public void onFailure(Call<NotificationsToken> call, Throwable t) {
//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }

}
