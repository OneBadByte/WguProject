package com.blackdartq.WguProject.DataManagementResources;

import java.util.ArrayList;

public class Product {

    private ArrayList<Parts> associatedParts = new ArrayList<>();
    private int productId;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public int getRandomPartsID(){
        ArrayList arrayList = this.getAllPartsIDs();
        int biggestNumber = 0;
        for(Object id : arrayList){
            if((int)id > biggestNumber){
                biggestNumber = (int)id;
            }
        }
        return biggestNumber + 1;
    }

    public int getAssociatedPartsSize(){
        return this.associatedParts.size();
    }

    public void createProduct(int productID, String name, double price, int inStock, int max, int min){
        this.setProductId(productID);
        this.setName(name);
        this.setInStock(inStock);
        this.setPrice(price);
        this.setMax(max);
        this.setMin(min);
    }

    public void createTestData(){
        Parts part1 = new Parts() {};
        Parts part2 = new Parts() {};
        Parts part3 = new Parts() {};
        Parts part4 = new Parts() {};

        part1.createPart(1, "part 1", 3.51, 5, 10, 1);
        part2.createPart(2, "part 2", 3.52, 1, 10, 1);
        part3.createPart(3, "part 3", 3.53, 4, 10, 1);
        part4.createPart(4, "part 4", 3.54, 2, 10, 1);

        this.addAssociatedPart(part1);
        this.addAssociatedPart(part2);
        this.addAssociatedPart(part3);
        this.addAssociatedPart(part4);
    }

    //++++ methods for associatedParts ++++

    public void addAssociatedPart(Parts part){
        this.associatedParts.add(part);
    }

    public void addAssociatedPart(int index, Parts part){
        this.associatedParts.remove(index);
        this.associatedParts.add(index, part);
    }

    public boolean removeAssociatedPart(int partNumber){
        try{
            this.associatedParts.remove(partNumber);
            return true;

        }catch(Exception e){
            return false;
        }
    }

    public Parts lookupAssociatedPart(int partNumber){
        return this.associatedParts.get(partNumber);
    }

    public ArrayList<Parts> getAllAssociatedParts(){
        return this.associatedParts;
    }

    //-------------------------------------

    //++++ getters and setters for productId, name, price, inStock, min, max ++++
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
    //---------------------------------------------------

    public ArrayList getAllPartsIDs(){
        ArrayList<Integer> partIDList = new ArrayList<>();
        for(Parts part : this.associatedParts){
            partIDList.add(part.getPartID());
        }
        return partIDList;
    }

    public ArrayList getAllPartsNames(){
        ArrayList<String> partIDList = new ArrayList<>();
        for(Parts part : this.associatedParts){
            partIDList.add(part.getName());
        }
        return partIDList;
    }

    public ArrayList getAllPartsInStocks(){
        ArrayList<Integer> partIDList = new ArrayList<>();
        for(Parts part : this.associatedParts){
            partIDList.add(part.getInStock());
        }
        return partIDList;
    }

    public ArrayList getAllPartsPrices(){
        ArrayList<Double> partIDList = new ArrayList<>();
        for(Parts part : this.associatedParts){
            partIDList.add(part.getPrice());
        }
        return partIDList;
    }

}
