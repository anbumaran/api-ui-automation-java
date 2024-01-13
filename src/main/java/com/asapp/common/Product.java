package com.asapp.common;

public enum Product {

    PENS("Pens"),
    STICKERS("Stickers"),
    WATER_BOTTLE("Water Bottle");

    private final String product;

    public String getProduct() {
        return this.product;
    }

    @Override
    public String toString() {
        return product;
    }

    Product(String product) {
        this.product = product;
    }

}
