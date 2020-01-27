package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.cart;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.adapter.MyCartAdapter;
import com.StockPharmacyProject.data.local.AppDatabase;
import com.StockPharmacyProject.data.local.CartItem;
import com.StockPharmacyProject.data.modele.AddOrder.AddOrder;
import com.StockPharmacyProject.data.modele.GetPharmacyList.PharmacyData;
import com.StockPharmacyProject.data.modele.GetPharmacyList.PharmacyList;
import com.StockPharmacyProject.data.modele.confirmOrder.ConfirmOrder;
import com.StockPharmacyProject.data.modele.confirmOrder.Item;
import com.StockPharmacyProject.data.modele.confirmOrder.Pharmacy;
import com.StockPharmacyProject.data.reset.ApiServices;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;
import com.StockPharmacyProject.ui.fragments.BaseFragment;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.MedicationsFragment;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.pharmacy.AddPharmacyInfoFragment;

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
import static com.StockPharmacyProject.helper.HelperMethod.alertDialog;
import static com.StockPharmacyProject.helper.HelperMethod.replace;


public class CartFragment extends BaseFragment {


    @BindView(R.id.cart_list)
    RecyclerView cartList;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.more)
    Button more;
    Unbinder unbinder;
    @BindView(R.id.swipe_content)
    SwipeRefreshLayout swipeContent;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;
    Spinner pharmacy;
    RelativeLayout spinnerLayout;
    RelativeLayout goPharmacyLayout;
    Button openAdd;
    private int pharmacyId;
    private Integer pharmacy_Id;
    private String pharmacy_Name;

    public MyCartAdapter cartAdapter;
    private List<CartItem> items = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private AppDatabase appDatabase;
    private ApiServices apiServices = getClient().create(ApiServices.class);
    private Integer orderItem, orderQuantities;
    private Item item = new Item();
    private List<Item> itemList = new ArrayList<>();
    private Pharmacy Pharmacy = new Pharmacy();
    private ConfirmOrder confirmOrder = new ConfirmOrder();
    private PharmacistHomeActivity pharmacistHomeActivity;
    private List<PharmacyData> pharmacyData;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pharmacist_cart, container, false);
        unbinder = ButterKnife.bind(this, root);
        setUpHomeActivity();
        getPharmacy();
        pharmacistHomeActivity = (PharmacistHomeActivity) getActivity();
