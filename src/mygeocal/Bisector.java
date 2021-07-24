/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import static mygeocal.FXMLDocumentController.key;
import static mygeocal.FXMLDocumentController.listPane;
import static mygeocal.FXMLDocumentController.onNode;
import static mygeocal.FXMLDocumentController.pane;
import static mygeocal.FXMLDocumentController.segmentStarted;
import static mygeocal.FXMLDocumentController.stage;
import static mygeocal.FXMLDocumentController.tmpSeg;
import static mygeocal.FXMLDocumentController.toolBar;
import static mygeocal.Grid.dY;

/**
 *
 * @author HP
 */
// key value 5
public class Bisector extends Segment {

    public Segment parent;
    public double slope;
    public ContextMenu bisectorMenu;
    public MenuItem delete;
    public static ArrayList<Bisector> bisectorList = new ArrayList<>();
    public ArrayList<PerpendicularLine> childPerpendicular = new ArrayList<>();
    

    public Bisector(Segment parent) {

        bisectorList.add(this);
        this.parent = parent;
        bisectorMenu = new ContextMenu();
        delete = new MenuItem("Delete");
        delete.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/delete_img.png"))));
        bisectorMenu.getItems().addAll(delete);
        if (parent.getStartY() == parent.getEndY()) {
            this.setStartX((parent.getStartX() + parent.getEndX()) / 2.);
            this.setEndX((parent.getStartX() + parent.getEndX()) / 2.);
            this.setStartY(0.);
            this.setEndY(pane.getHeight());
        } else if (parent.getStartX() == parent.getEndX()) {
            this.setStartX(0);
            this.setEndX(pane.getWidth());
            this.setStartY((parent.getEndY() + parent.getEndY()) / 2.);
            this.setEndY((parent.getEndY() + parent.getEndY()) / 2.);
        } else {
            this.slope = -1. / ((parent.getStartY() - parent.getEndY()) / (parent.getStartX() - parent.getEndX()));
            double a, b, c, d;
            double X = (parent.getStartX() + parent.getEndX()) / 2., Y = (parent.getStartY() + parent.getEndY()) / 2.;
            a = 0;
            b = this.slope * (a - X) + Y;
            c = pane.getWidth();
            d = this.slope * (c - X) + Y;
            this.setStartX(a);
            this.setStartY(b);
            this.setEndX(c);
            this.setEndY(d);
        }
        actions(this);
        toolBar.toFront();
    }

    public void deleteBisector() {
        for (int i = 0; i < childPerpendicular.size(); i++) {
            childPerpendicular.get(i).deletePerpedicularLine();
        }
        bisectorList.remove(this);
        Grid.pane.getChildren().remove(this);

    }

    public void actions(Bisector tmp) {
         delete.setOnAction(e -> {
            tmp.deleteBisector();

        });
         tmp.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            public void handle(ContextMenuEvent event) {
                bisectorMenu.show(tmp, stage.getX() + event.getX(), stage.getY() + event.getY());
                FXMLDocumentController.moveGrid = false;
                Grid.bug1 = true;
            }
        });
        tmp.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if((key==1 || key==2 ||key==10) &&(segmentStarted==true))
                    tmp.setCursor(Cursor.CROSSHAIR);
                else
                    tmp.setCursor(Cursor.HAND);
            }
        });
        
        tmp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (key == 3) {
                    if (segmentStarted == false && onNode == false) {
                        tmpSeg = new PerpendicularLine(event.getX(), event.getY(), tmp);
                        tmp.childPerpendicular.add((PerpendicularLine) tmpSeg);
                        segmentStarted = true;
                        onNode = true;
                    }
                }
            }
        });
    }

    public static void redraw() {listPane.toFront();
        Bisector tmp;
        for (int i = 0; i < bisectorList.size(); i++) {
            tmp =  bisectorList.get(i);
            if (tmp.parent.getStartY() == tmp.parent.getEndY()) {
                tmp.setStartX((tmp.parent.getStartX() + tmp.parent.getEndX()) / 2.);
                tmp.setEndX((tmp.parent.getStartX() + tmp.parent.getEndX()) / 2.);
                tmp.setStartY(0.);
                tmp.setEndY(pane.getHeight());
            } else if (tmp.parent.getStartX() == tmp.parent.getEndX()) {
                tmp.setStartX(0);
                tmp.setEndX(pane.getWidth());
                tmp.setStartY((tmp.parent.getEndY() + tmp.parent.getEndY()) / 2.);
                tmp.setEndY((tmp.parent.getEndY() + tmp.parent.getEndY()) / 2.);
            } else {
                tmp.slope = -1. / ((tmp.parent.getStartY() - tmp.parent.getEndY()) / (tmp.parent.getStartX() - tmp.parent.getEndX()));
                double a, b, c, d;
                double X = (tmp.parent.getStartX() + tmp.parent.getEndX()) / 2., Y = (tmp.parent.getStartY() + tmp.parent.getEndY()) / 2.;
                a = 0;
                b = tmp.slope * (a - X) + Y;
                c = pane.getWidth();
                d = tmp.slope * (c - X) + Y;
                tmp.setStartX(a);
                tmp.setStartY(b);
                tmp.setEndX(c);
                tmp.setEndY(d);
            }
        }
    }

}
