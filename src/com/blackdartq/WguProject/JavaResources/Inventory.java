package com.blackdartq.WguProject.JavaResources;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Parts> parts = new ArrayList<>();

    //++++ products functions ++++
    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addProduct(int index, Product product) {
        this.products.remove(index);
        this.products.add(index, product);
    }

    public boolean removeProduct(int productNumber) {
        try {
            this.products.remove(productNumber);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public Product lookupProduct(int productNumber) {
        return this.products.get(productNumber);
    }

    public void updateProduct(int productNumber){
        Product product = this.products.get(productNumber);
        // add something here
    }
    //----------------------------

    //++++ parts functions ++++
    public void addParts(Parts part) {
        this.parts.add(part);
    }

    public void addParts(int index, Parts part) {
        this.parts.add(index, part);
    }

    public boolean removeParts(int partNumber) {
        try {
            this.parts.remove(partNumber);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public Parts lookupParts(int partNumber) {
        return this.parts.get(partNumber);
    }

    public void updateParts(int partNumber){
        Parts part = this.parts.get(partNumber);
        // add something here
    }

    //----------------------------

    public void createTestPartsData(){
        Parts part1 = new Parts() {};
        Parts part2 = new Parts() {};
        Parts part3 = new Parts() {};
        Parts part4 = new Parts() {};
        part1.createPart(1, "part 1", 3.51, 5, 10, 1);
        part2.createPart(2, "part 2", 3.52, 1, 10, 1);
        part3.createPart(3, "part 3", 3.53, 4, 10, 1);
        part4.createPart(4, "part 4", 3.54, 2, 10, 1);
        this.addParts(part1);
        this.addParts(part2);
        this.addParts(part3);
        this.addParts(part4);


    }

    public void createTestProductData(){
        Product product1 = new Product() {};
        Product product2 = new Product() {};
        Product product3 = new Product() {};
        Product product4 = new Product() {};

        product1.createProduct(1, "product 1", 3.51, 5, 10, 1);
        product2.createProduct(2, "product 2", 3.52, 1, 10, 1);
        product3.createProduct(3, "product 3", 3.53, 4, 10, 1);
        product4.createProduct(4, "product 4", 3.54, 2, 10, 1);

        product1.createTestData();
        product2.createTestData();
        product3.createTestData();
        product4.createTestData();

        this.addProduct(product1);
        this.addProduct(product2);
        this.addProduct(product3);
        this.addProduct(product4);


    }

    public int getPartsSize(){
        return this.parts.size();
    }

    public ArrayList getAllPartsIDs(){
        ArrayList<Integer> partIDList = new ArrayList<>();
        for(Parts part : this.parts){
            partIDList.add(part.getPartID());
        }
        return partIDList;
    }

    public ArrayList getAllPartsNames(){
        ArrayList<String> partIDList = new ArrayList<>();
        for(Parts part : this.parts){
            partIDList.add(part.getName());
        }
        return partIDList;
    }

    public ArrayList getAllPartsInStocks(){
        ArrayList<Integer> partIDList = new ArrayList<>();
        for(Parts part : this.parts){
            partIDList.add(part.getInStock());
        }
        return partIDList;
    }

    public ArrayList getAllPartsPrices(){
        ArrayList<Double> partIDList = new ArrayList<>();
        for(Parts part : this.parts){
            partIDList.add(part.getPrice());
        }
        return partIDList;
    }

    public ArrayList getAllProductIDs(){
        ArrayList<Integer> productIDList = new ArrayList<>();
        for(Product product : this.products){
            productIDList.add(product.getProductId());
        }
        return productIDList;
    }
    public ArrayList getAllProductNames(){
        ArrayList<String> productIDList = new ArrayList<>();
        for(Product product : this.products){
            productIDList.add(product.getName());
        }
        return productIDList;
    }
    public ArrayList getAllProductPrices(){
        ArrayList<Double> productIDList = new ArrayList<>();
        for(Product product : this.products){
            productIDList.add(product.getPrice());
        }
        return productIDList;
    }
    public ArrayList getAllProductInStocks(){
        ArrayList<Integer> productIDList = new ArrayList<>();
        for(Product product : this.products){
            productIDList.add(product.getInStock());
        }
        return productIDList;
    }

}
