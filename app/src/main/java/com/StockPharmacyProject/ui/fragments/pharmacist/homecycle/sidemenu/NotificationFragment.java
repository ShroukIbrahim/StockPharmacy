package com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.adapter.MedicationsAdapter;
import com.StockPharmacyProject.ui.fragments.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

import static com.StockPharmacyProject.helper.RecycleTool.setRecycleTool;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseFragment {


    @BindView(R.id.list_notification)
    RecyclerView listNotification;
    @BindView(R.id.error_image)
    ImageView errorImage;
    @BindView(R.id.error_title)
    TextView errorTitle;
    @BindView(R.id.error_action)
    TextView errorAction;
    @BindView(R.id.error_sub_view)
    LinearLayout errorSubView;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        return view;
    }

}

