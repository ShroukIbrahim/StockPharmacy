
package com.StockPharmacyProject.data.modele.user.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User_ {

    @SerializedName("UserToken")
    @Expose
    private String userToken;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("UserType")
    @Expose
    private Integer userType;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

}
