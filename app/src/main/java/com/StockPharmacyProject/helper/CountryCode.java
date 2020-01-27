package com.StockPharmacyProject.helper;


import java.util.ArrayList;
import java.util.List;

public class CountryCode {

    public List<CountryCode> COUNTRIES = new ArrayList<>();
    private String code;
    private String name;
    private String dialCode;
    private int flag = -1;
    private int length_min = 11;
    private int length_max = 12;

    public static List<CountryCode> allCountriesList;

    public CountryCode(String code, String name, String dialCode, int length_min, int length_max) {
        this.code = code;
        this.name = name;
        this.dialCode = dialCode;
        this.length_min = length_min;
        this.length_max = length_max;
    }

    public CountryCode() {

        COUNTRIES.add(new CountryCode("EG", "Egypt", "+20", 11, 12));

    }

    public CountryCode getCountry(String code) {

        for (int i = 0; i < COUNTRIES.size(); i++) {
            if (COUNTRIES.get(i).getCode().equals(code)) {
                return COUNTRIES.get(i);
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getLength_min() {
        return length_min;
    }

    public void setLength_min(int length_min) {
        this.length_min = length_min;
    }

    public int getLength_max() {
        return length_max;
    }

    public void setLength_max(int length_max) {
        this.length_max = length_max;
    }
}

