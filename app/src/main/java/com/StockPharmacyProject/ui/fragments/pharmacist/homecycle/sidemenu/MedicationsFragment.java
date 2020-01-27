package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.adapter.MedicationsAdapter;
import com.StockPharmacyProject.data.modele.MedicationList.MedicationList;
import com.StockPharmacyProject.data.modele.MedicationList.Medication_Data;
import com.StockPharmacyProject.data.reset.ApiServices;
import com.StockPharmacyProject.helper.OnEndless;
import com.StockPharmacyProject.helper.network.InternetState;
import com.StockPharmacyProject.ui.activities.BaseActivity;
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
import static com.StockPharmacyProject.helper.HelperMethod.dismissProgressDialogC;
import static com.StockPharmacyProject.helper.HelperMethod.progressDialog;
import static com.StockPharmacyProject.helper.HelperMethod.showProgressDialog;
import static com.StockPharmacyProject.helper.RecycleTool.setRecycleTool;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationsFragment extends Fragment {


    @BindView(R.id.show_all_medications)
    RecyclerView showAllMedications;
    Unbinder unbinder;
    @BindView(R.id.key_word_search)
    TextInputEditText keyWordSearch;
    @BindView(R.id.searchMedication)
    ImageView searchMedication;
    @BindView(R.id.error_image)
    ImageView errorImage;
    @BindView(R.id.error_title)
    TextView errorTitle;
    @BindView(R.id.error_action)
    TextView errorAction;
    @BindView(R.id.error_sub_view)
    LinearLayout errorSubView;


    private MedicationsAdapter medicationsAdapter;
    private List<Medication_Data> medicationData = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    ApiServices ApiServices;
    String keyword;
    private OnEndless onEndless;
    private Integer maxPage = 0;
    private BaseActivity baseActivity;
    private boolean checkSearch = false;


    public MedicationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medications, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = getClient().create(ApiServices.class);
        showAllMedications.setHasFixedSize(true);
        onEndless();


        return view;
    }


    private void showAllMedications(int page) {
        if (InternetState.isConnected(getActivity())) {
            ApiServices.getAllItems(page)
                    .enqueue(new Callback<MedicationList>() {
                        @Override
                        public void onResponse(Call<MedicationList> call, Response<MedicationList> response) {
                            if (response.body().getStatus().equals(1)) {
                                maxPage = response.body().getData().getData().getLastPage();
                                medicationData.addAll(response.body().getData().getData().getData().getData());
                                medicationsAdapter.notifyDataSetChanged();


                            }
                        }

                        @Override
                        public void onFailure(Call<MedicationList> call, Throwable t) {

                        }
                    });
        } else {

            Toast.makeText(getActivity(), getResources().getString(R.string.error_inter_net) + "", Toast.LENGTH_SHORT).show();
        }
    }

    private void searchMedications(int page) {
        checkSearch = true;
        // showProgressDialog(getActivity(),getString(R.string.please_wait));
        ApiServices.searchOfAllItems(page, keyWordSearch.getText().toString())
                .enqueue(new Callback<MedicationList>() {
                    @Override
                    public void onResponse(Call<MedicationList> call, Response<MedicationList> response) {
                        if (response.body().getStatus().equals(1)) {
                            maxPage = response.body().getData().getData().getLastPage();
                            medicationData.clear();
                            medicationsAdapter.notifyDataSetChanged();
                            if (response.body().getData().getData().getTotal() != 0) {
                                medicationData.addAll(response.body().getData().getData().getData().getData());
                                errorSubView.setVisibility(View.GONE);
                                // dismissProgressDialog();
                                medicationsAdapter.notifyDataSetChanged();
                            } else {
                                setError(getString(R.string.no_item) + " " + keyWordSearch.getText().toString());
                                //dismissProgressDialog();
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<MedicationList> call, Throwable t) {

                    }
                });
    }

    private void onEndless() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        showAllMedications.setLayoutManager(linearLayoutManager);
        onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndless.previous_page = current_page;
                        if (checkSearch) {
                            searchMedications(current_page);

                        } else {
                            showAllMedications(current_page);
                        }
                    } else {
                        onEndless.current_page = onEndless.previous_page;
                    }
                } else {
                    onEndless.current_page = onEndless.previous_page;
                }
            }
        };


        showAllMedications.addOnScrollListener(onEndless);
        medicationsAdapter = new MedicationsAdapter(getActivity(), medicationData);
        showAllMedications.setAdapter(medicationsAdapter);
        if (checkSearch) {
            searchMedications(1);
        } else {
            showAllMedications(1);
        }

    }


    private void setError(String errorTitleTxt) {
        if (medicationData.size() == 0) {
            View.OnClickListener action = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEndless.previousTotal = 0;
                    onEndless.current_page = 1;
                    onEndless.previous_page = 1;
                    medicationData = new ArrayList<>();
                    errorSubView.setVisibility(View.GONE);
                    keyWordSearch.getText().clear();
                    medicationsAdapter = new MedicationsAdapter(getActivity(), medicationData);
                    showAllMedications.setAdapter(medicationsAdapter);
                    if (checkSearch) {
                        searchMedications(1);

                    } else {
                        showAllMedications(1);
                    }


                }
            };
            setRecycleTool(baseActivity, errorSubView, errorImage, errorTitle, errorAction, R.drawable.ic_search_black_24dp
                    , errorTitleTxt, getString(R.string.reload), action);
        }
    }

    @OnClick({R.id.searchMedication})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchMedication:
                searchMedication.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (keyWordSearch.getText().toString().isEmpty() && checkSearch) {
                            checkSearch = false;
                            showAllMedications(1);

                        } else {
                            searchMedications(1);

                        }


                    }
                });
                break;
        }
    }
}
