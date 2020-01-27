
package com.StockPharmacyProject.data.modele.MedicationList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_ {

    @SerializedName("CurrentPage")
    @Expose
    private Integer currentPage;
    @SerializedName("data")
    @Expose
    private Data__ data;
    @SerializedName("FirstPageURL")
    @Expose
    private String firstPageURL;
    @SerializedName("From")
    @Expose
    private Integer from;
    @SerializedName("LastPage")
    @Expose
    private Integer lastPage;
    @SerializedName("LastPageURL")
    @Expose
    private String lastPageURL;
    @SerializedName("NextPageURL")
    @Expose
    private String nextPageURL;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("PerPage")
    @Expose
    private Integer perPage;
    @SerializedName("PreviousPageURL")
    @Expose
    private String previousPageURL;
    @SerializedName("To")
    @Expose
    private Integer to;
    @SerializedName("Total")
    @Expose
    private Integer total;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Data__ getData() {
        return data;
    }

    public void setData(Data__ data) {
        this.data = data;
    }

    public String getFirstPageURL() {
        return firstPageURL;
    }

    public void setFirstPageURL(String firstPageURL) {
        this.firstPageURL = firstPageURL;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public String getLastPageURL() {
        return lastPageURL;
    }

    public void setLastPageURL(String lastPageURL) {
        this.lastPageURL = lastPageURL;
    }

    public String getNextPageURL() {
        return nextPageURL;
    }

    public void setNextPageURL(String nextPageURL) {
        this.nextPageURL = nextPageURL;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public String getPreviousPageURL() {
        return previousPageURL;
    }

    public void setPreviousPageURL(String previousPageURL) {
        this.previousPageURL = previousPageURL;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
