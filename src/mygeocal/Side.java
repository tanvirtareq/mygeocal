/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import static mygeocal.FXMLDocumentController.circleCenterX;
import static mygeocal.FXMLDocumentController.circleCenterY;
import static mygeocal.FXMLDocumentController.circleName;
import static mygeocal.FXMLDocumentController.circleRadius;
import static mygeocal.FXMLDocumentController.coColumnX;
import static mygeocal.FXMLDocumentController.coColumnY;
import static mygeocal.FXMLDocumentController.nameColumn;
import static mygeocal.FXMLDocumentController.segmentEnd;
import static mygeocal.FXMLDocumentController.segmentName;
import static mygeocal.FXMLDocumentController.segmentStart;

/**
 *
 * @author HP
 */
public class Side {

    Side() {

    }
    public static TableView pointTable = FXMLDocumentController.pointTable;
    public static ObservableList<Node> pointData = FXCollections.observableArrayList();
    public static ObservableList<GeoCircle> circleData = FXCollections.observableArrayList();
    public static ObservableList<Segment> segmentData = FXCollections.observableArrayList();

    public static void decorateSidePane() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        coColumnX.setCellValueFactory(new PropertyValueFactory<>("xx"));
        coColumnY.setCellValueFactory(new PropertyValueFactory<>("yy"));
        pointTable.setEditable(false);
        pointTable.setItems(pointData);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        circleName.setCellValueFactory(new PropertyValueFactory<>("name"));
        circleCenterX.setCellValueFactory(new PropertyValueFactory<>("xx"));
        circleCenterY.setCellValueFactory(new PropertyValueFactory<>("yy"));
        circleRadius.setCellValueFactory(new PropertyValueFactory<>("rr"));
        FXMLDocumentController.circleTable.setItems(circleData);
        FXMLDocumentController.circleTable.setEditable(false);

        segmentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        segmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        segmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        FXMLDocumentController.segmentTable.setItems(segmentData);
        FXMLDocumentController.segmentTable.setEditable(false);

    }

}
