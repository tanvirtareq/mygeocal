/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package properties;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import mygeocal.Bisector;
import mygeocal.FXMLDocumentController;
import mygeocal.GeoCircle;
import mygeocal.Grid;
import mygeocal.Node;
import mygeocal.PerpendicularLine;
import mygeocal.RenameBox;
import mygeocal.Segment;
import mygeocal.Side;
import mygeocal.StraightLine;
import properties.NodeProperties;


/**
 * FXML Controller class
 *
 * @author HP
 */
public class NodePropertiesFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXTextField xTextField , yTextField;
    @FXML
    private Label nodeName;
    @FXML
    private JFXComboBox<String> colorPicker;
    @FXML
    private JFXButton cancel;
    ObservableList < String > list = FXCollections.observableArrayList("Black","Red","Blue","Orange","Purple","Green","Yellow");
    
    public void changeColor()
    {
        String color = colorPicker.getValue();
        if(color == "Blue")
        {
            NodeProperties.bokri.setFill(Color.ROYALBLUE);
        }
        else if(color=="Red")
        {
            NodeProperties.bokri.setFill(Color.RED);
        }
        else if(color=="Black")
        {
            NodeProperties.bokri.setFill(Color.BLACK);
        }
        else if(color=="Orange")
        {
            NodeProperties.bokri.setFill(Color.ORANGE);
        }
        else if(color=="Purple")
        {
            NodeProperties.bokri.setFill(Color.PURPLE);
        }
        else if(color=="Green")
        {
            NodeProperties.bokri.setFill(Color.GREEN);
        }
        else if(color=="Yellow")
        {
            NodeProperties.bokri.setFill(Color.YELLOW);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colorPicker.getItems().addAll(list);
        cancel.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/cancel.png"))));
        nodeName.setText(NodeProperties.bokri.name.getText());
        Double d = ((NodeProperties.bokri.getCenterX()-Grid.positionOfYAxis)*(Grid.unitOfScale)/(Grid.increment));
        DecimalFormat dec = new DecimalFormat("#0.00");
        xTextField.setPromptText(dec.format(d));
        d = ((Grid.positionOfXAxis + Grid.dY -NodeProperties.bokri.getCenterY())*(Grid.unitOfScale)/(Grid.increment));
        yTextField.setPromptText(dec.format(d));
    }    
    public void cancelButtonPressedIV(MouseEvent event)
    {
        NodeProperties.stage.close();
    }
    public void okButtonPressed(ActionEvent event) {
        if(!xTextField.getText().toString().isEmpty())
        {
            Double xd=Double.parseDouble(xTextField.getText().toString());
            NodeProperties.bokri.setCenterX((xd*Grid.increment/Grid.unitOfScale)+Grid.positionOfYAxis);
            NodeProperties.bokri.x = xd;
            Formatter fmt=new Formatter();
            fmt.format("%.2f", xd);
            NodeProperties.bokri.xx = fmt;
        }
        
        if(!yTextField.getText().toString().isEmpty())
        {
             Double yd=Double.parseDouble(yTextField.getText().toString());
             NodeProperties.bokri.setCenterY((-1*yd*Grid.increment/Grid.unitOfScale)+Grid.positionOfXAxis+Grid.dY);
             NodeProperties.bokri.y = yd;
             
             Formatter fmt=new Formatter();
            fmt.format("%.2f", yd);
            NodeProperties.bokri.yy = fmt;
        }
        Side.pointTable.getItems().set(Side.pointData.indexOf(NodeProperties.bokri), NodeProperties.bokri);
        changeColor();
        mygeocal.FXMLDocumentController.page.redraw(mygeocal.FXMLDocumentController.gc);
        Node.redraw();
        Segment.redraw();
        StraightLine.redraw();
        PerpendicularLine.redraw();
        GeoCircle.redraw();
        Bisector.redraw();
        cancelButtonPressed(event);
        
    }


    @FXML
    public void cancelButtonPressed(ActionEvent event) {
        NodeProperties.stage.close();
    }
    
}
