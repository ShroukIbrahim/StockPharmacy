
package com.StockPharmacyProject.data.modele.confirmOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pharmacy {

    @SerializedName("PharmacyId")
    @Expose
    private Integer pharmacyId;

    public Pharmacy(Integer pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Pharmacy() {

    }


    public Integer getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Integer pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

}
