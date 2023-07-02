package com.example.blink.ui.customer.search;

public class SearchRecyclerViewItem {
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    Integer productId;
    String productName;
    Double productPrice;
    String productSupplier;

    public SearchRecyclerViewItem(Integer productId, String productName, Double productPrice, String productSupplier) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productSupplier = productSupplier;
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
