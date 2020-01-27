
package com.StockPharmacyProject.data.modele.Governorates;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("data")
    @Expose
    private List<GovernoratesData> data = null;

    public List<GovernoratesData> getData() {
        return data;
    }

    public void setData(List<GovernoratesData> data) {
        this.data = data;
    }

}
