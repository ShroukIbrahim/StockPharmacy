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
import com.StockPharmacyProject.adapter.OrderAdapter;
import com.StockPharmacyProject.data.modele.PendingOrderList.Order;
import com.StockPharmacyProject.data.modele.PendingOrderList.OrderList;
import com.StockPharmacyProject.data.reset.ApiServices;
import com.StockPharmacyProject.helper.OnEndless;
import com.StockPharmacyProject.helper.network.InternetState;
import com.StockPharmacyProject.ui.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.StockPharmacyProject.data.reset.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends BaseFragment {


    @BindView(R.id.list_item)
    RecyclerView listItem;
    Unbinder unbinder;

    private OrderAdapter orderAdapter;
    private List<Order> orderData = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    ApiServices ApiServices = getClient().create(ApiServices.class);

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pharmacist_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        listItem.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        listItem.setLayoutManager(linearLayoutManager);
        orderAdapter = new OrderAdapter(getActivity(), orderData);
        listItem.setAdapter(orderAdapter);
        showOrderList();
        return view;
    }

    private void showOrderList() {
        if (InternetState.isConnected(getActivity())) {
        ApiServices.showOrder().enqueue(new Callback<OrderList>() {
            @Override
            public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                if (response.body().getStatus().equals(1))

                {
                    orderData.addAll(response.body().getData().getOrders());
                    orderAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<OrderList> call, Throwable t) {

            }
        });
        } else {

            Toast.makeText(getActivity(), getResources().getString(R.string.error_inter_net) + "", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }
}
