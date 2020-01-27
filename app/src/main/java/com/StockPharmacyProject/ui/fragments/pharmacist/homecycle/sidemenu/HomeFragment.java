package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.adapter.OrderAdapter;
import com.StockPharmacyProject.data.modele.PendingOrderList.Order;
import com.StockPharmacyProject.data.modele.PendingOrderList.OrderList;
import com.StockPharmacyProject.data.reset.ApiServices;
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

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.StockPharmacyProject.data.reset.RetrofitClient.getClient;


public class HomeFragment extends BaseFragment {


    @BindView(R.id.list_order)
    RecyclerView listOrder;
    Unbinder unbinder;

    private OrderAdapter orderAdapter;
    private List<Order> orderData = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    ApiServices apiServices;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pharmacist_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getClient().create(ApiServices.class);
        listOrder.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        listOrder.setLayoutManager(linearLayoutManager);
        orderAdapter = new OrderAdapter(getActivity(), orderData);
        listOrder.setAdapter(orderAdapter);
        showOrderList();
        return view;
    }

    private void showOrderList() {
        if (InternetState.isConnected(getActivity())) {
            apiServices.showOrder().enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                    try {

                        if (response.body().getStatus().equals(1)) {
                            orderData.addAll(response.body().getData().getOrders());
                            orderAdapter.notifyDataSetChanged();

                        }
                    } catch (Exception e) {
                        //Log.i(TAG, "Exception" + response.message() + "" + response.body().getMessageEnglish());

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

    @Override
    public void onStart() {
        super.onStart();
showOrderList();
    }

    //    @Override
//    public void onResume() {
//        super.onResume();
//    }
}

//
//
//    @BindView(R.id.btn_camera)
//    ImageButton btnCamera;
//    @BindView(R.id.btn_image)
//    ImageButton btnImage;
//    @BindView(R.id.btn_microphone)
//    ImageButton btnMicrophone;
//    @BindView(R.id.complete_order)
//    Button completeOrder;
//    Unbinder unbinder;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//
//        View root = inflater.inflate(R.layout.fragment_pharmacist_home, container, false);
//        unbinder = ButterKnife.bind(this, root);
//        PharmacistHomeActivity pharmacistHomeActivity = (PharmacistHomeActivity) getActivity();
//        return root;
//    }
//
//
//    @OnClick({R.id.btn_camera, R.id.btn_image, R.id.btn_microphone, R.id.complete_order})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_camera:
//                btnCamera.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        takePhotoFromCamera();
//                    }
//                });
//                break;
//            case R.id.btn_image:
//                btnImage.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        choosePhotoFromGallary();
//                    }
//                });
//
//
//                break;
//            case R.id.btn_microphone:
//                break;
//            case R.id.complete_order:
//                break;
//        }
//    }
//
//    private void takePhotoFromCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA);
//    }
//
//    public void choosePhotoFromGallary() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(galleryIntent, DEFAULT);
//    }

