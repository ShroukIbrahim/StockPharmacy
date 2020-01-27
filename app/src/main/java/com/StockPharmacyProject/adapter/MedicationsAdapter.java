package com.StockPharmacyProject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
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
import com.StockPharmacyProject.data.modele.MedicationList.Medication_Data;
import com.StockPharmacyProject.ui.activities.pharmacist.PharmacistHomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.StockPharmacyProject.helper.HelperMethod.dismissProgressDialogC;
import static com.StockPharmacyProject.helper.HelperMethod.htmlReader;
import static com.StockPharmacyProject.helper.HelperMethod.showProgressDialogC;

public class MedicationsAdapter extends RecyclerView.Adapter<MedicationsAdapter.MedicationsViewHolder> {


    private Context context;
    private List<Medication_Data> medicationData = new ArrayList<>();
    private int quantity;
    private AppDatabase database;
    private Integer itemId, units;
    private String itemName, companyName, pack;
    private boolean checkItem = true;
    private Integer q;
    private PharmacistHomeActivity pharmacistHomeActivity;
    private int cartItemNum;

    public MedicationsAdapter(Context context, List<Medication_Data> medicationData) {
        this.context = context;
        this.medicationData = medicationData;
    }


    @NonNull
    @Override
    public MedicationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_medications_list, parent, false);
        pharmacistHomeActivity = (PharmacistHomeActivity) context;
        database = AppDatabase.getAppDatabase(context);
        return new MedicationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationsViewHolder holder, int position) {

        if (medicationData.get(position).getUnits() > 1) {
            holder.packName.setText(medicationData.get(position).getPack() +
                    "/" + medicationData.get(position).getUnits());
        } else {
            holder.packName.setText(medicationData.get(position).getPack());
        }

        htmlReader(holder.itemName, (medicationData.get(position).getEnglishName()));
        holder.companyName.setText(medicationData.get(position).getCompanyName());
        for (int c=0;c<database.getItemDAO().getItems().size();c++)
        {
            if (database.getItemDAO().getItems().get(c).getItemId().equals(medicationData.get(position).getItemId())){
                holder.addCart.setImageResource(R.drawable.ic_add_shopping_cart_red_24dp);
                holder.quantityNum.setText(""+database.getItemDAO().getItems().get(c).getQuantity());
                break;
            }
            else {
                holder.addCart.setImageResource(R.drawable.ic_add_shopping_cart_blue_24dp);
                holder.quantityNum.setText("" + 1);

            }
        }
        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(holder.quantityNum.getText().toString());
                quantity++;
                holder.quantityNum.setText("" + quantity);

            }
        });
        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(holder.quantityNum.getText().toString());
                if (quantity != 1) {
                    quantity--;
                    holder.quantityNum.setText("" + quantity);
                }
            }
        });
        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemId = medicationData.get(position).getItemId();
                itemName = medicationData.get(position).getEnglishName();
                companyName = medicationData.get(position).getCompanyName();
                pack = medicationData.get(position).getPack();
                units = medicationData.get(position).getUnits();
                final CartItem item = new CartItem(itemId, itemName, companyName, pack, units, quantity);
                if ((database.getItemDAO().getItems().size() < 10)) {

                    if (database.getItemDAO().getItems().size() == 0) {
                        database.getItemDAO().insert(item);
                        holder.addCart.setImageResource(R.drawable.ic_add_shopping_cart_red_24dp);
                        pharmacistHomeActivity.setCountCartNum(View.VISIBLE, database.getItemDAO().getItems().size());
                        holder.quantityNum.setText("" + 1);
                        Toast.makeText(context, "Add Successful..", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for (int j = 0; j < database.getItemDAO().getItems().size(); j++) {
                        if (database.getItemDAO().getItems().get(j).getItemId().equals(itemId)) {
                            checkItem = false;
                            break;

                        }
                        else
                        {
                            checkItem=true;
                        }
                    }

                    if (checkItem) {
                        database.getItemDAO().insert(item);
                        holder.addCart.setImageResource(R.drawable.ic_add_shopping_cart_red_24dp);
                        pharmacistHomeActivity.setCountCartNum(View.VISIBLE, database.getItemDAO().getItems().size());
                        checkItem = false;
                        //holder.quantityNum.setText("" + 1);
                        Toast.makeText(context, "Add Successful......", Toast.LENGTH_SHORT).show();

                        System.out.println(checkItem);
                    } else {
                        System.out.println(checkItem);
                        holder.addCart.setImageResource(R.drawable.ic_add_shopping_cart_red_24dp);
                        database.getItemDAO().update(itemId, quantity);
                        pharmacistHomeActivity.setCountCartNum(View.VISIBLE, database.getItemDAO().getItems().size());
                        checkItem = true;
                       // holder.quantityNum.setText("" + 1);
                        Toast.makeText(context, "Update Successful...", Toast.LENGTH_SHORT).show();

                    }


                } else {
                    Toast.makeText(context, "Max Item In Cart equal 10", Toast.LENGTH_SHORT).show();
                }
            }


        });

    }

//    public void ShowDialogAdd(String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage(message);
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                database.getItemDAO().insert(item);
//                cartItemNum = database.getItemDAO().getItems().size();
//                showProgressDialogC(context, "Please Wait..");
//                notifyDataSetChanged();
//                Toast.makeText(context, "Add Successful..", Toast.LENGTH_SHORT).show();
//                pharmacistHomeActivity = (PharmacistHomeActivity) context;
//                pharmacistHomeActivity.setCountCartNum(View.VISIBLE, cartItemNum);
//                dismissProgressDialogC();
//            }
//
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                return;
//            }
//        });
//
//        builder.show();
//    }
//
//    public void ShowDialogUpdate(String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage(message);
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                database.getItemDAO().update(itemId, quantity);
//                cartItemNum = database.getItemDAO().getItems().size();
//                Toast.makeText(context, "Update Successful...", Toast.LENGTH_SHORT).show();
//                showProgressDialogC(context, "Please Wait..");
//                pharmacistHomeActivity = (PharmacistHomeActivity) context;
//                pharmacistHomeActivity.setCountCartNum(View.VISIBLE, cartItemNum);
//                dismissProgressDialogC();
//            }
//
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                return;
//            }
//        });
//
//        builder.show();
//    }


    @Override
    public int getItemCount() {
        return medicationData.size();
    }


    public class MedicationsViewHolder extends RecyclerView.ViewHolder {
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

        public MedicationsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }


}
