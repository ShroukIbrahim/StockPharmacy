package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.pharmacy;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.data.modele.GetPharmacyList.PharmacyList;
import com.StockPharmacyProject.data.modele.Governorates.Governorates;
import com.StockPharmacyProject.data.modele.Governorates.GovernoratesData;
import com.StockPharmacyProject.data.reset.ApiServices;
import com.StockPharmacyProject.helper.LocationTrack;
import com.StockPharmacyProject.helper.network.InternetState;
import com.StockPharmacyProject.ui.activities.MapsActivity;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;
import com.StockPharmacyProject.ui.fragments.BaseFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.StockPharmacyProject.data.reset.RetrofitClient.getClient;
import static com.StockPharmacyProject.helper.HelperMethod.dismissProgressDialog;
import static com.StockPharmacyProject.helper.HelperMethod.replace;
import static com.StockPharmacyProject.helper.HelperMethod.showProgressDialog;
import static com.StockPharmacyProject.helper.Validation.ValidLatLng;
import static com.StockPharmacyProject.helper.Validation.validationConfirmPassword;
import static com.StockPharmacyProject.helper.Validation.validationEmail;
import static com.StockPharmacyProject.helper.Validation.validationLength;
import static com.StockPharmacyProject.helper.Validation.validationPassword;
import static com.StockPharmacyProject.helper.Validation.validationPhone;
import static com.StockPharmacyProject.ui.activities.MapsActivity.MyLocation;
import static com.StockPharmacyProject.ui.activities.MapsActivity.myLatitude;
import static com.StockPharmacyProject.ui.activities.MapsActivity.myLongitude;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPharmacyInfoFragment extends BaseFragment {


    @BindView(R.id.pharmacy_name)
    TextInputEditText pharmacyName;
    @BindView(R.id.pharmacy_Governorate)
    Spinner pharmacyGovernorate;
    @BindView(R.id.pharmacy_city)
    Spinner pharmacyCity;
    @BindView(R.id.pharmacy_location)
    TextInputEditText pharmacyLocation;
    @BindView(R.id.location_image)
    ImageView locationImage;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;
    Unbinder unbinder;
    double latitude;
    double longitude;
    String Address;
    @BindView(R.id.city)
    RelativeLayout city;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    ApiServices apiServices = getClient().create(ApiServices.class);
    private Integer id;
    private int city_id;
    private String city_txt;
    private Integer governor_Id;
    private String governor_txt;
    private int pharmacy_id;
    private PharmacistHomeActivity pharmacistHomeActivity;


    public AddPharmacyInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_farmacy_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUpHomeActivity();
        pharmacistHomeActivity = (PharmacistHomeActivity) getActivity();
        pharmacistHomeActivity.setToolBar(View.VISIBLE, "Add New Pharmacy", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pharmacistHomeActivity.setToolBar(View.GONE, null, null);
                onBack();
            }
        });
        getGovernor();
        return view;
    }


    private void getGovernor() {
        if (InternetState.isConnected(getActivity())) {

            apiServices.getAllGovernor().enqueue(new Callback<Governorates>() {
                @Override
                public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                    try {

                        if (response.body().getStatus().equals(1)) {
                            final List<GovernoratesData> governoratesData = response.body().getData().getData();
                            final List<String> GovernorTxt = new ArrayList<String>();
                            final List<Integer> GovernorId = new ArrayList<Integer>();
                            GovernorId.add(id);
                            GovernorTxt.add(getString(R.string.select_government));


                            for (int i = 0; i < governoratesData.size(); i++) {
                                GovernorTxt.add(governoratesData.get(i).getValue());
                                GovernorId.add(governoratesData.get(i).getKey());
                            }
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, GovernorTxt);
                            pharmacyGovernorate.setAdapter(adapter);
                            pharmacyGovernorate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i != 0) {
                                        governor_Id = GovernorId.get(i);
                                        governor_txt = GovernorTxt.get(i);
                                        getCity(governor_Id);
                                        //city.setVisibility(View.VISIBLE);
                                        // Toast.makeText(getActivity(), city_id + "", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        }


                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<Governorates> call, Throwable t) {

                }
            });
        } else {

            Toast.makeText(getActivity(), getResources().getString(R.string.error_inter_net) + "", Toast.LENGTH_SHORT).show();
        }

    }

    private void getCity(final int id) {
        apiServices.getAllCites(id).enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        final List<GovernoratesData> governoratesData = response.body().getData().getData();
                        List<String> CityTxt = new ArrayList<String>();
                        final List<Integer> CityId = new ArrayList<Integer>();
                        CityId.add(id);
                        CityTxt.add(getString(R.string.select_city));
                        for (int i = 0; i < governoratesData.size(); i++) {
                            CityTxt.add(governoratesData.get(i).getValue());
                            CityId.add(governoratesData.get(i).getKey());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, CityTxt);
                        pharmacyCity.setAdapter(adapter);

                        pharmacyCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {

                                    city_id = CityId.get(i);
                                    // Toast.makeText(getActivity(), city_id + "", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {

            }
        });

    }

    @OnClick({R.id.location_image, R.id.add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_image:
                locationImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MapsActivity.class);
                        startActivity(intent);


                    }

                });
                break;
            case R.id.add:
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       onValidation();
                    }
                });

                break;
        }
    }



    private void addPharmacy() {
        if (InternetState.isConnected(getActivity())) {
            add.setVisibility(View.GONE);
            showProgressDialog(getActivity(), getResources().getString(R.string.please_wait));
            String name = pharmacyName.getText().toString();
            apiServices.addPharmacy(name, city_id, governor_Id, myLatitude, myLongitude, MyLocation).enqueue(new Callback<PharmacyList>() {
                @Override
                public void onResponse(Call<PharmacyList> call, Response<PharmacyList> response) {
                    if (response.body().getStatus().equals(1)) {
                        Toast.makeText(getActivity(), "Add Pharmacy Successful", Toast.LENGTH_SHORT).show();
                        dismissProgressDialog();
                        replace(homeCycleActivity.profileFragment, getFragmentManager(), R.id.nav_host_fragment, null, null);
                        add.setVisibility(View.VISIBLE);
                        myLatitude=0.0;
                        myLongitude=0.0;
                    } else {
                        dismissProgressDialog();
                        Toast.makeText(getActivity(), "Please Add Valid Data", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<PharmacyList> call, Throwable t) {
                    dismissProgressDialog();

                }
            });
        } else {
            dismissProgressDialog();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_inter_net) + "", Toast.LENGTH_SHORT).show();
        }
    }


    private void onValidation() {

        if (!validationLength(pharmacyName, getString(R.string.invalid_user_name), 3)) {
            return;
        }


        if (pharmacyGovernorate.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), getString(R.string.select_government), Toast.LENGTH_SHORT).show();
            return;
        }

        if (pharmacyCity.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), getString(R.string.select_government), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!validationLength(pharmacyLocation, getString(R.string.invalid_user_name), 3)) {
            return;
        }
        if (!ValidLatLng(myLatitude, myLongitude)) {
            Toast.makeText(getActivity(), "This application doesn't cover this place", Toast.LENGTH_SHORT).show();
        }

        addPharmacy();
    }


    @Override
    public void onBack() {
        replace(homeCycleActivity.profileFragment, getFragmentManager(), R.id.nav_host_fragment, null, null);
        // super.onBack();

    }

    @Override
    public void onResume() {
        super.onResume();
        pharmacyLocation.setText(MyLocation);
        latitude = myLatitude;
        longitude = myLongitude;
        Address = MyLocation;
        //Toast.makeText(getActivity(), myLatitude+""+myLongitude+""+MyLocation, Toast.LENGTH_SHORT).show();

//        Toast.makeText(getActivity(),latitude+ "", Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(),longitude+ "", Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(),Address+ "", Toast.LENGTH_SHORT).show();


    }
}


