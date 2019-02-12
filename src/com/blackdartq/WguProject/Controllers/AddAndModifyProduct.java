package com.blackdartq.WguProject.Controllers;

import com.blackdartq.WguProject.DataManagementResources.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class AddAndModifyProduct extends ControllerUtil {

    // holds the data of the product from inventory
    private Product productHolder = new Product();

    // will hold the indexes of the part being deleted
    private ArrayList<Integer> deleteHolder = new ArrayList<>();

    public void initialize() {
        // changes the header text to Add or Modify
        productHeaderLabel.setText(this.getAddOrModifyPart() + " Product");

        if (this.getAddOrModifyProduct().equals("Modify")) {
            // assigns the product row selected
            Inventory inventory = this.getInventory();
            productHolder = inventory.lookupProduct(this.getProductRowSelected());

            // sets all the text fields to that products data
            productIDTextField.setText(String.valueOf(productHolder.getProductId()));
            productNameTextField.setText(productHolder.getName());
            productInventoryTextField.setText(String.valueOf(productHolder.getInStock()));
            productPriceTextField.setText(String.valueOf(productHolder.getPrice()));
            productMaxTextField.setText(String.valueOf(productHolder.getMax()));
            productMinTextField.setText(String.valueOf(productHolder.getMin()));

            // fills out the product list views
            new ListViewUtil().fillOutListViewSection(getProductDeleteListViews(), getProductPartsData());
        }else{
            // sets the productSaveButton to disabled by default when adding a product
            productSaveButton.setDisable(true);
            this.setProductRowSelected(this.getInventory().getProductSize());
            productIDTextField.setText(String.valueOf(this.getInventory().getRandomProductID()));
        }
        productIDTextField.setDisable(true);
    }
    //++++++ Product Controls ++++++++++++
    // Labels
    @FXML
    private Label productHeaderLabel;

    // Buttons
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

    // TextFields
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

    // List views
    @FXML
    ListView productDeletePartIDListView;
    @FXML
    ListView productDeletePartNameListView;
    @FXML
    ListView productDeleteInventoryLevelListView;
    @FXML
    ListView productDeletePricePerUnitListView;
    @FXML
    ListView productAddPartIDListView;
    @FXML
    ListView productAddPartNameListView;
    @FXML
    ListView productAddInventoryLevelListView;
    @FXML
    ListView productAddPricePerUnitListView;


    // functions to get an ArrayList of controls

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
    //------------------------------------

    //++++++ Product FXML Functions ++++++
    @FXML
    public void checkForCompleteData(){
        // checks all of the products textFields for data
        boolean setDisable = false;
        for(TextField tf : getProductTextFields()){
            if(tf.getText().equals("")){
                // if nothing entered will change background color to red
                tf.setStyle("-fx-background-color: #FFC6C6; -fx-border-color: grey");
                setDisable = true;
            }else{
                // if nothing entered will change background color to white
                tf.setStyle("-fx-background-color: white; -fx-border-color: grey");
            }
        }
        // changes the setDisable value on the save button
        productSaveButton.setDisable(setDisable);

        // checks that min max inventory has the right data values
        checkMinMaxInventory();
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
        deleteHolder.add(this.getPartsRowSelected());
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.fillOutListViewSection(getProductDeleteListViews(), getProductPartsData(), deleteHolder);
    }

    @FXML
    public void selectEntireDeletePartsRow(MouseEvent event) {
        int rowSelected = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
        if(!this.isLockSearch()){
            this.setPartsRowSelected(rowSelected);
        }
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.selectEntireRow(getProductDeleteListViews(), rowSelected);
    }

    @FXML
    public void onProductAddButtonClicked(MouseEvent event) throws IOException{
        this.setAddOrModifyPart(getSourceString(event.getSource().toString()));
        saveDataToInventory();
        this.setAddOrModifyProduct("Modify");

        // sets the window to switch back to AddAndModifyProduct
        this.setWindowToSwitchTo(3);
        changeSceneTo(2, getStage(productAddButton));
    }

    @FXML
    public void onSaveButtonClicked() throws IOException {
        saveDataToInventory();
        changeSceneTo(1, getStage(productNameTextField));
    }

    @FXML
    public void onCancelButtonPressed() throws IOException {
        productHolder = this.getInventory().lookupProduct(this.getProductRowSelected());
        backToOtherWindow(productAddButton);

    }

    //------------------------------------


    //++++++ Product Helper Functions ++++++
    public void checkMinMaxInventory(){
        boolean setDisable = false;

        // checks if textField has text in it
        if(productMaxTextField.getText().equals("")
                || productMinTextField.getText().equals("")
                || productInventoryTextField.getText().equals("")){
            return;
        }

        // forces text to be integer and create variables
        int inventory = Integer.parseInt(productInventoryTextField.getText());
        int max = Integer.parseInt(productMaxTextField.getText());
        int min = Integer.parseInt(productMinTextField.getText());

        // checks if min greater than max and changes background to red
        if(max <= min){
            productMaxTextField.setStyle("-fx-background-color: #FFC6C6; -fx-border-color: grey");
            productMinTextField.setStyle("-fx-background-color: #FFC6C6; -fx-border-color: grey");
            setDisable = true;
        }else{
            // sets background to white
            productMaxTextField.setStyle("-fx-background-color: white; -fx-border-color: grey");
            productMinTextField.setStyle("-fx-background-color: white; -fx-border-color: grey");
        }

        // checks if inventory is less than min and changes background to red
        if(inventory < min){
            productInventoryTextField.setStyle("-fx-background-color: #FFC6C6; -fx-border-color: grey");
            productMinTextField.setStyle("-fx-background-color: #FFC6C6; -fx-border-color: grey");
            setDisable = true;
        }else{
            // sets background to white
            productInventoryTextField.setStyle("-fx-background-color: white; -fx-border-color: grey");
        }

        // checks if inventory is greater than max and changes background to red
        if(inventory > max){
            productInventoryTextField.setStyle("-fx-background-color: #FFC6C6; -fx-border-color: grey");
            productMaxTextField.setStyle("-fx-background-color: #FFC6C6; -fx-border-color: grey");
            setDisable = true;
        }else{
            // sets background to white
            productInventoryTextField.setStyle("-fx-background-color: white; -fx-border-color: grey");
        }

        // sets the disable value of button
        productSaveButton.setDisable(setDisable);
    }

    public void saveDataToInventory() {
        // sets the data for the new product or modifies product
        // releases the lock search
        this.setLockSearch(false);

        // sets products values using the text fields
        productHolder.setProductId(getTextFieldInt(productIDTextField));
        productHolder.setName(productNameTextField.getText());
        productHolder.setInStock(getTextFieldInt(productInventoryTextField));
        productHolder.setPrice(getTextFieldDouble(productPriceTextField));
        productHolder.setMax(getTextFieldInt(productMaxTextField));
        productHolder.setMin(getTextFieldInt(productMinTextField));

        // uses deleteHolders values as the indexes to remove values from productData
        for(int i : deleteHolder){
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
    //------------------------------------
}
