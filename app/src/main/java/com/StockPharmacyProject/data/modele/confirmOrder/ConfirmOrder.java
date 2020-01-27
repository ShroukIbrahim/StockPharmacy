
package com.StockPharmacyProject.data.modele.confirmOrder;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmOrder {

    @SerializedName("Items")
    @Expose
    private List<Item> items ;
    @SerializedName("Pharmacy")
    @Expose
    private Pharmacy pharmacy;


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

}
