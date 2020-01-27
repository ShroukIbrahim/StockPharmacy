package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.pharmacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.adapter.MedicationsAdapter;
import com.StockPharmacyProject.adapter.PharmacyListAdapter;
import com.StockPharmacyProject.data.modele.GetPharmacyList.PharmacyData;
import com.StockPharmacyProject.data.modele.GetPharmacyList.PharmacyList;

import com.StockPharmacyProject.data.reset.ApiServices;
import com.StockPharmacyProject.helper.HelperMethod;
import com.StockPharmacyProject.helper.OnEndless;
import com.StockPharmacyProject.helper.network.InternetState;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;
import com.StockPharmacyProject.ui.fragments.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import static com.StockPharmacyProject.helper.SharedPreferencesManger.LoadData;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.PHARMACY_API_TOKEN;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.PHARMACY_EMAIL;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.PHARMACY_NAME;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.PHARMACY_PHONE;


public class ProfileFragment extends BaseFragment {


    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.pharmacy_list)
    RecyclerView pharmacyList;
    @BindView(R.id.add_pharmacy)
    FloatingActionButton addPharmacy;
    public static boolean fromProfile=false;
    Unbinder unbinder;

    private PharmacyListAdapter pharmacyListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<PharmacyData> pharmacyData = new ArrayList<>();
    ApiServices apiServices = getClient().create(ApiServices.class);
    private OnEndless onEndless;
    private Integer maxPage;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this,root);
        getProfileData();
        pharmacyList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        pharmacyList.setLayoutManager(linearLayoutManager);
        pharmacyListAdapter = new PharmacyListAdapter(getActivity(), pharmacyData);
        pharmacyList.setAdapter(pharmacyListAdapter);
        getAllPharmacies();
        return root;
    }

    private void getAllPharmacies() {
        if (InternetState.isConnected(getActivity())) {
        apiServices.getAllPharmacy().enqueue(new Callback<PharmacyList>() {
            @Override
            public void onResponse(Call<PharmacyList> call, Response<PharmacyList> response) {
                if (response.body().getStatus().equals(1)) {
                    pharmacyData.addAll(response.body().getData().getData());
                    pharmacyListAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<PharmacyList> call, Throwable t) {

            }
        });
        } else {

            Toast.makeText(getActivity(), getResources().getString(R.string.error_inter_net) + "", Toast.LENGTH_SHORT).show();
        }

    }

    private void getProfileData() {
        name.setText(LoadData(getActivity(), PHARMACY_NAME));
        phone.setText(LoadData(getActivity(), PHARMACY_PHONE));
        email.setText(LoadData(getActivity(), PHARMACY_EMAIL));
    }

    @OnClick(R.id.add_pharmacy)
    public void onClick() {
        addPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPharmacyInfoFragment addPharmacyInfoFragment = new AddPharmacyInfoFragment();
                HelperMethod.replace(addPharmacyInfoFragment,
                        getFragmentManager(),R.id.nav_host_fragment,null,null);
            }
        });
    }
}