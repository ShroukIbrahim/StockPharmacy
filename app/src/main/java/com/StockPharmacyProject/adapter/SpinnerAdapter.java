package com.StockPharmacyProject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.data.modele.Governorates.GovernoratesData;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<GovernoratesData> generalResponseDataList = new ArrayList<>();
    private LayoutInflater inflter;
    public int selectedId = 0;

    public SpinnerAdapter(Context applicationContext) {
        this.context = applicationContext;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<GovernoratesData> generalResponseDataList, String hint) {
        this.generalResponseDataList = new ArrayList<>();
        this.generalResponseDataList.add(new GovernoratesData(0, hint));
        this.generalResponseDataList.addAll(generalResponseDataList);
    }

    @Override
    public int getCount() {
        return generalResponseDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.items_custom_spinner, null);
for (int j=0;j<generalResponseDataList.size();j++) {
    TextView names = view.findViewById(R.id.item_spinner_tv_text);
    names.setText(generalResponseDataList.get(i).getValue());
    selectedId = generalResponseDataList.get(i).getKey();
}
            return view;

    }
}