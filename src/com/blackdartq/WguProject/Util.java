package com.blackdartq.WguProject;

import com.blackdartq.WguProject.JavaResources.Inventory;
import javafx.collections.ObservableList;
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

    private Inventory inventory = new Inventory();
    private String addOrModify = "Add";
    private int partsRowSelected = 0;
    private int productRowSelected = 0;

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

    public String getAddOrModify() {
        return addOrModify;
    }

    public void setAddOrModify(String addOrModify) {
        this.addOrModify = addOrModify;
    }

    public void setAll(State state){
        this.setInventory(state.inventory);
        this.setAddOrModify(state.addOrModify);
        this.setPartsRowSelected(state.getPartsRowSelected());
    }

    public State getState(){
        return this;
    }
}

abstract class Util extends State {

    private String mainWindow = "MainWindow.fxml";
    private String addAndModifyParts = "AddAndModifyParts.fxml";
    private String addAndModifyProduct = "AddAndModifyProduct.fxml";

    public void changeWindowTo(int windowChoice, Stage stage) throws IOException {
        Parent parent;
        Scene scene;
        FXMLLoader loader;
        switch (windowChoice){
            case 2:
                loader = new FXMLLoader(getClass().getResource(this.addAndModifyParts));
                AddAndModifyParts control2 = new AddAndModifyParts();
                control2.setAll(this.getState());
                loader.setController(control2);
                parent = loader.load();
                scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();
                break;
            case 3:
                loader = new FXMLLoader(getClass().getResource(this.addAndModifyProduct));
                AddAndModifyProduct control3 = new AddAndModifyProduct();
                control3.setAll(this.getState());
                loader.setController(control3);
                parent = loader.load();
                scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();
                break;

            case 1:
            default:
                loader = new FXMLLoader(getClass().getResource(this.mainWindow));
                MainWindowController control = new MainWindowController();
                control.setAll(this.getState());
                loader.setController(control);
                parent = loader.load();
                scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();
                control.fillOutPartsListView();
                break;
        }

    }

    public Stage getStage(Control control){
        Stage stage = (Stage) control.getScene().getWindow();
        return stage;
    }

    @FXML
    public void backToMainWindow(MouseEvent event) throws IOException {
        Button button = (Button) event.getSource();
        changeWindowTo(1, getStage(button));
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

    public void fillOutListView(ListView listView, ArrayList arrayList){
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        System.out.println(arrayList);
        listView.getItems().setAll(arrayList);
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


//    public void fillOutListView(ListView listView, int integer){
//        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        listView.getItems().setAll(integer);
//    }
//
//    public void fillOutListView(ListView listView, String string){
//        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        listView.getItems().setAll(string);
//    }
//
//    public void fillOutListView(ListView listView, double doub){
//        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        listView.getItems().setAll(doub);
//    }

}

