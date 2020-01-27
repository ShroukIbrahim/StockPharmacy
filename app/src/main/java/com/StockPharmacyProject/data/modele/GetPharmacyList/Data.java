
package com.StockPharmacyProject.data.modele.GetPharmacyList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("data")
    @Expose
    private List<PharmacyData> data = null;

    public List<PharmacyData> getData() {
        return data;
    }

    public void setData(List<PharmacyData> data) {
        this.data = data;
    }

}
