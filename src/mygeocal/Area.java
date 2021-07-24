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
import java.util.Formatter;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.sort;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
class Compare implements Comparator<Node> {

    static Node x0;

    @Override
    public int compare(Node o1, Node o2) {
        int o = orientation(x0, o1, o2);

        if (o == 0) {
            return (distSq(x0, o2) >= distSq(x0, o1)) ? -1 : 1;
        }

        if (o == 2) {
            return -1;
        }
        return 1;

    }

    public static double distSq(Node p1, Node p2) {
        return (p1.x - p2.x) * (p1.x - p2.x)
                + (p1.y - p2.y) * (p1.y - p2.y);
    }

    public static int orientation(Node p, Node q, Node r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0) {
            return 0;  // colinear 
        }
        return (val > 0) ? 1 : 2; // clock or counterclock wise 
    }

}

public class Area {

    public static Stage stg;
    public static Scene scn;
    ArrayList<Line> carve = new ArrayList<>();
    ArrayList<Node> list = new ArrayList<>();
    public static ArrayList<Area> areaList = new ArrayList<>();
    Text name = new Text();
    Stage stage = new Stage();
    Scene scene;
    AnchorPane pane = new AnchorPane();
    JFXTextField input = new JFXTextField();
    JFXButton ok = new JFXButton("Ok"), cancel = new JFXButton("Cancel");
    int numberOfNode;

    public static int count = 0;

    public Area() {

        takeInput();

        System.out.println(numberOfNode);
        FXMLDocumentController.key = 8;
        areaList.add(this);

    }

    public static void takeNode(MouseEvent event, Area area) {
        Node n = Node.addNode(event);
        area.list.add(n);
        if (area.list.size() == area.numberOfNode) {
            area.showArea();
        }
    }

    public static void takeNode(Node n, Area area) {
//        Node n = Node.addNode(event);
        area.list.add(n);
        if (area.list.size() == area.numberOfNode) {
            area.showArea();
        }
    }

    private void showArea() {

        int x = 0;

        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).y < list.get(x).y) {
                x = i;
            } else if (list.get(i).y == list.get(x).y && list.get(i).x < list.get(x).x) {
                x = i;
            }
        }

        Compare.x0 = list.get(x);

        Compare cmp = new Compare();
        Collections.sort(list, cmp);

        for (int i = 0; i < list.size() - 1; ++i) {
            Line line = new Line(list.get(i).getCenterX(), list.get(i).getCenterY(), list.get(i + 1).getCenterX(), list.get(i + 1).getCenterY());
            line.setStroke(Color.BLUE);
            line.setStrokeWidth(2.5);
            System.out.println("yoyo");
            FXMLDocumentController.pane.getChildren().add(line);
            carve.add(line);

        }

        Line line = new Line(list.get(list.size() - 1).getCenterX(), list.get(list.size() - 1).getCenterY(), list.get(0).getCenterX(), list.get(0).getCenterY());
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(2.5);
        FXMLDocumentController.pane.getChildren().add(line);
        carve.add(line);

        double sump = 0, summ = 0;

        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                summ += list.get(i).x * list.get(i - 1).y;
