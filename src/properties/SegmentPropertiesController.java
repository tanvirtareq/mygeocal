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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import mygeocal.Grid;

/**
 * FXML Controller class
 *
 * @author Tanvir Tareq
 */
public class SegmentPropertiesController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXButton okButtonPressed;
    @FXML
    private JFXButton cancelButtonPressed;
    @FXML
    private JFXButton cancel;
    @FXML
    private Label startLabel;
    @FXML
    private Label endLabel;
    @FXML
    private Label lengthLabel, slopeLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

//        cancel.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/cancel.png"))));
        Double d1 = (SegmentProperties.bokri.startNode.x);
        Double d2 = (SegmentProperties.bokri.startNode.y);

        Formatter fmt1 = new Formatter();
        Formatter fmt2 = new Formatter();
        fmt1.format("%.2f", d1);
        fmt2.format("%.2f", d2);
        if (SegmentProperties.bokri.getStartX() == SegmentProperties.bokri.getEndX()) {
            slopeLabel.setText("Undefined");
        } else {
            Double d = -SegmentProperties.bokri.slope;
            Formatter ff = new Formatter();
            ff.format("%.2f",d);
            slopeLabel.setText(ff.toString());
        }
        startLabel.setText("( " + fmt1.toString() + " , " + fmt2.toString() + " )");

        d1 = (SegmentProperties.bokri.endNode.x);
        d2 = (SegmentProperties.bokri.endNode.y);
        fmt1 = new Formatter();
        fmt2 = new Formatter();
        fmt1.format("%.2f", d1);
        fmt2.format("%.2f", d2);

        endLabel.setText("( " + fmt1.toString() + " , " + fmt2.toString() + " )");

        double length = Math.sqrt((SegmentProperties.bokri.startNode.x - SegmentProperties.bokri.endNode.x) * (SegmentProperties.bokri.startNode.x - SegmentProperties.bokri.endNode.x) + (SegmentProperties.bokri.startNode.y - SegmentProperties.bokri.endNode.y) * (SegmentProperties.bokri.startNode.y - SegmentProperties.bokri.endNode.y));
        fmt1 = new Formatter();
        fmt1.format("%.2f", length);

        lengthLabel.setText("( " + fmt1.toString() + " )");
    }

    @FXML
    private void okButtonPressed(ActionEvent event) {
        SegmentProperties.stage.close();
    }

    @FXML
    private void cancelButtonPressed(ActionEvent event) {
        SegmentProperties.stage.close();
    }

}
