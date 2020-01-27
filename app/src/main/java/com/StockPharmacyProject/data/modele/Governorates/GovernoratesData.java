
package com.StockPharmacyProject.data.modele.Governorates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GovernoratesData {

    @SerializedName("Key")
    @Expose
    private Integer key;
    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("ValueAR")
    @Expose
    private String valueAR;

    public GovernoratesData(Integer key, String value) {
        this.key = key;
        this.valueAR = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueAR() {
        return valueAR;
    }

    public void setValueAR(String valueAR) {
        this.valueAR = valueAR;
    }

}