//                System.out.println(summ);
            }
            if (i != list.size() - 1) {
                sump += list.get(i).x * list.get(i + 1).y;
            }

        }
        summ += list.get(0).x * list.get(list.size() - 1).y;
        sump += list.get(list.size() - 1).x * list.get(0).y;
        double ans = .5 * (sump - summ);
        Formatter ff = new Formatter();
        ff.format("%.4f", ans);
        System.out.println(ans);
        stg = new Stage();
        stg.setTitle("Answer");
        AnchorPane pn = new AnchorPane();
        scn = new Scene(pn);
        stg.setScene(scn);
        
        stg.initModality(Modality.APPLICATION_MODAL);
        pane.setPrefSize(250., 100.);
        stg.setWidth(300.);
        stg.setHeight(150.);
        
        Label ansLbl = new Label();
        ansLbl.setText("Area = ");
        Label anss = new Label();
        anss.setText(ff.toString());
        
        ansLbl.setLayoutX(30.);
        ansLbl.setLayoutY(30.);
        anss.setLayoutX(100.);
        anss.setLayoutY(33.);
        ansLbl.setFont(Font.font("Constantia", FontPosture.REGULAR, 20));
        anss.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 20));
        JFXButton ok;
        ok = new JFXButton();
        ok.setText("Ok");
        ok.setLayoutX(220.);
        ok.setLayoutY(70.);
        ok.setPrefWidth(50);
        ok.setTextFill(Color.WHITE);
        ok.buttonTypeProperty().setValue(JFXButton.ButtonType.RAISED);
        ok.setFont(Font.font("Constantia", FontPosture.REGULAR, 15));
        ok.setStyle("-fx-background-radius: 6; -fx-background-color: #0047D5;");
        pn.getChildren().addAll(ansLbl,anss,ok);
        stg.show();
        stg.getIcons().add(new Image("/image/GeoCal.png"));
        ok.setOnAction(e->{
            stg.close();
        });
        FXMLDocumentController.key = -1;


    }

    private void takeInput() {
        stage.setTitle("Input");
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

        ok.setLayoutX(250.);
        ok.setLayoutY(70.);
        ok.setTextFill(Color.WHITE);
        cancel.setTextFill(Color.WHITE);
        ok.setPrefWidth(50);
        ok.buttonTypeProperty().setValue(JFXButton.ButtonType.RAISED);
        cancel.buttonTypeProperty().setValue(JFXButton.ButtonType.RAISED);
        ok.setStyle("-fx-background-radius: 6; -fx-background-color:  #0047D5;");
        cancel.setStyle("-fx-background-radius: 6; -fx-background-color: #0047D5;");
        cancel.setLayoutX(315.);
        cancel.setLayoutY(70);
        pane.getChildren().addAll(ok, cancel);
        stage.show();

        ok.setOnAction(e -> {
            System.out.println("ok on");;
            okPressed();
        });
        cancel.setOnAction(e -> {
            cancelPressd();
        });
//        FXMLDocumentController.key = 1;
//        FXMLDocumentController.count = 0;
    }

    void addNode(Node point) {
        list.add(point);
    }

    void okPressed() {
        System.out.println("ok");
        Double d = Double.parseDouble(input.getText());
        numberOfNode = d.intValue();
        cancelPressd();

    }

    void cancelPressd() {
        stage.close();
    }

    double listx[], listy[], ar[];

    public void analyse() {

    }

    public static void redraw() {
        for (int i = 0; i < areaList.size(); i++) {
            Area ar = areaList.get(i);

            if (ar.carve.size() > 1 && ar.list.size() > 1) {
                ar.carve.get(0).setStartX(ar.list.get(0).getCenterX());
                ar.carve.get(0).setStartY(ar.list.get(0).getCenterY());

            }

            for (int j = 1; j < ar.list.size(); ++j) {
                ar.carve.get(j).setStartX(ar.list.get(j).getCenterX());
                ar.carve.get(j).setStartY(ar.list.get(j).getCenterY());

                ar.carve.get(j - 1).setEndX(ar.list.get(j).getCenterX());
                ar.carve.get(j - 1).setEndY(ar.list.get(j).getCenterY());

            }
//            ar.carve.get(j).setStartX(ar.list.get(j).getCenterX());
//            ar.carve.get(j).setStartY(ar.list.get(j).getCenterY());

            if (ar.carve.size() > 1 && ar.list.size() > 1) {

                ar.carve.get(ar.list.size() - 1).setEndX(ar.list.get(0).getCenterX());
                ar.carve.get(ar.list.size() - 1).setEndY(ar.list.get(0).getCenterY());

            }

        }
    }

}
