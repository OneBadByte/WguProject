package com.blackdartq.WguProject.JavaResources;

public abstract class Parts {

    private int partID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public void createPart(int partID, String name, double price, int inStock, int max, int min){
        this.setPartID(partID);
        this.setName(name);
        this.setInStock(inStock);
        this.setPrice(price);
        this.setMax(max);
        this.setMin(min);
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
