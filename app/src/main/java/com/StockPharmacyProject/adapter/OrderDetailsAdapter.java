package com.StockPharmacyProject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.data.local.AppDatabase;
import com.StockPharmacyProject.data.local.CartItem;
import com.StockPharmacyProject.data.modele.OrderDetail.Item;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;
import com.StockPharmacyProject.ui.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.StockPharmacyProject.helper.HelperMethod.htmlReader;

public class OrderDetailsAdapter  extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>  {


    private Context context;
    private ArrayList<Item> items;

    public OrderDetailsAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public OrderDetailsAdapter.OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_medications_list, parent, false);
        return new OrderDetailsAdapter.OrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.OrderDetailsViewHolder holder, int position) {
        // holder.itemName.setText(medicationData.get(position).getEnglishName());
        htmlReader(holder.itemName, (items.get(position).getEnglishName()));
        holder.companyName.setText(items.get(position).getCompanyName());
        if (items.get(position).getUnits() > 1) {
            holder.packName.setText(items.get(position).getPack() +
                    "/" + items.get(position).getUnits());
        } else
            holder.packName.setText(""+items.get(position).getPack());
        holder.quantityNum.setText(""+items.get(position).getQuantity());
        holder.addItem.setVisibility(View.GONE);
        holder.removeItem.setVisibility(View.GONE);
        holder.addCart.setVisibility(View.GONE);


    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class OrderDetailsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image)
        ImageView itemImage;
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
        @BindView(R.id.add_cart)
        ImageButton addCart;
        View view;

        public OrderDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }


}

