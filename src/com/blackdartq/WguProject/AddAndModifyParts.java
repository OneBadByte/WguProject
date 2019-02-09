package com.blackdartq.WguProject;

import com.blackdartq.WguProject.JavaResources.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AddAndModifyParts extends Util {

    public void initialize() {
        Inventory inventory = this.getInventory();
        partsLabel.setText(this.getAddOrModifyPart() + " Parts");

        if(this.getAddOrModifyPart().equals("Modify")){
            setTextFields(inventory.lookupParts(this.getPartsRowSelected()));
        }
    }

    private void setTextFields(Parts part){
        // sets normal part text fields
        partsIDTextField.setText(String.valueOf(part.getPartID()));
        partsNameTextField.setText(part.getName());
        partsInventoryTextField.setText(String.valueOf(part.getInStock()));
        partsPriceCostTextField.setText(String.valueOf(part.getPrice()));
        partsMaxTextField.setText(String.valueOf(part.getMax()));
        partsMinTextField.setText(String.valueOf(part.getMin()));

        // sets the special class part text fields
        if(part instanceof Inhouse){
            partsInHouseRadioButton.setSelected(true);
            partsOutsourcedRadioButton.setSelected(false);
            partsLabel2.setText("Machine ID");
            Inhouse test = (Inhouse) part;
            partsCompanyNameOrMachineIDTextField.setText(String.valueOf(test.getMachineID()));
        }else if(part instanceof Outsourced){
            partsInHouseRadioButton.setSelected(false);
            partsOutsourcedRadioButton.setSelected(true);
            partsLabel2.setText("Company Name");
            Outsourced test = (Outsourced) part;
            partsCompanyNameOrMachineIDTextField.setText(String.valueOf(test.getCompanyName()));
        }
    }

    //++++ Parts Controls ++++
    @FXML
    private Label partsLabel;
    @FXML
    private Label partsLabel2;
    @FXML
    private Button partsSaveButton;
    @FXML
    private Button partsCancelButton;
    @FXML
    private RadioButton partsInHouseRadioButton;
    @FXML
    private RadioButton partsOutsourcedRadioButton;
    @FXML
    private TextField partsIDTextField;
    @FXML
    private TextField partsNameTextField;
    @FXML
    private TextField partsInventoryTextField;
    @FXML
    private TextField partsPriceCostTextField;
    @FXML
    private TextField partsMaxTextField;
    @FXML
    private TextField partsMinTextField;
    @FXML
    private TextField partsCompanyNameOrMachineIDTextField;


    //++++ Part functions
    @FXML
    public void unClickOtherRadioButton(MouseEvent event) {
        String radioButtonName = event.getSource().toString().split("\\'")[1];
        radioButtonName = radioButtonName.replace("\\'", "").trim();
        if (radioButtonName.equals("In-House")) {
            this.partsOutsourcedRadioButton.setSelected(false);
            this.partsLabel2.setText("Machine ID");
        } else {
            this.partsInHouseRadioButton.setSelected(false);
            this.partsLabel2.setText("Company Name");
        }
    }

    public void saveInhouseToInventoryOrProduct(boolean saveToInventory, Inhouse inhouse) throws IOException {
        Inventory inventory = this.getInventory();
        if(this.getAddOrModifyPart().equals("Modify")){
            if(saveToInventory){
                // sets the part to the row being modified in inventory
                inventory.removeParts(this.getPartsRowSelected());
                inventory.addParts(this.getPartsRowSelected(), inhouse);
            }else{
                // sets the part to the row being modified in product
                Product product = inventory.lookupProduct(this.getProductRowSelected());
                product.addAssociatedPart(this.getPartsRowSelected(), inhouse);
                inventory.addProduct(this.getProductRowSelected(), product);
            }
        }else{
            if(saveToInventory){
                // sets the part to the row in inventory
                inventory.addParts(inhouse);
            }else {
                // sets the part to the row in product
                Product product = new Product();
                product.addAssociatedPart(inhouse);
                inventory.addProduct(product);
            }
        }

        // Saves state and changes window
        this.setInventory(inventory);
//        changeWindowTo(this.getWindowToSwitchTo(), getStage(partsSaveButton));
        backToOtherWindow(partsCancelButton);
    }

    public void saveOutsourcedToInventoryOrProduct(boolean saveToInventory, Outsourced outsourced) throws IOException {
        Inventory inventory = this.getInventory();
        if(saveToInventory){
            if (this.getAddOrModifyPart().equals("Modify")) {
                    // sets the part to the row being modified in inventory
                    inventory.removeParts(this.getPartsRowSelected());
                    inventory.addParts(this.getPartsRowSelected(), outsourced);
            }else{
                inventory.addParts(outsourced);
            }
        }else{
//            if (this.getAddOrModifyProduct().equals("Modify")) {
            Product product = inventory.lookupProduct(this.getProductRowSelected());
            product.addAssociatedPart(outsourced);
            inventory.addProduct(this.getProductRowSelected(), product);
//            }else{
//                Product product = inventory.lookupProduct(this.getProductRowSelected());
//                product.addAssociatedPart(outsourced);
//                inventory.addProduct(this.getProductRowSelected(), product);

            }
        // Saves state and changes window
        this.setInventory(inventory);
//        changeWindowTo(this.getWindowToSwitchTo(), getStage(partsSaveButton));
        backToOtherWindow(partsCancelButton);
        }

//        if (this.getAddOrModifyPart().equals("Modify")) {
//            if (saveToInventory) {
//                // sets the part to the row being modified in inventory
//                inventory.removeParts(this.getPartsRowSelected());
//                inventory.addParts(this.getPartsRowSelected(), outsourced);
//            } else {
//                // sets the part to the row being modified in product
//                Product product = inventory.lookupProduct(this.getProductRowSelected());
//                product.addAssociatedPart(this.getPartsRowSelected(), outsourced);
//                inventory.addProduct(this.getProductRowSelected(), product);
//            }
//        } else {
//            if (saveToInventory) {
//                // sets the part to the row in inventory
//                inventory.addParts(outsourced);
//            } else {
//                // sets the part to the row in product
//                Product product = new Product();
//                product.addAssociatedPart(outsourced);
//                inventory.addProduct(product);
//            }
//        }
//        // Saves state and changes window
//        this.setInventory(inventory);
////        changeWindowTo(this.getWindowToSwitchTo(), getStage(partsSaveButton));
//        backToOtherWindow(partsCancelButton);
//    }

    @FXML
    public void onSaveButtonClick(MouseEvent event) throws IOException {
        // checks which radio button is selected
        if (partsInHouseRadioButton.isSelected()) {
            Inhouse inhouse = new Inhouse();

            // gets the text from the text fields and parses them to type
            inhouse.setMachineID(getTextFieldInt(partsCompanyNameOrMachineIDTextField));
            inhouse.setPartID(getTextFieldInt(partsIDTextField));
            inhouse.setName(partsNameTextField.getText());
            inhouse.setInStock(getTextFieldInt(partsInventoryTextField));
            inhouse.setPrice(getTextFieldDouble(partsPriceCostTextField));
            inhouse.setMax(getTextFieldInt(partsMaxTextField));
            inhouse.setMin(getTextFieldInt(partsMinTextField));

            // saves in house to product or inventory based on states modify product
            saveInhouseToInventoryOrProduct(this.isModifyingProduct(), inhouse);
        } else {
            Outsourced outsourced = new Outsourced();

            // gets the text from the text fields and parses them to type
            outsourced.setCompanyName(partsCompanyNameOrMachineIDTextField.getText());
            outsourced.setPartID(getTextFieldInt(partsIDTextField));
            outsourced.setName(partsNameTextField.getText());
            outsourced.setInStock(getTextFieldInt(partsInventoryTextField));
            outsourced.setPrice(getTextFieldDouble(partsPriceCostTextField));
            outsourced.setMax(getTextFieldInt(partsMaxTextField));
            outsourced.setMin(getTextFieldInt(partsMinTextField));

            // saves in house to product or inventory based on states modify product
            saveOutsourcedToInventoryOrProduct(this.isModifyingProduct(), outsourced);
        }
    }
}
