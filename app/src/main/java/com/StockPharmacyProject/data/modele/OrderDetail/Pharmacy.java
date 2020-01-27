
package com.StockPharmacyProject.data.modele.OrderDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pharmacy {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("GovernorateId")
    @Expose
    private Integer governorateId;
    @SerializedName("Latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitudes")
    @Expose
    private Object longitudes;
    @SerializedName("UserToken")
    @Expose
    private Object userToken;
    @SerializedName("PharmacyId")
    @Expose
    private Integer pharmacyId;
    @SerializedName("Street")
    @Expose
    private Object street;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getGovernorateId() {
        return governorateId;
    }

    public void setGovernorateId(Integer governorateId) {
        this.governorateId = governorateId;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(Object longitudes) {
        this.longitudes = longitudes;
    }

    public Object getUserToken() {
        return userToken;
    }

    public void setUserToken(Object userToken) {
        this.userToken = userToken;
    }

    public Integer getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Integer pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Object getStreet() {
        return street;
    }

    public void setStreet(Object street) {
        this.street = street;
    }

}
