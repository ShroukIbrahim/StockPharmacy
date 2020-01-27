
package com.StockPharmacyProject.data.modele.GetPharmacyList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Governorate {

    @SerializedName("Key")
    @Expose
    private Integer key;
    @SerializedName("Value")
    @Expose
    private Object value;
    @SerializedName("ValueAR")
    @Expose
    private Object valueAR;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValueAR() {
        return valueAR;
    }

    public void setValueAR(Object valueAR) {
        this.valueAR = valueAR;
    }

}
