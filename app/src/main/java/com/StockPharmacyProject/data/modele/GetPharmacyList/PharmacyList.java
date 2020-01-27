
package com.StockPharmacyProject.data.modele.GetPharmacyList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PharmacyList {

    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("MessageArabic")
    @Expose
    private String messageArabic;
    @SerializedName("MessageEnglish")
    @Expose
    private String messageEnglish;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getMessageArabic() {
        return messageArabic;
    }

    public void setMessageArabic(String messageArabic) {
        this.messageArabic = messageArabic;
    }

    public Object getMessageEnglish() {
        return messageEnglish;
    }

    public void setMessageEnglish(String messageEnglish) {
        this.messageEnglish = messageEnglish;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}