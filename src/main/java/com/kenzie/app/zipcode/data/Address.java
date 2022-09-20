package com.kenzie.app.zipcode.data;

public class Address {

    //declare properties
    private String name;
    private String streetName;
    private String cityName;
    private String stateName; //enum for state
    private int zipcode;



    //declare constructors
    public Address(String name, String streetName, String cityName, String stateName, int zipcode) {
        this.name = name;
        this.streetName = streetName;
        this.cityName = cityName;
        this.stateName = stateName;
        this.zipcode = zipcode;
    }

    public Address() {
        name = "";
        streetName ="";
        cityName ="";
        stateName ="";
        zipcode = 0;

    }

    public Address(String name, String streetName, String cityName, String stateName) {
        this.name = name;
        this.streetName = streetName;
        this.cityName = cityName;
        this.stateName = stateName;
    }



    //declare methods
    //getters/setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
}
