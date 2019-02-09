package com.blackdartq.WguProject;

import com.blackdartq.WguProject.JavaResources.Inventory;
import com.blackdartq.WguProject.JavaResources.Parts;
import com.blackdartq.WguProject.JavaResources.Product;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class MainWindowController extends Util {

    public void initialize() {
        Inventory inventory = this.getInventory();
        if (inventory.getPartsSize() == 0) {
            inventory.createTestPartsData();
            inventory.createTestProductData();
        }
        this.setInventory(inventory);

        fillOutPartsListView();
        fillOutProductListView();

    }

    //++++ Main Window Controls ++++
    @FXML
    Button mainExitButton;

    // Main Parts Id's
    @FXML
    Button mainPartsAddButton;
    @FXML
    Button mainPartsModifyButton;
    @FXML
    Button mainPartsDeleteButton;
    @FXML
    Button mainPartsSearchButton;
    @FXML
    TextField mainPartsSearchTextField;
    @FXML
    public ListView mainPartsIDListView;
    @FXML
    public ListView mainPartsNameListView;
    @FXML
    public ListView mainPartsInventoryLevelListView;
    @FXML
    public ListView mainPartsPerUnitListView;

    public ListView[] getPartsListViews(){
        ListView[] listViews = {
                mainPartsIDListView,
                mainPartsNameListView,
                mainPartsInventoryLevelListView,
                mainPartsPerUnitListView,
        };
        return listViews;
    }

    // Main Product Id's
    @FXML
    Button mainProductsAddButton;
    @FXML
    Button mainProductsModifyButton;
    @FXML
    Button mainProductsDeleteButton;
    @FXML
    Button mainProductsSearchButton;
    @FXML
    public TextField mainProductsSearchTextField;
    @FXML
    public ListView mainProductsIDListView;
    @FXML
    public ListView mainProductsNameListView;
    @FXML
    public ListView mainProductsInventoryLevelListView;
    @FXML
    public ListView mainProductsPerUnitListView;

    public ListView[] getProductListViews(){
        ListView[] listViews = {
                mainProductsIDListView,
                mainProductsNameListView,
                mainProductsInventoryLevelListView,
                mainProductsPerUnitListView,
        };
        return listViews;
    }


    @FXML
    public void onPartsAddOrModifyButtonClicked(MouseEvent event) throws IOException, InterruptedException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.setAddOrModifyPart(getSourceString(event.getSource().toString()));
        this.setWindowToSwitchTo(1);
        changeWindowTo(2, stage);
    }

    @FXML
    public void onProductAddOrModifyButtonClicked(MouseEvent event) throws IOException, InterruptedException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.setAddOrModifyProduct(getSourceString(event.getSource().toString()));
        this.setWindowToSwitchTo(1);
        changeWindowTo(3, stage);
    }

    @FXML
    public void onExitButtonClick() {
        Platform.exit();
    }

    //------------------------------
    //++++ Product Controls ++++


    //--------------------------

//    @FXML
//    public void backToMainWindow(MouseEvent event) throws IOException {
//        Button button = (Button) event.getSource();
//        changeWindowTo(1, getStage(button));
//    }

    //------------------------
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

    @FXML
    public void selectEntirePartsRow(MouseEvent event) {
        int rowSelected = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
        this.setPartsRowSelected(rowSelected);
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.selectEntireRow(getPartsListViews(), rowSelected);
    }

    @FXML
    public void selectEntireProductsRow(MouseEvent event) {
        int rowSelected = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
        this.setProductRowSelected(rowSelected);
        ListViewUtil listViewUtil = new ListViewUtil();
        listViewUtil.selectEntireRow(getProductListViews(), rowSelected);
    }

    @FXML
    public void deletePartsRow() {
        Inventory inventory = this.getInventory();
        inventory.removeParts(this.getPartsRowSelected());
        fillOutPartsListView();
    }

    @FXML
    public void deleteProductsRow() {
        Inventory inventory = this.getInventory();
        inventory.removeProduct(this.getProductRowSelected());
        fillOutProductListView();
    }

    @FXML
    public void searchForPart(MouseEvent event) {
        if (mainPartsSearchTextField.getText().equals("")) {
            fillOutProductListView();
            return;
        }
        int thing = Integer.parseInt(mainPartsSearchTextField.getText());
        Inventory inventory = this.getInventory();
        Parts part = inventory.lookupParts(thing - 1);
        fillOutPartsListView(part);
        this.setPartsRowSelected(thing - 1);
    }

    @FXML
    public void searchForProduct(MouseEvent event) {
        if (mainProductsSearchTextField.getText().equals("")) {
            fillOutProductListView();
            return;
        }
        int thing = Integer.parseInt(mainProductsSearchTextField.getText());
        Inventory inventory = this.getInventory();
        Product product = inventory.lookupProduct(thing - 1);
        fillOutProductListView(product);
        this.setPartsRowSelected(thing - 1);
    }
}

