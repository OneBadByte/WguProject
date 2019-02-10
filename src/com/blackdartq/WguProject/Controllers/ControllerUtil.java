package com.blackdartq.WguProject.Controllers;

import com.blackdartq.WguProject.DataManagementResources.Inventory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

abstract class State{

    // Holds all the data for the entire project
    private Inventory inventory = new Inventory();

    // Controls if adding or modifing product and/or part
    private String addOrModifyPart = "Add";
    private String addOrModifyProduct = "Add";

    /* gets the rows selected in the list views for the part
     * used to get the data from the states inventory
     */
    private int partsRowSelected = 0;
    private int productRowSelected = 1;

    // checks if modifing product and if not uses the inventory
    private boolean modifyingProduct = false;

    /* controls the window to switch to
    * 1 main window
    * 2 parts window
    * 3 products window
     */
    private int windowToSwitchTo = 1;

    /* locks the row selected so that when a row is selected in the listview
     * it doesn't change the row being edited
     */

    // Getters and Setters for variables above
    private boolean lockSearch = false;

    public boolean isLockSearch() {
        return lockSearch;
    }

    public void setLockSearch(boolean lockSearch) {
        this.lockSearch = lockSearch;
    }

    public String getAddOrModifyProduct() {
        return addOrModifyProduct;
    }

    public void setAddOrModifyProduct(String addOrModifyProduct) {
        this.addOrModifyProduct = addOrModifyProduct;
    }

    public int getWindowToSwitchTo() {
        return windowToSwitchTo;
    }

    public void setWindowToSwitchTo(int windowToSwitchTo) {
        this.windowToSwitchTo = windowToSwitchTo;
    }

    public boolean isModifyingProduct() {
        return modifyingProduct;
    }

    public void setModifyingProduct(boolean modifyingProduct) {
        this.modifyingProduct = modifyingProduct;
    }

    public int getProductRowSelected() {
        return productRowSelected;
    }

    public void setProductRowSelected(int productRowSelected) {
        this.productRowSelected = productRowSelected;
    }

    public int getPartsRowSelected() {
        return partsRowSelected;
    }

    public void setPartsRowSelected(int partsRowSelected) {
        this.partsRowSelected = partsRowSelected;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getAddOrModifyPart() {
        return addOrModifyPart;
    }

    public void setAddOrModifyPart(String addOrModifyPart) {
        this.addOrModifyPart = addOrModifyPart;
    }

    // used to transfer state to another controller
    public void setAll(State state){
        this.setInventory(state.inventory);
        this.setAddOrModifyPart(state.addOrModifyPart);
        this.setPartsRowSelected(state.getPartsRowSelected());
        this.addOrModifyProduct = state.addOrModifyProduct;
        this.productRowSelected = state.productRowSelected;
        this.modifyingProduct = state.modifyingProduct;
        this.windowToSwitchTo = state.windowToSwitchTo;
    }

    // used to get the state from another controller
    public State getState(){
        return this;
    }
}

abstract class ControllerUtil extends State {

    final String mainWindow = "../FXML/MainWindow.fxml";
    final String addAndModifyParts = "../FXML/AddAndModifyParts.fxml";
    final String addAndModifyProduct = "../FXML/AddAndModifyProduct.fxml";

