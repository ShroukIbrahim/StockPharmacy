
package com.StockPharmacyProject.data.modele.PharmacyInfo;

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
    private Double latitude;
    @SerializedName("longitudes")
    @Expose
    private Double longitudes;
    @SerializedName("UserToken")
    @Expose
    private Object userToken;
    @SerializedName("PharmacyId")
    @Expose
    private Integer pharmacyId;
    @SerializedName("Street")
    @Expose
    private String street;
    @SerializedName("City")
    @Expose
    private City city;
    @SerializedName("Governorate")
    @Expose
    private Governorate governorate;

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(Double longitudes) {
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Governorate getGovernorate() {
        return governorate;
    }

    public void setGovernorate(Governorate governorate) {
        this.governorate = governorate;
    }

}
