package com.StockPharmacyProject.data.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "itemsOrder")
public class CartItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "itemId")
    private Integer itemId;

    @ColumnInfo(name = "itemName")
    private String nameItem;

    @ColumnInfo(name = "companyName")
    private String description;

    @ColumnInfo(name = "packName")
    private String packName;

    @ColumnInfo(name = "units")
    private int units;

    @ColumnInfo(name = "quantity")
    private Integer quantity;


    public CartItem(Integer itemId, String nameItem, String description, String packName, int units, Integer quantity) {
        this.id = id;
        this.itemId = itemId;
        this.nameItem = nameItem;
        this.description = description;
        this.packName = packName;
        this.units = units;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}