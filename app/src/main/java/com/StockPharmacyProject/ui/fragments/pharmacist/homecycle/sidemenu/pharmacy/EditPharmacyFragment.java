package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.pharmacy;


import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.adapter.SpinnerAdapter;
import com.StockPharmacyProject.data.modele.GetPharmacyList.PharmacyList;
import com.StockPharmacyProject.data.modele.Governorates.Governorates;
import com.StockPharmacyProject.data.modele.Governorates.GovernoratesData;
import com.StockPharmacyProject.data.modele.PharmacyInfo.PharmacyInfo;
import com.StockPharmacyProject.data.reset.ApiServices;
import com.StockPharmacyProject.helper.network.InternetState;
import com.StockPharmacyProject.ui.activities.MapsActivity;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;
import com.StockPharmacyProject.ui.fragments.BaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
import static com.StockPharmacyProject.helper.HelperMethod.getAddress;
import static com.StockPharmacyProject.helper.HelperMethod.replace;
import static com.StockPharmacyProject.helper.HelperMethod.showProgressDialog;
import static com.StockPharmacyProject.ui.activities.MapsActivity.MyLocation;
import static com.StockPharmacyProject.ui.activities.MapsActivity.myLatitude;
import static com.StockPharmacyProject.ui.activities.MapsActivity.myLongitude;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditPharmacyFragment extends BaseFragment {
    int pharmacyId;
    @BindView(R.id.pharmacy_name)
    TextInputEditText pharmacyName;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;
    @BindView(R.id.location_image)
    ImageView locationImage;
    Unbinder unbinder;
    ApiServices apiServices = getClient().create(ApiServices.class);
    SupportMapFragment mapFragment;
    @BindView(R.id.pharmacy_location)
    TextInputEditText pharmacyLocation;
    @BindView(R.id.Governorate_layoyt)
    TextView GovernorateLayoyt;
    @BindView(R.id.city_layout)
    TextView cityLayout;
    private double Latitude, Longitudes;
    private String Address;
    private GoogleMap mMap;
    private SpinnerAdapter governorateAdapter, cityAdapter;
    private int governorateSelectedId = 0, citiesSelectedId = 0;
    private AdapterView.OnItemSelectedListener listener;
    PharmacistHomeActivity pharmacistHomeActivity;
    private Integer id;
    private int city_id;
    private String city_txt;
    private Integer governor_Id;
    private String governor_txt;
    private int pharmacy_id;

    final List<String> GovernorTxt = new ArrayList<String>();
    final List<Integer> GovernorId = new ArrayList<Integer>();

    List<String> CityTxt = new ArrayList<String>();
    final List<Integer> CityId = new ArrayList<Integer>();
    private int cityId;
    private int goverorId;
    private Spinner governorate;
    private Spinner city_spinner;
    private RelativeLayout ph_layout;


    public EditPharmacyFragment() {
        // Required empty public constructor
    }

    public EditPharmacyFragment(int pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pharmacits_edit_pharmacy, container, false);
        unbinder = ButterKnife.bind(this, view);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        getPharmacyInfo();

        //setUpHomeActivity();
        pharmacistHomeActivity = (PharmacistHomeActivity) getActivity();
        pharmacistHomeActivity.setToolBar(View.VISIBLE, "Edit Pharmacy", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pharmacistHomeActivity.setToolBar(View.GONE, null, null);

                onBack();
            }
        });


        return view;
    }


    private void getPharmacyInfo() {
        if (InternetState.isConnected(getActivity())) {
           // showProgressDialog(getActivity(), getResources().getString(R.string.please_wait));
            apiServices.getPharmacyInfo(pharmacyId).enqueue(new Callback<PharmacyInfo>() {
                @Override
                public void onResponse(Call<PharmacyInfo> call, Response<PharmacyInfo> response) {
                    if (response.body().getStatus().equals(1)) {
                        pharmacyName.setText(response.body().getData().getPharmacy().getName());
                        GovernorateLayoyt.setText(response.body().getData().getPharmacy().getGovernorate().getValue());
                        cityLayout.setText(response.body().getData().getPharmacy().getCity().getValue());
                        city_id = response.body().getData().getPharmacy().getCity().getKey();
                        governor_Id = response.body().getData().getPharmacy().getGovernorate().getKey();
                        pharmacyLocation.setText(response.body().getData().getPharmacy().getStreet());
                        Latitude = response.body().getData().getPharmacy().getLatitude();
                        Longitudes = response.body().getData().getPharmacy().getLongitudes();

                        mapFragment.onResume(); // needed to get the map to display immediately

                        try {
                            MapsInitializer.initialize(getActivity().getApplicationContext());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                mMap = googleMap;
                                // Add a marker in Sydney and move the camera
                                LatLng sydney = new LatLng(Latitude, Longitudes);
                                Address = getAddress(getActivity(), Latitude, Longitudes);
                                //Toast.makeText(getActivity(), Address + "", Toast.LENGTH_SHORT).show();
                                mMap.addMarker(new MarkerOptions().position(sydney).title(Address));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                mMap.setMinZoomPreference(10);
                                mMap.getMaxZoomLevel();

                            }
                        });


                    }

                }

                @Override
                public void onFailure(Call<PharmacyInfo> call, Throwable t) {
                }
            });
        } else {

            Toast.makeText(getActivity(), getResources().getString(R.string.error_inter_net) + "", Toast.LENGTH_SHORT).show();
        }

    }
