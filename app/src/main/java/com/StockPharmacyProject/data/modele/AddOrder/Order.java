
package com.StockPharmacyProject.data.modele.AddOrder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("OrderId")
    @Expose
    private Integer orderId;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("Items")
    @Expose
    private List<Object> items = null;
    @SerializedName("NumberOfItems")
    @Expose
    private Integer numberOfItems;
    @SerializedName("Pharmacy")
    @Expose
    private Pharmacy pharmacy;
    @SerializedName("OrderStatus")
    @Expose
    private Integer orderStatus;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("OrderStatusString")
    @Expose
    private String orderStatusString;
    @SerializedName("UserToken")
    @Expose
    private Object userToken;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderStatusString() {
        return orderStatusString;
    }

    public void setOrderStatusString(String orderStatusString) {
        this.orderStatusString = orderStatusString;
    }

    public Object getUserToken() {
        return userToken;
    }

    public void setUserToken(Object userToken) {
        this.userToken = userToken;
    }

}
