package com.blackdartq.WguProject;

import com.blackdartq.WguProject.JavaResources.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;

public class AddAndModifyProduct extends Util {

    public void initialize() {
        productHeaderLabel.setText(this.getAddOrModify() + " Product");

        if(this.getAddOrModify().equals("Modify")){
            Inventory inventory = this.getInventory();
            Product product = inventory.lookupProduct(this.getProductRowSelected());
            productIDTextField.setText(String.valueOf(product.getProductId()));
            productNameTextField.setText(product.getName());
            productInventoryTextField.setText(String.valueOf(product.getInStock()));
            productPriceTextField.setText(String.valueOf(product.getPrice()));
            productMaxTextField.setText(String.valueOf(product.getMax()));
            productMinTextField.setText(String.valueOf(product.getMin()));
            ListViewUtil listViewUtil = new ListViewUtil();
            System.out.println("Look now");
            listViewUtil.fillOutListViewSection(getProductDeleteListViews(), getProductPartsData());
        }
    }

    //++++ Product Controls ++++
    @FXML
    private Label productHeaderLabel;
    @FXML
    private Button productSaveButton;
    @FXML
    private Button productCancelButton;
    @FXML
    private TextField productIDTextField;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField productInventoryTextField;
    @FXML
    private TextField productPriceTextField;
    @FXML
    private TextField productMaxTextField;
    @FXML
    private TextField productMinTextField;

    // Delete List views
    @FXML
    ListView productDeletePartIDListView;
    @FXML
    ListView productDeletePartNameListView;
    @FXML
    ListView productDeleteInventoryLevelListView;
    @FXML
    ListView productDeletePricePerUnitListView;

    public ListView[] getProductDeleteListViews(){
        ListView[] listViews = {
                productDeletePartIDListView,
                productDeletePartNameListView,
                productDeleteInventoryLevelListView,
                productDeletePricePerUnitListView,
        };
        return listViews;
    }

    public ArrayList[] getProductPartsData(){
        Inventory inventory = this.getInventory();
        Product product = inventory.lookupProduct(this.getProductRowSelected());
        ArrayList[] arrayLists = {
                product.getAllPartsIDs(),
                product.getAllPartsNames(),
                product.getAllPartsInStocks(),
                product.getAllPartsPrices()
        };
        return arrayLists;
    }

    //++++ Product functions
    @FXML
    public void onSaveButtonClicked() throws IOException {
        Inventory inventory = this.getInventory();
        Product product = new Product();
        product.setProductId(getTextFieldInt(productIDTextField));
        product.setName(productNameTextField.getText());
        product.setInStock(getTextFieldInt(productInventoryTextField));
        product.setPrice(getTextFieldDouble(productPriceTextField));
        product.setMax(getTextFieldInt(productMaxTextField));
        product.setMin(getTextFieldInt(productMinTextField));

        if(this.getAddOrModify().equals("Modify")){
            inventory.addProduct(this.getProductRowSelected(), product);
        }else{
            inventory.addProduct(product);
        }
        this.setInventory(inventory);
        changeWindowTo(1, getStage(productNameTextField));
    }
}
