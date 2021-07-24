/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Comparator;
import static javafx.collections.FXCollections.sort;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static mygeocal.FXMLDocumentController.stage;

/**
 *
 * @author HP
 */
public class GetEquation {

    ArrayList<Line> carve = new ArrayList<>();
    Text name = new Text();
    Stage stage = new Stage();
    Scene scene;
    AnchorPane pane = new AnchorPane();
    JFXTextField input = new JFXTextField();
    JFXButton ok = new JFXButton("Ok"), cancel = new JFXButton("Cancel");
    int numberOfNode;

    public GetEquation() {

        takeInput();
        FXMLDocumentController.key = 0;

    }

    private void takeInput() {

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        pane.setPrefSize(400., 150.);
        stage.setWidth(400.);
        stage.setHeight(150.);

        name.setText("Number of points: ");
        name.setFont(Font.font("Constantia", 20));
        name.setX(15.);
        name.setY(30.);
        pane.getChildren().add(name);

        input.setPrefWidth(80);
        input.setLayoutX(180.);
        input.setLayoutY(5.);
        input.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 20));
        pane.getChildren().add(input);

        ok.setLayoutX(270.);
        ok.setLayoutY(70.);
        ok.setTextFill(Color.WHITE);
        cancel.setTextFill(Color.WHITE);
        ok.buttonTypeProperty().setValue(JFXButton.ButtonType.RAISED);
        cancel.buttonTypeProperty().setValue(JFXButton.ButtonType.RAISED);
        ok.setStyle("-fx-background-radius: 6; -fx-background-color: #6991DF;");
        cancel.setStyle("-fx-background-radius: 6; -fx-background-color: #6991DF;");
        cancel.setLayoutX(315.);
        cancel.setLayoutY(70);
        pane.getChildren().addAll(ok, cancel);
        stage.show();

        ok.setOnAction(e -> {
            okPressed();
        });
        cancel.setOnAction(e -> {
            cancelPressd();
        });
        FXMLDocumentController.key = 1;
        FXMLDocumentController.count = 0;
    }
    void addNode(Node point) {
        list.add(point);
    }
    void okPressed() {
        Double d = Double.parseDouble(input.getText());
        numberOfNode = d.intValue();
        cancelPressd();

    }

    void cancelPressd() {
        stage.close();
    }
    
    ArrayList<Node> list = new ArrayList<>();

    double listx[], listy[], ar[];

    public void analyse(){
        
    }
}