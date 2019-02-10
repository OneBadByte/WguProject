package com.blackdartq.WguProject.Controllers;

import com.blackdartq.WguProject.DataManagementResources.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AddAndModifyParts extends ControllerUtil {

    // intialize gets run when this class is created
    public void initialize() {
        Inventory inventory = this.getInventory();
        // Changes the Header text to Add or Modify Parts
        partsLabel.setText(this.getAddOrModifyPart() + " Parts");

        if(this.getAddOrModifyPart().equals("Modify")){
            // sets all the text fields to the part being looked up
            setTextFields(inventory.lookupParts(this.getPartsRowSelected()));
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


    //++++ Part fxml functions
    @FXML
    public void unClickOtherRadioButton(MouseEvent event) {
        // gets the text at the end of the button
        String radioButtonName = event.getSource().toString().split("\\'")[1];
        radioButtonName = radioButtonName.replace("\\'", "").trim();

        // unselects the opposite radio buttons
        if (radioButtonName.equals("In-House")) {
            this.partsOutsourcedRadioButton.setSelected(false);
            this.partsLabel2.setText("Machine ID");
        } else {
            this.partsInHouseRadioButton.setSelected(false);
            this.partsLabel2.setText("Company Name");
        }
    }

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
    //------------------------

    //++++ Part helper functions

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
            // changes the radio button to either in house or outsourced
            partsLabel2.setText("Machine ID");
            partsInHouseRadioButton.setSelected(true);
            partsOutsourcedRadioButton.setSelected(false);

            Inhouse inhouse = (Inhouse) part;
            partsCompanyNameOrMachineIDTextField.setText(String.valueOf(inhouse.getMachineID()));

        }else if(part instanceof Outsourced){
            // changes the radio button to either in house or outsourced
            partsLabel2.setText("Company Name");
            partsInHouseRadioButton.setSelected(false);
            partsOutsourcedRadioButton.setSelected(true);

            Outsourced outsourced = (Outsourced) part;
            partsCompanyNameOrMachineIDTextField.setText(String.valueOf(outsourced.getCompanyName()));
        }
    }

    public void saveInhouseToInventoryOrProduct(boolean saveToProduct, Inhouse inhouse) throws IOException {
        Inventory inventory = this.getInventory();

        // checks to see if saving to inventory or product
        if (!saveToProduct) {
            // checks if inventorys parts are being added or modified
            if (this.getAddOrModifyPart().equals("Modify")) {

                // sets the part to the row being modified in inventory
                inventory.removeParts(this.getPartsRowSelected());
                inventory.addParts(this.getPartsRowSelected(), inhouse);
            } else {
                inventory.addParts(inhouse);
            }
        } else {
            Product product = inventory.lookupProduct(this.getProductRowSelected());
            product.addAssociatedPart(inhouse);
            inventory.addProduct(this.getProductRowSelected(), product);
            // Saves state and changes window
        }
        this.setInventory(inventory);
        backToOtherWindow(partsCancelButton);
    }

    public void saveOutsourcedToInventoryOrProduct(boolean saveToProduct, Outsourced outsourced) throws IOException {
        Inventory inventory = this.getInventory();
        if (!saveToProduct) {
            if (this.getAddOrModifyPart().equals("Modify")) {
                // sets the part to the row being modified in inventory
                inventory.removeParts(this.getPartsRowSelected());
                inventory.addParts(this.getPartsRowSelected(), outsourced);
            } else {
                inventory.addParts(outsourced);
            }
        } else {
            Product product = inventory.lookupProduct(this.getProductRowSelected());
            product.addAssociatedPart(outsourced);
            inventory.addProduct(this.getProductRowSelected(), product);
            // Saves state and changes window
        }
        this.setInventory(inventory);
        backToOtherWindow(partsCancelButton);
    }
}
