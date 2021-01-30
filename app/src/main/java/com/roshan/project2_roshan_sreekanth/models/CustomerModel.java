package com.roshan.project2_roshan_sreekanth.models;

public class CustomerModel
{
    private int id;
    private  String username;
    private String password;
    private String address;

    public CustomerModel(String username, String password, String address)
    {
        this.username = username;
        this.password = password;
        this.address = address;
    }

    @Override
    public String toString()
    {
        return "CustomerModel{username = " + username + ", password = " + password + ", address = " + address;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getAddress()
    {
        return address;
    }
}
