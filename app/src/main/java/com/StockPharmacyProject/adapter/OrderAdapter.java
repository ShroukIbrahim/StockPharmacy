package com.StockPharmacyProject.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.data.modele.AddOrder.AddOrder;
import com.StockPharmacyProject.data.modele.PendingOrderList.Order;
import com.StockPharmacyProject.data.reset.ApiServices;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.orders.OrderDetilsFragment;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.StockPharmacyProject.data.reset.RetrofitClient.getClient;
import static com.StockPharmacyProject.helper.HelperMethod.replace;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderAdapterViewHolder> {



    private Activity activity;
    private List<Order> orderData;
    ApiServices apiServices = getClient().create(ApiServices.class);
    private Integer orderId;

    public OrderAdapter(Activity activity, List<Order> orderData) {
        this.activity = activity;
        this.orderData = orderData;
    }


    @NonNull
    @Override
    public OrderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_order, parent, false);
        return new OrderAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapterViewHolder holder, int position) {
//        String dateStr = orderData.get(position).getOrderDate();
//        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
//        Date dateObj = null;
//        try {
//            dateObj = curFormater.parse(dateStr);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        String newDateStr = curFormater.format(dateObj);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(orderData.get(position).getOrderDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);


        holder.timeNews.setText(formattedTime);
        holder.pharmacyName.setText(orderData.get(position).getPharmacy().getName());
        holder.orderStatus.setText(orderData.get(position).getOrderStatusString());
        holder.numOfOrder.setText("order has " + orderData.get(position).getNumberOfItems() + " items.");

        orderId = orderData.get(position).getOrderId();
        final Order order = orderData.get(position);
//        holder.orderItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OrderDetilsFragment orderDetilsFragment = new OrderDetilsFragment(orderId);
//                FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
//                replace(orderDetilsFragment, manager, R.id.nav_host_fragment, null, null);
//            }
//        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(order);

            }
        });
    }

    public void deleteItem(Order order) {
        apiServices.CancelOrder(orderId).enqueue(new Callback<AddOrder>() {
            @Override
            public void onResponse(Call<AddOrder> call, Response<AddOrder> response) {
                if (response.body().getStatus().equals(1)) {
                    int position = orderData.indexOf(order);
                    if (orderData.size() != 0) {
                        orderData.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, orderData.size());
                        Toast.makeText(activity, "Delete Order Successful", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<AddOrder> call, Throwable t) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return orderData.size();
    }

    @Override
    public long getItemId(int position) {
        return orderData != null ? orderData.get(position).getOrderId() : 0;
    }


    public class OrderAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.time_news)
        RelativeTimeTextView timeNews;
        @BindView(R.id.pharmacy_name)
        TextView pharmacyName;
        @BindView(R.id.order_status)
        TextView orderStatus;
        @BindView(R.id.num_of_order)
        TextView numOfOrder;
        @BindView(R.id.delete_button)
        ImageButton deleteButton;
        @BindView(R.id.order_item)
        LinearLayout orderItem;
        View view;

        public OrderAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }


}
