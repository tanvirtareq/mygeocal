/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package properties;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Tanvir Tareq
 */
public class AnglePropertiesController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXButton okButtonPressed;
    @FXML
    private JFXButton cancelButtonPressed;
    @FXML
    private JFXButton cancel;
    @FXML 
    private Text nodeA;
    @FXML
    private Text nodeB;
    
    @FXML
    private Text nodeC;
    
    @FXML
    private Text angleText;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Double d1=(AngleProperties.a.x);
        Double d2=(AngleProperties.a.y);
        Formatter fmt1=new Formatter();
        Formatter fmt2=new Formatter();
        fmt1.format("%.2f", d1);
        fmt2.format("%.2f", d2);
        
        nodeA.setText("( "+fmt1.toString()+" , "+fmt2.toString()+" )");

        d1=(AngleProperties.b.x);
        d2=(AngleProperties.b.y);
        fmt1=new Formatter();
        fmt2=new Formatter();
        fmt1.format("%.2f", d1);
        fmt2.format("%.2f", d2);
        
        nodeB.setText("( "+fmt1.toString()+" , "+fmt2.toString()+" )");

        d1=(AngleProperties.c.x);
        d2=(AngleProperties.c.y);
        fmt1=new Formatter();
        fmt2=new Formatter();
        fmt1.format("%.2f", d1);
        fmt2.format("%.2f", d2);
        
        nodeC.setText("( "+fmt1.toString()+" , "+fmt2.toString()+" )");

        d1=(AngleProperties.bokri);
        d2=Math.toDegrees(d1);
        fmt1=new Formatter();
        fmt2=new Formatter();
        fmt1.format("%.2f", d1);
        fmt2.format("%.2f", d2);
        
        angleText.setText(fmt2.toString()  + "D");
 
        
        // TODO
    }    

    @FXML
    private void okButtonPressed() {
        AngleProperties.stage.close();
    }

    @FXML
    private void cancelButonPressed() {
        AngleProperties.stage.close();
    }
    
}
