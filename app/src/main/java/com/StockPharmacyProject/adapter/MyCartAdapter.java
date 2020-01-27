package com.StockPharmacyProject.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.data.local.AppDatabase;
import com.StockPharmacyProject.data.local.CartItem;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.cart.CartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.StockPharmacyProject.helper.HelperMethod.htmlReader;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder> {

    public List<CartItem> orderItems = new ArrayList<>();
    Activity activity;


    private AppDatabase database;
    private int quantity;
    private Integer itemId;
    private PharmacistHomeActivity  pharmacistHomeActivity;
    private CartFragment cartFragment;
    private int cartItemcount;
    private int ItemNum;


    public MyCartAdapter(List<CartItem> orderItems, Activity activity) {
        this.orderItems = orderItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_medications_list, parent, false);
        database = AppDatabase.getAppDatabase(activity);
        pharmacistHomeActivity = (PharmacistHomeActivity) activity;
        return new MyCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCartViewHolder holder, final int position) {


        htmlReader(holder.itemName, (orderItems.get(position).getNameItem()));
        holder.companyName.setText(orderItems.get(position).getDescription());
        holder.quantityNum.setText("" + orderItems.get(position).getQuantity());
        holder.deleteItem.setVisibility(View.VISIBLE);
        if (orderItems.get(position).getUnits() > 1) {
            holder.packName.setText(orderItems.get(position).getPackName() +
                    "/" + orderItems.get(position).getUnits());
        } else
            holder.packName.setText(orderItems.get(position).getPackName());

        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(holder.quantityNum.getText().toString());
                quantity++;
                holder.quantityNum.setText("" + quantity);
                Toast.makeText(activity, "Quantity =" + quantity, Toast.LENGTH_SHORT).show();
                database.getItemDAO().update(orderItems.get(position).getItemId(), quantity);

            }
        });
        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(holder.quantityNum.getText().toString());
                if (quantity != 1) {
                    quantity--;
                    holder.quantityNum.setText("" + quantity);
                    Toast.makeText(activity, "Quantity =" + quantity, Toast.LENGTH_SHORT).show();
                    database.getItemDAO().update(orderItems.get(position).getItemId(), quantity);
                }
            }

        });
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Delete This Item ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (database.getItemDAO().getItems().size() != 0) {
                            pharmacistHomeActivity.setCountCartNum(View.VISIBLE, database.getItemDAO().getItems().size());
                            database.getItemDAO().delete(orderItems.get(position).getItemId());
                            orderItems.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, orderItems.size());
                            pharmacistHomeActivity.setCountCartNum(View.VISIBLE, database.getItemDAO().getItems().size());
                            Toast.makeText(activity, "Delete Item Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            pharmacistHomeActivity.setCountCartNum(View.GONE, 0);
                        }

                    }

                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                builder.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    @Override
    public long getItemId(int position) {
        return orderItems != null ? orderItems.get(position).getItemId() : 0;
    }

    class MyCartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.company_name)
        TextView companyName;
        @BindView(R.id.pack_name)
        TextView packName;
        @BindView(R.id.add_Item)
        ImageButton addItem;
        @BindView(R.id.quantity_num)
        TextView quantityNum;
        @BindView(R.id.remove_Item)
        ImageButton removeItem;
        @BindView(R.id.delete_item)
        ImageButton deleteItem;
        View view;

        MyCartViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }

    }

}
