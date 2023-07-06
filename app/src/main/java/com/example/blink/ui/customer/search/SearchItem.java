package com.example.blink.ui.customer.search;

public class SearchItem {
    Integer productId;
    String productName;
    Double productPrice;
    String productSupplier;

    public SearchItem(Integer productId, String productName, Double productPrice, String productSupplier) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productSupplier = productSupplier;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(String productSupplier) {
        this.productSupplier = productSupplier;
    }

}
