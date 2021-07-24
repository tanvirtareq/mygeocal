/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Tanvir Tareq
 */
public class EquationInputBoxController implements Initializable {
    @FXML
    private JFXButton okButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXTextField textField;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void okButtonPressed(ActionEvent event) {
        new Equation(textField.getText().toString());
        FXMLDocumentController.toolBar.toFront();
        cancelButtonPressed(event);
        
    }

    @FXML
    private void cancelButtonPressed(ActionEvent event) {
        FXMLDocumentController.stage.close();
    }
    
}
