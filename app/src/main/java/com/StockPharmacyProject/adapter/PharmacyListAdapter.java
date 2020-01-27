package com.StockPharmacyProject.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.data.modele.GetPharmacyList.PharmacyData;
import com.StockPharmacyProject.helper.HelperMethod;
import com.StockPharmacyProject.ui.fragments.BaseFragment;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.pharmacy.AddPharmacyInfoFragment;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.pharmacy.EditPharmacyFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PharmacyListAdapter extends RecyclerView.Adapter<PharmacyListAdapter.PharmacyListViewHolder> {



    private Activity activity;
    private List<PharmacyData> pharmacyLists;


    public PharmacyListAdapter(Activity activity, List<PharmacyData> pharmacyLists) {
        this.activity = activity;
        this.pharmacyLists = pharmacyLists;
    }

    @NonNull
    @Override
    public PharmacyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_pharmacy, parent, false);
        return new PharmacyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyListViewHolder holder, int position) {
        holder.pharmacyName.setText(pharmacyLists.get(position).getName());
        int pharmacyId= pharmacyLists.get(position).getPharmacyId();

        holder.pharmacyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditPharmacyFragment editPharmacyFragment = new EditPharmacyFragment(pharmacyId);
                FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
                HelperMethod.replace(editPharmacyFragment,manager,R.id.nav_host_fragment,null,null);

            }
        });
    }

    @Override
    public int getItemCount() {
        return pharmacyLists.size();
    }




    public class PharmacyListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pharmacy_name)
        TextView pharmacyName;
        @BindView(R.id.delete_button)
        ImageButton deleteButton;
//        @BindView(R.id.pharmacy_show_info)
//        RelativeLayout pharmacyShowInfo;
        View view;

        public PharmacyListViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }


}
