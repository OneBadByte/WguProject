package com.blackdartq.WguProject;

import com.blackdartq.WguProject.JavaResources.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class AddAndModifyProduct extends Util {

    private ArrayList<Parts> productPartsHolder = new ArrayList<>();

    public void initialize() {
        productHeaderLabel.setText(this.getAddOrModifyPart() + " Product");

        if (this.getAddOrModifyProduct().equals("Modify")) {
            Inventory inventory = this.getInventory();
            productPartsHolder = inventory.lookupProduct(this.getProductRowSelected()).getAllAssociatedParts();
            System.out.println("here " + productPartsHolder);
            Product product = inventory.lookupProduct(this.getProductRowSelected());
            productIDTextField.setText(String.valueOf(product.getProductId()));
            productNameTextField.setText(product.getName());
            productInventoryTextField.setText(String.valueOf(product.getInStock()));
            productPriceTextField.setText(String.valueOf(product.getPrice()));
            productMaxTextField.setText(String.valueOf(product.getMax()));
            productMinTextField.setText(String.valueOf(product.getMin()));
            ListViewUtil listViewUtil = new ListViewUtil();
            listViewUtil.fillOutListViewSection(getProductDeleteListViews(), getProductPartsData());
        }else{
            this.setProductRowSelected(this.getInventory().getProductSize()+1);
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
    private Button productAddButton;
    @FXML
    private Button productDeleteButton;
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

    public ListView[] getProductDeleteListViews() {
        ListView[] listViews = {
                productDeletePartIDListView,
                productDeletePartNameListView,
                productDeleteInventoryLevelListView,
                productDeletePricePerUnitListView,
        };
        return listViews;
    }

    public ArrayList[] getProductPartsData() {
        Inventory inventory = this.getInventory();
        Product product = inventory.lookupProduct(this.getProductRowSelected());
        System.out.println(product.getAllPartsIDs());
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
    public void deletePartsRow() {
        Inventory inventory = this.getInventory();
        Product product = inventory.lookupProduct(this.getProductRowSelected());
        product.removeAssoicatedPart(this.getPartsRowSelected());
        inventory.addProduct(this.getProductRowSelected(), product);
        this.setInventory(inventory);
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.fillOutListViewSection(getProductDeleteListViews(), getProductPartsData());

    }

    @FXML
    public void selectEntirePartsRow(MouseEvent event) {
        int rowSelected = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
        this.setPartsRowSelected(rowSelected);
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.selectEntireRow(getProductDeleteListViews(), rowSelected);
    }

    @FXML
    public void onProductAddButtonClicked(MouseEvent event) throws IOException, InterruptedException {
        this.setAddOrModifyPart(getSourceString(event.getSource().toString()));
        saveData();
        this.setWindowToSwitchTo(3);
        changeWindowTo(2, getStage(productAddButton));
    }

    public void saveData() {
        // sets the data for the new product
        Product product = new Product();
        product.setProductId(getTextFieldInt(productIDTextField));
        product.setName(productNameTextField.getText());
        product.setInStock(getTextFieldInt(productInventoryTextField));
        product.setPrice(getTextFieldDouble(productPriceTextField));
        product.setMax(getTextFieldInt(productMaxTextField));
        product.setMin(getTextFieldInt(productMinTextField));
        // adds all parts to the new product
        for (Parts part : productPartsHolder) {
            product.addAssociatedPart(part);
        }
        // checks if the products being modified and saves to inventory
        Inventory inventory = this.getInventory();
        if (this.getAddOrModifyProduct().equals("Modify")) {
            inventory.addProduct(this.getProductRowSelected(), product);
        } else {
            inventory.addProduct(product);
        }
        this.setInventory(inventory);

    }

    @FXML
    public void onSaveButtonClicked() throws IOException {
        saveData();
        changeWindowTo(1, getStage(productNameTextField));
    }
}
