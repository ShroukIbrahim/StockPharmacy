package com.StockPharmacyProject.data.reset;


import com.StockPharmacyProject.data.modele.AddOrder.AddOrder;
import com.StockPharmacyProject.data.modele.GetPharmacyList.PharmacyData;
import com.StockPharmacyProject.data.modele.GetPharmacyList.PharmacyList;
import com.StockPharmacyProject.data.modele.Governorates.Governorates;

import com.StockPharmacyProject.data.modele.MedicationList.MedicationList;
import com.StockPharmacyProject.data.modele.OrderDetail.Data;
import com.StockPharmacyProject.data.modele.OrderDetail.Order;
import com.StockPharmacyProject.data.modele.OrderDetail.OrderDetail;
import com.StockPharmacyProject.data.modele.PendingOrderList.OrderList;
import com.StockPharmacyProject.data.modele.PharmacyInfo.PharmacyInfo;
import com.StockPharmacyProject.data.modele.confirmOrder.ConfirmOrder;
import com.StockPharmacyProject.data.modele.confirmOrder.Item;
import com.StockPharmacyProject.data.modele.confirmOrder.Pharmacy;
import com.StockPharmacyProject.data.modele.notification.NotificationsToken;
import com.StockPharmacyProject.data.modele.user.User.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    //User

    @POST("User/Login")
    @FormUrlEncoded
    Call<User> Login(@Field("Phone") String phone, @Field("Password") String password);

    @POST("User/RegisterUser")
    @FormUrlEncoded
    Call<User> Register(@Field("Name") String name,
                        @Field("Email") String email,
                        @Field("Phone") String phone,
                        @Field("Password") String password,
                        @Field("UserType") int usertype);


    //List MedicationItems

    @GET("Item/GetItems")
    Call<MedicationList> getAllItems(@Query("PageNumber") int pageNumber);


    @GET("Item/GetItems")
    Call<MedicationList> searchOfAllItems(@Query("PageNumber") int pageNumber, @Query("Description") String description);

    //pharmacy

    @POST("Pharmacy/AddPharmacy")
    @FormUrlEncoded
    Call<PharmacyList> addPharmacy(
            @Field("Name") String name,
            @Field("CityId") int CityId,
            @Field("GovernorateId") int GovernorateId,
            @Field("Latitude") double Latitude,
            @Field("longitudes") double longitudes,
            @Field("Street") String street);


    @POST("Pharmacy/UpdatePharmacy")
    @FormUrlEncoded
    Call<PharmacyList> updatePharmacy(
            @Field("Name") String name,
            @Field("CityId") int CityId,
            @Field("GovernorateId") int GovernorateId,
            @Field("Latitude") double Latitude,
            @Field("longitudes") double longitudes,
            @Field("PharmacyId") int pharmacy_id,
            @Field("Street") String street);

    @GET("Pharmacy/GetPharmacyList")
    Call<PharmacyList> getAllPharmacy();

    @GET("Pharmacy/GetPharmacyData")
    Call<PharmacyInfo> getPharmacyInfo(@Query("PharmacyId") int PharmacyId);


    //order
    @POST("Order/AddOrder")
    @FormUrlEncoded
    Call<AddOrder> confirmOrder(@Field("Items[]") List<Item> items,
                                @Field("Pharmacy") Pharmacy pharmacy);

    @POST("Order/AddOrder")
    Call<AddOrder> postData(@Body ConfirmOrder confirmOrder);

    @POST("Order/CancelOrder")
    @FormUrlEncoded
    Call<AddOrder> CancelOrder(@Field("OrderId") int intOrderId );


    @GET("Order/GetPendingOrderList")
    Call<OrderList> showOrder();

    @GET("Order/GetOrderDetailByOrderId")
    Call<OrderDetail> showOrderDetails(@Query("orderId") int orderId);

    //Places
    @GET("Utility/GetGovernorates")
    Call<Governorates> getAllGovernor();

    @GET("Utility/GetCities")
    Call<Governorates> getAllCites(@Query("governorateId") int governorateId);


    @POST("register-token")
    @FormUrlEncoded
    Call<NotificationsToken> RegisterToken(@Field("token") String token
            , @Field("api_token") String api_token, @Field("type") String type);

}
