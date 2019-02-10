package com.blackdartq.WguProject.Controllers;

import com.blackdartq.WguProject.DataManagementResources.Inventory;
import com.blackdartq.WguProject.DataManagementResources.Parts;
import com.blackdartq.WguProject.DataManagementResources.Product;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class MainWindowController extends ControllerUtil {

    public void initialize() {
        // Creates test products and parts of the inventory
        Inventory inventory = this.getInventory();
        if (inventory.getPartsSize() == 0) {
            inventory.createTestPartsData();
            inventory.createTestProductData();
        }
        this.setInventory(inventory);

        fillOutPartsListView();
        fillOutProductListView();

    }

    //+++++ Main Window FXML Controls +++++
    // Buttons
    @FXML
    Button mainExitButton;
    @FXML
    Button mainPartsAddButton;
    @FXML
    Button mainPartsModifyButton;
    @FXML
    Button mainPartsDeleteButton;
    @FXML
    Button mainPartsSearchButton;
    @FXML
    Button mainProductsAddButton;
    @FXML
    Button mainProductsModifyButton;
    @FXML
    Button mainProductsDeleteButton;
    @FXML
    Button mainProductsSearchButton;

    // TextFields
    @FXML
    TextField mainPartsSearchTextField;
    @FXML
    public TextField mainProductsSearchTextField;

    // ListViews
    @FXML
    public ListView mainPartsIDListView;
    @FXML
    public ListView mainPartsNameListView;
    @FXML
    public ListView mainPartsInventoryLevelListView;
    @FXML
    public ListView mainPartsPerUnitListView;
    @FXML
    public ListView mainProductsIDListView;
    @FXML
    public ListView mainProductsNameListView;
    @FXML
    public ListView mainProductsInventoryLevelListView;
    @FXML
    public ListView mainProductsPerUnitListView;
    //--------------------------------------

    //+++++ Main Window FXML Functions +++++
    @FXML
    public void onPartsAddOrModifyButtonClicked(MouseEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.setAddOrModifyPart(getSourceString(event.getSource().toString()));
        this.setModifyingProduct(false);
        this.setWindowToSwitchTo(1);
        changeSceneTo(2, stage);
    }

    @FXML
    public void onProductAddOrModifyButtonClicked(MouseEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.setAddOrModifyProduct(getSourceString(event.getSource().toString()));
        this.setModifyingProduct(true);
        this.setWindowToSwitchTo(1);
        changeSceneTo(3, stage);
    }

    @FXML
    public void onExitButtonClick() {
        Platform.exit();
    }

    @FXML
    public void selectEntirePartsRow(MouseEvent event) {
        int rowSelected = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
        if(!this.isLockSearch()){
            this.setPartsRowSelected(rowSelected);
        }
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.selectEntireRow(getPartsListViews(), rowSelected);
    }

    @FXML
    public void selectEntireProductsRow(MouseEvent event) {
        int rowSelected = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
        if(!this.isLockSearch()){
            this.setProductRowSelected(rowSelected);
        }
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.selectEntireRow(getProductListViews(), rowSelected);
    }

    @FXML
    public void deletePartsRow() {
        Inventory inventory = this.getInventory();
        System.out.println("removing row: " + this.getPartsRowSelected());
        inventory.removeParts(this.getPartsRowSelected());
        fillOutPartsListView();
    }

    @FXML
    public void deleteProductsRow() {
        Inventory inventory = this.getInventory();
        System.out.println("removing row: " + this.getProductRowSelected());
        inventory.removeProduct(this.getProductRowSelected());
        fillOutProductListView();
    }

    @FXML
    public void searchForPart(MouseEvent event) {
        if (mainPartsSearchTextField.getText().equals("")) {
            fillOutProductListView();
            this.setLockSearch(false);
            return;
        }
        this.setLockSearch(true);
        int thing = Integer.parseInt(mainPartsSearchTextField.getText());
        Inventory inventory = this.getInventory();
        Parts part = inventory.lookupParts(thing - 1);
        fillOutPartsListView(part);
        System.out.println(thing);
        this.setPartsRowSelected(thing-1);
        mainPartsSearchTextField.clear();
    }

    @FXML
    public void searchForProduct(MouseEvent event) {
        if (mainProductsSearchTextField.getText().equals("")) {
            fillOutProductListView();
            this.setLockSearch(false);
            return;
        }
        this.setLockSearch(true);
        int thing = Integer.parseInt(mainProductsSearchTextField.getText());
        Inventory inventory = this.getInventory();
        Product product = inventory.lookupProduct(thing - 1);
        fillOutProductListView(product);
        System.out.println(thing);
        this.setPartsRowSelected(thing - 1);
        mainProductsSearchTextField.clear();
    }
    //--------------------------------------

    //+++++ Main Window Helper Functions +++
    public ListView[] getPartsListViews(){
        ListView[] listViews = {
                mainPartsIDListView,
                mainPartsNameListView,
                mainPartsInventoryLevelListView,
                mainPartsPerUnitListView,
        };
        return listViews;
    }

    public ListView[] getProductListViews(){
        ListView[] listViews = {
                mainProductsIDListView,
                mainProductsNameListView,
                mainProductsInventoryLevelListView,
                mainProductsPerUnitListView,
        };
        return listViews;
    }
    public void fillOutProductListView() {
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.fillOutListViewSection(getProductListViews(), getMainProductListViewData());
    }

    public void fillOutProductListView(Product product) {
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.fillOutListView(mainProductsIDListView, product.getProductId());
        listViewUtil.fillOutListView(mainProductsNameListView, product.getName());
        listViewUtil.fillOutListView(mainProductsInventoryLevelListView, product.getInStock());
        listViewUtil.fillOutListView(mainProductsPerUnitListView, product.getPrice());
    }

    public void fillOutPartsListView() {
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.fillOutListViewSection(getPartsListViews(), getMainPartsListViewData());
    }

    public void fillOutPartsListView(Parts part) {
        // fill out list views
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.fillOutListView(mainPartsIDListView, part.getPartID());
        listViewUtil.fillOutListView(mainPartsNameListView, part.getName());
        listViewUtil.fillOutListView(mainPartsInventoryLevelListView, part.getInStock());
        listViewUtil.fillOutListView(mainPartsPerUnitListView, part.getPrice());
    }
    //--------------------------------------

}