//        pharmacistHomeActivity.setToolBar(View.VISIBLE, "Cart", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pharmacistHomeActivity.setToolBar(View.GONE, null, null);
//                onBack();
//
//
//            }
//        });
        appDatabase = AppDatabase.getAppDatabase(getContext());
        cartList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        cartList.setLayoutManager(linearLayoutManager);
        //items =appDatabase.getItemDAO().getItems();
        cartAdapter = new MyCartAdapter(appDatabase.getItemDAO().getItems(), getActivity());
        cartList.setAdapter(cartAdapter);

        for (int i = 0; i < appDatabase.getItemDAO().getItems().size(); i++) {
            orderItem = (appDatabase.getItemDAO().getItems().get(i).getItemId());
            orderQuantities = (appDatabase.getItemDAO().getItems().get(i).getQuantity());
            item = new Item(orderItem, orderQuantities);
            itemList.add(i, item);
            System.out.println(itemList.get(i).getItemId());
            System.out.println(itemList.get(i).getQuantity());
        }
        showAllItems();


        return root;
    }

    // get All Item In Cart
    public void showAllItems() {
        appDatabase = AppDatabase.getAppDatabase(getContext());
        if (appDatabase.getItemDAO().getItems().size() == 0) {
            cartList.setAdapter(null);
            pharmacistHomeActivity.setCountCartNum(View.GONE, 0);
        } else {
            items.addAll(appDatabase.getItemDAO().getItems());
            cartAdapter.notifyDataSetChanged();
        }
    }


    //confirm Order
    private void confirmOrder() {
        ProgressBar.setVisibility(View.VISIBLE);
        confirm.setVisibility(View.GONE);
        Pharmacy.setPharmacyId(pharmacy_Id);
        confirmOrder.setItems(itemList);
        confirmOrder.setPharmacy(Pharmacy);
        apiServices.postData(confirmOrder).enqueue(new Callback<AddOrder>() {
            @Override
            public void onResponse(Call<AddOrder> call, Response<AddOrder> response) {
                if (response.body().getStatus().equals(1)) {
                    appDatabase.getItemDAO().deleteAll();
                    pharmacistHomeActivity = (PharmacistHomeActivity) getActivity();
                    pharmacistHomeActivity.setCountCartNum(View.GONE, 0);
                    items.clear();
                    cartAdapter.notifyDataSetChanged();
                    ProgressBar.setVisibility(View.GONE);
                    confirm.setVisibility(View.VISIBLE);
                    cartList.setAdapter(null);
                    Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                } else {
                    confirm.setVisibility(View.VISIBLE);
                    ProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Please Add Item or pharmacy to complete order", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AddOrder> call, Throwable t) {
                Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();


            }
        });

    }

    //get All pharmacy show it in Spinner
    private void getPharmacy() {

        apiServices.getAllPharmacy().enqueue(new Callback<PharmacyList>() {
            @Override
            public void onResponse(Call<PharmacyList> call, Response<PharmacyList> response) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        pharmacyData = response.body().getData().getData();
                        final List<String> PharmacyName = new ArrayList<String>();
                        final List<Integer> PharmacyId = new ArrayList<Integer>();
//                        if (pharmacyData.size() == 0) {
//
//                            Toast.makeText(getActivity(), "Please Add Pharmacy to complete order", Toast.LENGTH_SHORT).show();
//                        } else {
                        PharmacyId.add(0);
                        PharmacyName.add("Select Pharmacy");
                        for (int i = 0; i < pharmacyData.size(); i++) {
                            PharmacyName.add(pharmacyData.get(i).getName());
                            PharmacyId.add(pharmacyData.get(i).getPharmacyId());
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, PharmacyName);
                        pharmacy.setAdapter(adapter);
                        pharmacy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {
                                    pharmacy_Id = PharmacyId.get(i);
                                    pharmacy_Name = PharmacyName.get(i);

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
            public void onFailure(Call<PharmacyList> call, Throwable t) {

            }
        });

    }

    //Show Spinner dialog >> choose pharmacy
    private void ShowDialog() {
        {
            AlertDialog.Builder alterDialog = new AlertDialog.Builder(getActivity());
            alterDialog.setTitle("Choose Pharmacy");
            LayoutInflater inflater = this.getLayoutInflater();
            View add_menu = inflater.inflate(R.layout.custom_pharmacy_items, null);
            pharmacy = (Spinner) add_menu.findViewById(R.id.pharmacy);
            goPharmacyLayout = (RelativeLayout) add_menu.findViewById(R.id.go_pharmacy_layout);
            openAdd = (Button) add_menu.findViewById(R.id.open_add);
            spinnerLayout = (RelativeLayout) add_menu.findViewById(R.id.spinner_layout);

            if ((pharmacyData.size() == 0)) {
                goPharmacyLayout.setVisibility(View.VISIBLE);
                spinnerLayout.setVisibility(View.GONE);
                openAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alterDialog.setCancelable(true);
                        AddPharmacyInfoFragment addPharmacyInfoFragment = new AddPharmacyInfoFragment();
                        replace(addPharmacyInfoFragment, getFragmentManager(), R.id.nav_host_fragment, null, null);

                    }
                });
            } else {
                goPharmacyLayout.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.VISIBLE);
                getPharmacy();
            }
            alterDialog.setView(add_menu);

            //set Button
            alterDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    confirmOrder();
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


    @OnClick({R.id.confirm, R.id.more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        ShowDialog();
                    }
                });
                break;
            case R.id.more:
                MedicationsFragment medicationsFragment = new MedicationsFragment();
                replace(medicationsFragment, getFragmentManager(), R.id.nav_host_fragment, null, null);
                break;
        }
    }

//    @Override
//    public void onBack() {
//        setUpHomeActivity();
//        replace(new HomeFragment(), getFragmentManager(), R.id.nav_host_fragment, null, null);
//
//    }

}


