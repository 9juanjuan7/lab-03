package com.example.listycitylab3;

import java.io.Serializable; // Import Serializable

public class City implements Serializable { // Implement Serializable
    private String name;
    private String province;

    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    // Setter for province
    public void setProvince(String province) {
        this.province = province;
    }
}
