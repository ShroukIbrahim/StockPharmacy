package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.orders;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.adapter.OrderDetailsAdapter;
import com.StockPharmacyProject.data.modele.OrderDetail.Data;
import com.StockPharmacyProject.data.modele.OrderDetail.Item;
import com.StockPharmacyProject.data.modele.OrderDetail.Order;
import com.StockPharmacyProject.data.modele.OrderDetail.OrderDetail;
import com.StockPharmacyProject.data.modele.PendingOrderList.OrderList;
import com.StockPharmacyProject.data.reset.ApiServices;
import com.StockPharmacyProject.helper.network.InternetState;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;
import com.StockPharmacyProject.ui.fragments.BaseFragment;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.pharmacy.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.StockPharmacyProject.data.reset.RetrofitClient.getClient;
import static com.StockPharmacyProject.helper.HelperMethod.replace;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetilsFragment extends BaseFragment {

    int orderId;
    @BindView(R.id.list_item)
    RecyclerView listItem;
    Unbinder unbinder;

    PharmacistHomeActivity pharmacistHomeActivity;

    OrderDetailsAdapter orderDetailsAdapter;
    private LinearLayoutManager linearLayoutManager;
    ApiServices apiServices = getClient().create(ApiServices.class);
    private ArrayList<Item> items = new ArrayList<>();


    public OrderDetilsFragment(int orderId) {
        this.orderId = orderId;
    }

    public OrderDetilsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pharmacist_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        listItem.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        listItem.setLayoutManager(linearLayoutManager);
        orderDetailsAdapter = new OrderDetailsAdapter(getActivity(), items);
        listItem.setAdapter(orderDetailsAdapter);
        ShowOrder();
        setUpHomeActivity();
        PharmacistHomeActivity pharmacistHomeActivity = (PharmacistHomeActivity) getActivity();
        pharmacistHomeActivity.setToolBar(View.VISIBLE, "Order Details", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pharmacistHomeActivity.setToolBar(View.GONE, null, null);
                onBack();
            }
        });

        return view;
    }

    private void ShowOrder() {
        if (InternetState.isConnected(getActivity())) {
            apiServices.showOrderDetails(orderId).enqueue(new Callback<OrderDetail>() {
                @Override
                public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                    if (response.body().getStatus().equals(1)) {
                        items.addAll(response.body().getData().getOrder().getItems());
                        orderDetailsAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onFailure(Call<OrderDetail> call, Throwable t) {

                }
            });
        } else {

            Toast.makeText(getActivity(), getResources().getString(R.string.error_inter_net) + "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onBack() {
        //setUpHomeActivity();
        OrderFragment orderFragment = new OrderFragment();
        replace(orderFragment, getFragmentManager(), R.id.nav_host_fragment, null, null);

    }


}
