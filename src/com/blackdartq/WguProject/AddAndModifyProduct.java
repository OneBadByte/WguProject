package com.blackdartq.WguProject;

import com.blackdartq.WguProject.JavaResources.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.ArrayList;

public class AddAndModifyProduct extends Util {

    private Product productHolder = new Product();
    private ArrayList<Integer> deleteHolder = new ArrayList<>();

    public void initialize() {
        productHeaderLabel.setText(this.getAddOrModifyPart() + " Product");

        if (this.getAddOrModifyProduct().equals("Modify")) {
            Inventory inventory = this.getInventory();
            // assigns the product row selected
            productHolder = inventory.lookupProduct(this.getProductRowSelected());

            // sets all the text fields to that products data
            productIDTextField.setText(String.valueOf(productHolder.getProductId()));
            productNameTextField.setText(productHolder.getName());
            productInventoryTextField.setText(String.valueOf(productHolder.getInStock()));
            productPriceTextField.setText(String.valueOf(productHolder.getPrice()));
            productMaxTextField.setText(String.valueOf(productHolder.getMax()));
            productMinTextField.setText(String.valueOf(productHolder.getMin()));
            ListViewUtil listViewUtil = new ListViewUtil();
            listViewUtil.fillOutListViewSection(getProductDeleteListViews(), getProductPartsData());
        }else{
            productSaveButton.setDisable(true);
            this.setProductRowSelected(this.getInventory().getProductSize());
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
    private Button productSearchButton;

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
    @FXML
    private TextField productSearchTextField;

    public TextField[] getProductTextFields() {
        TextField[] textFields = {
                productIDTextField,
                productNameTextField,
                productInventoryTextField,
                productPriceTextField,
                productMaxTextField,
                productMinTextField,
        };
        return textFields;
    }

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
        ArrayList[] arrayLists = {
                productHolder.getAllPartsIDs(),
                productHolder.getAllPartsNames(),
                productHolder.getAllPartsInStocks(),
                productHolder.getAllPartsPrices()
        };
        return arrayLists;
    }

    //++++ Product functions ++++

    @FXML
    public void checkForCompleteData(){
        boolean setDisable = false;
        for(TextField tf : getProductTextFields()){
           if(tf.getText().equals("")){
               tf.setStyle("-fx-background-color: #FFC6C6; -fx-border-color: grey");
               setDisable = true;
           }else{
               tf.setStyle("-fx-background-color: white; -fx-border-color: grey");
           }
        }
        productSaveButton.setDisable(setDisable);
    }

    @FXML
    public void searchForPart() {
        String searchText = productSearchTextField.getText();
        if (searchText.equals("")) {
            this.setLockSearch(false);
            return;
        }
        ListViewUtil listViewUtil = new ListViewUtil();
        Parts part = productHolder.lookupAssociatedPart(this.getPartsRowSelected());
        this.setLockSearch(true);
        listViewUtil.fillOutListView(productDeletePartIDListView, part.getPartID());
        listViewUtil.fillOutListView(productDeletePartNameListView, part.getName());
        listViewUtil.fillOutListView(productDeleteInventoryLevelListView, part.getInStock());
        listViewUtil.fillOutListView(productDeletePricePerUnitListView, part.getPrice());
        productSearchTextField.clear();
    }

    @FXML
    public void deletePartsRow() {
        System.out.println("deleting row: " + this.getPartsRowSelected());
        deleteHolder.add(this.getPartsRowSelected());
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.fillOutListViewSection(getProductDeleteListViews(), getProductPartsData(), deleteHolder);

    }

    @FXML
    public void selectEntirePartsRow(MouseEvent event) {
        int rowSelected = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
        if(!this.isLockSearch()){
            this.setPartsRowSelected(rowSelected);
        }
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.selectEntireRow(getProductDeleteListViews(), rowSelected);
    }

    @FXML
    public void onProductAddButtonClicked(MouseEvent event) throws IOException, InterruptedException {
        this.setAddOrModifyPart(getSourceString(event.getSource().toString()));
        saveData();
        this.setAddOrModifyProduct("Modify");
        this.setWindowToSwitchTo(3);
        changeWindowTo(2, getStage(productAddButton));
    }

    public void saveData() {
        // sets the data for the new product
        this.setLockSearch(false);
        productHolder.setProductId(getTextFieldInt(productIDTextField));
        productHolder.setName(productNameTextField.getText());
        productHolder.setInStock(getTextFieldInt(productInventoryTextField));
        productHolder.setPrice(getTextFieldDouble(productPriceTextField));
        productHolder.setMax(getTextFieldInt(productMaxTextField));
        productHolder.setMin(getTextFieldInt(productMinTextField));
        for(int i : deleteHolder){
            System.out.println("removing " + i);
            productHolder.removeAssociatedPart(i);
        }

        // checks if the products being modified and saves to inventory
        Inventory inventory = this.getInventory();
        if (this.getAddOrModifyProduct().equals("Modify")) {
            inventory.addProduct(this.getProductRowSelected(), productHolder);
        } else {
            inventory.addProduct(productHolder);
        }
        this.setInventory(inventory);

    }

    @FXML
    public void onSaveButtonClicked() throws IOException {
        saveData();
        changeWindowTo(1, getStage(productNameTextField));
    }

    @FXML
    public void onCancelButtonPressed() throws IOException {
        productHolder = this.getInventory().lookupProduct(this.getProductRowSelected());
        backToOtherWindow(productAddButton);

    }
}
