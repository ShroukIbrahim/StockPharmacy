package com.StockPharmacyProject.ui.fragments.stock.stockcycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.StockPharmacyProject.data.modele.user.User.User;
import com.StockPharmacyProject.data.reset.ApiServices;
import com.google.android.material.textfield.TextInputEditText;
import com.StockPharmacyProject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.StockPharmacyProject.data.reset.RetrofitClient.getClient;
import static com.StockPharmacyProject.data.reset.RetrofitClientNAuth.getClientNAuth;
import static com.StockPharmacyProject.helper.HelperMethod.replace;

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
    Button fragmentRegisterClientRegister;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;
     Unbinder unbinder;
   ApiServices ApiServices = getClientNAuth().create(ApiServices.class);

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_register, container, false);
        unbinder= ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.fragment_register)
    public void onClick() {
        fragmentRegisterClientRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();

            }
        });
    }
    private void Register() {
        String name = fragmentRegisterName.getText().toString();
        String email = fragmentRegisterEmail.getText().toString();
        String phone = fragmentRegisterPhone.getText().toString();
        String password = fragmentRegisterPassword.getText().toString();
        ApiServices.Register(name,email,phone, password,2).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getStatus() == 1) {

                    Toast.makeText(getActivity(), "Register Successful", Toast.LENGTH_SHORT).show();
                   LoginFragment loginFragment = new LoginFragment();
                    replace(loginFragment, getFragmentManager(), R.id.activity_store_cycle, null, null);


                } else
                    Toast.makeText(getActivity(), "Register Failed", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }
}
