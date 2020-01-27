
package com.StockPharmacyProject.data.modele.MedicationList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data__ {

    @SerializedName("data")
    @Expose
    private List<Medication_Data> data = null;

    public List<Medication_Data> getData() {
        return data;
    }

    public void setData(List<Medication_Data> data) {
        this.data = data;
    }

}