//    private void setSpinner() {
//
//        governorateAdapter = new SpinnerAdapter(getActivity());
//        cityAdapter = new SpinnerAdapter(getActivity());
//        listener = new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    getSpinnerData(getActivity(), pharmacyCity,
//                            cityAdapter, getString(R.string.select_city)
//                            , apiServices.getAllCites(governorateAdapter.selectedId),
//                            cityLayout, citiesSelectedId, true);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        };
//
//        getSpinnerData(getActivity(), pharmacyGovernorate,
//                governorateAdapter, getString(R.string.select_government),
//                apiServices.getAllGovernor(), governorateSelectedId, listener);
//    }

    private void editPharmacy() {
        if (InternetState.isConnected(getActivity())) {
            add.setVisibility(View.GONE);
            showProgressDialog(getActivity(), getResources().getString(R.string.please_wait));
            String name = pharmacyName.getText().toString();
            apiServices.updatePharmacy(name, city_id, governor_Id, Latitude, Longitudes, pharmacyId, Address).enqueue(new Callback<PharmacyList>() {
                @Override
                public void onResponse(Call<PharmacyList> call, Response<PharmacyList> response) {
                    if (response.body().getStatus().equals(1)) {
                        dismissProgressDialog();
                        add.setVisibility(View.VISIBLE);
                        pharmacistHomeActivity.setToolBar(View.GONE,null,null);
                        replace(homeCycleActivity.profileFragment, getFragmentManager(), R.id.nav_host_fragment, null, null);
                        Toast.makeText(getActivity(), response.body().getMessageEnglish() + "", Toast.LENGTH_SHORT).show();
                    } else {
                        dismissProgressDialog();
                        Toast.makeText(getActivity(), response.body().getMessageEnglish() + "", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<PharmacyList> call, Throwable t) {

                }
            });
        } else {
            dismissProgressDialog();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_inter_net) + "", Toast.LENGTH_SHORT).show();
        }
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
                            GovernorTxt.add("Goverorate");


                            for (int i = 0; i < governoratesData.size(); i++) {
                                GovernorTxt.add(governoratesData.get(i).getValue());
                                GovernorId.add(governoratesData.get(i).getKey());
                            }
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, GovernorTxt);
                            governorate.setAdapter(adapter);
                            governorate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i != 0) {
                                        governor_Id = GovernorId.get(i);
                                        governor_txt = GovernorTxt.get(i);
                                        getCity(governor_Id);
                                        ph_layout.setVisibility(View.VISIBLE);
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
                        CityTxt.add("City");
                        for (int i = 0; i < governoratesData.size(); i++) {
                            CityTxt.add(governoratesData.get(i).getValue());
                            CityId.add(governoratesData.get(i).getKey());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, CityTxt);
                        city_spinner.setAdapter(adapter);

                        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {

                                    city_id = CityId.get(i);
                                    city_txt = CityTxt.get(i);
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

    private void ShowGovDialog() {
        {
            AlertDialog.Builder alterDialog = new AlertDialog.Builder(getActivity());
            alterDialog.setTitle("Choose Governor");
            LayoutInflater inflater = this.getLayoutInflater();
            View add_menu = inflater.inflate(R.layout.custom_pharmacy_items, null);
            governorate = (Spinner) add_menu.findViewById(R.id.pharmacy);
            city_spinner = (Spinner) add_menu.findViewById(R.id.city_spinner);
            ph_layout = (RelativeLayout) add_menu.findViewById(R.id.ph_layout);
            alterDialog.setView(add_menu);
            getGovernor();


            //set Button
            alterDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    GovernorateLayoyt.setText(governor_txt + "");
                    cityLayout.setText(city_txt + "");
                    dialogInterface.dismiss();

                }
            });
            alterDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
            alterDialog.show();
        }


    }

    @OnClick({R.id.location_image, R.id.add, R.id.Governorate_layoyt, R.id.city_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_image:
                locationImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MapsActivity.class);
                        intent.putExtra("lat", Latitude);
                        intent.putExtra("lng", Longitudes);
                        startActivity(intent);

                    }
                });
                break;
            case R.id.add:
                editPharmacy();
                break;
            case R.id.Governorate_layoyt:
                ShowGovDialog();
                break;
            case R.id.city_layout:
                break;
        }
    }


    @Override
    public void onBack() {
//        setUpHomeActivity();
        getActivity().getSupportFragmentManager().popBackStack();
        replace(homeCycleActivity.profileFragment, getFragmentManager(), R.id.nav_host_fragment, null, null);

    }

    @Override
    public void onResume() {
        super.onResume();
        pharmacyLocation.setText(MyLocation);
        Latitude = myLatitude;
        Longitudes = myLongitude;
        Address = MyLocation;
//        Toast.makeText(getActivity(),latitude+ "", Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(),longitude+ "", Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(),Address+ "", Toast.LENGTH_SHORT).show();

        mapFragment.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(Latitude, Longitudes);
                Address = getAddress(getActivity(), Latitude, Longitudes);
                // Toast.makeText(getActivity(), Address + "", Toast.LENGTH_SHORT).show();
                mMap.addMarker(new MarkerOptions().position(sydney).title(Address));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.setMinZoomPreference(10);
                mMap.getMaxZoomLevel();

            }
        });


    }

}
