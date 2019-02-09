package com.blackdartq.WguProject;

import com.blackdartq.WguProject.JavaResources.Inhouse;
import com.blackdartq.WguProject.JavaResources.Inventory;
import com.blackdartq.WguProject.JavaResources.Outsourced;
import com.blackdartq.WguProject.JavaResources.Parts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AddAndModifyParts extends Util {

    public void initialize() {
        partsLabel.setText(this.getAddOrModify() + " Parts");

        if(this.getAddOrModify().equals("Modify")){
            Inventory inventory = this.getInventory();
            Parts part = inventory.lookupParts(this.getPartsRowSelected());
            partsIDTextField.setText(String.valueOf(part.getPartID()));
            partsNameTextField.setText(part.getName());
            partsInventoryTextField.setText(String.valueOf(part.getInStock()));
            partsPriceCostTextField.setText(String.valueOf(part.getPrice()));
            partsMaxTextField.setText(String.valueOf(part.getMax()));
            partsMinTextField.setText(String.valueOf(part.getMin()));
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

    @FXML
    public void onSaveButtonClick(MouseEvent event) throws IOException {
        Inventory inventory = this.getInventory();
        if (partsInHouseRadioButton.isSelected()) {
            Inhouse inhouse = new Inhouse();
            inhouse.setMachineID(getTextFieldInt(partsCompanyNameOrMachineIDTextField));
            inhouse.setPartID(getTextFieldInt(partsIDTextField));
            inhouse.setName(partsNameTextField.getText());
            inhouse.setInStock(getTextFieldInt(partsInventoryTextField));
            inhouse.setPrice(getTextFieldDouble(partsPriceCostTextField));
            inhouse.setMax(getTextFieldInt(partsMaxTextField));
            inhouse.setMin(getTextFieldInt(partsMinTextField));
            if(this.getAddOrModify().equals("Modify")){
                inventory.removeParts(this.getPartsRowSelected());
                inventory.addParts(this.getPartsRowSelected(), inhouse);
            }else{
                inventory.addParts(inhouse);
            }
        } else {
            Outsourced outsourced = new Outsourced();
            outsourced.setCompanyName(partsCompanyNameOrMachineIDTextField.getText());
            outsourced.setPartID(getTextFieldInt(partsIDTextField));
            outsourced.setName(partsNameTextField.getText());
            outsourced.setInStock(getTextFieldInt(partsInventoryTextField));
            outsourced.setPrice(getTextFieldDouble(partsPriceCostTextField));
            outsourced.setMax(getTextFieldInt(partsMaxTextField));
            outsourced.setMin(getTextFieldInt(partsMinTextField));
            if(this.getAddOrModify().equals("Modify")){
                inventory.removeParts(this.getPartsRowSelected());
                inventory.addParts(this.getPartsRowSelected(), outsourced);
            }else{
                inventory.addParts(outsourced);
            }
        }
        this.setInventory(inventory);
        changeWindowTo(1, getStage(partsSaveButton));

    }
}
