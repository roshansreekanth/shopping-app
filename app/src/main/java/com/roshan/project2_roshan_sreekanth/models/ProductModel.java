package com.roshan.project2_roshan_sreekanth.models;

public class ProductModel
{
    private String productName;
    private float productPrice;
    private String productCode;

    public ProductModel(String productName, String productCode, float productPrice)
    {
        this.productName = productName;
        this.productCode = productCode;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

}
