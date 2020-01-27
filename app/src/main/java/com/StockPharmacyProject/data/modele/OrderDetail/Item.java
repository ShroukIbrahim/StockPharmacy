
package com.StockPharmacyProject.data.modele.OrderDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("ItemId")
    @Expose
    private Integer itemId;
    @SerializedName("EnglishName")
    @Expose
    private String englishName;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("Units")
    @Expose
    private Integer units;
    @SerializedName("Pack")
    @Expose
    private Object pack;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Object getPack() {
        return pack;
    }

    public void setPack(Object pack) {
        this.pack = pack;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