    public void createStageAndSwitchScene(Stage stage, FXMLLoader loader) throws IOException {
        Parent parent;
        Scene scene;
        parent = loader.load();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void changeSceneTo(int windowChoice, Stage stage) throws IOException {
        FXMLLoader loader;
        switch (windowChoice){
            case 2:
                AddAndModifyParts control2 = new AddAndModifyParts();
                loader = new FXMLLoader(getClass().getResource(this.addAndModifyParts));
                control2.setAll(this.getState());
                loader.setController(control2);
                createStageAndSwitchScene(stage, loader);
                break;
            case 3:
                loader = new FXMLLoader(getClass().getResource(this.addAndModifyProduct));
                AddAndModifyProduct control3 = new AddAndModifyProduct();
                control3.setAll(this.getState());
                loader.setController(control3);
                createStageAndSwitchScene(stage, loader);
                break;
            case 1:
            default:
                loader = new FXMLLoader(getClass().getResource(this.mainWindow));
                MainWindowController control = new MainWindowController();
                control.setAll(this.getState());
                loader.setController(control);
                createStageAndSwitchScene(stage, loader);
                break;
        }
    }

    public Stage getStage(Control control){
        Stage stage = (Stage) control.getScene().getWindow();
        return stage;
    }

    @FXML
    public void backToOtherWindow(MouseEvent event) throws IOException {
        Button button = (Button) event.getSource();
        int placeHolderWindowValue = this.getWindowToSwitchTo();
        this.setWindowToSwitchTo(1);
        changeSceneTo(placeHolderWindowValue, getStage(button));
    }

    @FXML
    public void backToOtherWindow(Control control) throws IOException {
        int placeHolderWindowValue = this.getWindowToSwitchTo();
        this.setWindowToSwitchTo(1);
        changeSceneTo(placeHolderWindowValue, getStage(control));
    }

    public String getSourceString(String eventSource){
        eventSource = eventSource.split("\\'")[1];
        eventSource = eventSource.replace("\\'", "").trim();
        return eventSource;
    }

    public int getTextFieldInt(TextField textField){
        if(textField.getText().equals("")){
            textField.setText("0");
        }
       return Integer.parseInt(textField.getText());
    }

    public double getTextFieldDouble(TextField textField){
        if(textField.getText().equals("")){
            textField.setText("0.00");
        }
        return Double.parseDouble(textField.getText());
    }

    public ArrayList[] getMainPartsListViewData(){
        ArrayList[] arrayLists = new ArrayList[4];
        arrayLists[0] = this.getInventory().getAllPartsIDs();
        arrayLists[1] = this.getInventory().getAllPartsNames();
        arrayLists[2] = this.getInventory().getAllPartsInStocks();
        arrayLists[3] = this.getInventory().getAllPartsPrices();
        return arrayLists;
    }

    public ArrayList[] getMainProductListViewData(){
        ArrayList[] arrayLists = new ArrayList[4];
        arrayLists[0] = this.getInventory().getAllProductIDs();
        arrayLists[1] = this.getInventory().getAllProductNames();
        arrayLists[2] = this.getInventory().getAllProductInStocks();
        arrayLists[3] = this.getInventory().getAllProductPrices();
        return arrayLists;
    }
}

class ListViewUtil<T>{
    private T t;

    public void fillOutListViewSection(ListView[] listViews, ArrayList[] arrayLists){
        // fill out list views
        fillOutListView(listViews[0], arrayLists[0]);
        fillOutListView(listViews[1], arrayLists[1]);
        fillOutListView(listViews[2], arrayLists[2]);
        fillOutListView(listViews[3], arrayLists[3]);
    }

    public void fillOutListViewSection(ListView[] listViews, ArrayList[] arrayLists, ArrayList excludes){
        // fill out list views
        fillOutListView(listViews[0], arrayLists[0], excludes);
        fillOutListView(listViews[1], arrayLists[1], excludes);
        fillOutListView(listViews[2], arrayLists[2], excludes);
        fillOutListView(listViews[3], arrayLists[3], excludes);
    }

    public void fillOutListView(ListView listView, ArrayList arrayList){
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getItems().setAll(arrayList);
    }

    public void fillOutListView(ListView listView, ArrayList arrayList, ArrayList excludes){
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ArrayList<Object> temp = new ArrayList<>();
        int arrayRow = 0;
        for(int i =0; i < arrayList.size(); i++){
            if(!excludes.contains(i)){
               temp.add(arrayList.get(i)) ;
            }
            arrayRow++;
        }
        listView.getItems().setAll(temp);
    }

    public void fillOutListView(ListView listView, T t){
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getItems().setAll(t);
    }

    public void selectEntireRow(ListView[] listViews, int rowSelected) {
        listViews[0].getSelectionModel().select(rowSelected);
        listViews[1].getSelectionModel().select(rowSelected);
        listViews[2].getSelectionModel().select(rowSelected);
        listViews[3].getSelectionModel().select(rowSelected);
    }
}

