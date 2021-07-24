/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import java.util.ArrayList;
import java.util.Formatter;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import static mygeocal.FXMLDocumentController.gc;
import static mygeocal.FXMLDocumentController.key;
import static mygeocal.FXMLDocumentController.listPane;
import static mygeocal.FXMLDocumentController.page;
import static mygeocal.FXMLDocumentController.pane;
import static mygeocal.FXMLDocumentController.segmentStarted;
import static mygeocal.FXMLDocumentController.stage;
import static mygeocal.FXMLDocumentController.tmpCircle;
import static mygeocal.FXMLDocumentController.tmpSeg;
import static mygeocal.FXMLDocumentController.toolBar;
import static mygeocal.Grid.dX;
import static mygeocal.Grid.dY;

/**
 *
 * @Kiriti
 */
public class GeoCircle extends Circle {

    public Node centerNode, boundNode;
    public String name;
    public static ArrayList<GeoCircle> geoCircleList = new ArrayList<>();

    Formatter xx = new Formatter();
    Formatter yy = new Formatter();
    Formatter rr = new Formatter();

    public Formatter getXx() {
        return xx;
    }

    public void setXx(Formatter xx) {
        this.xx = xx;
    }

    public Formatter getYy() {
        return yy;
    }

    public void setYy(Formatter yy) {
        this.yy = yy;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getR() {
        return r;
    }

    public ContextMenu circleMenu;
    public MenuItem delete, properties;
    public double x, y, r;

    GeoCircle() {
        geoCircleList.add(this);
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.PURPLE);
        this.setStrokeWidth(2.);
        Grid.pane.getChildren().add(this);
        delete = new MenuItem("Delete");
        properties = new MenuItem("Properties");
        this.delete.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/delete_img.png"))));
        this.properties.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Node Properties.png"))));
        action(this);
        this.toBack();
    }

    GeoCircle(MouseEvent event) {
        this();
        this.centerNode = Node.addNode(event);
        this.name = centerNode.name.getText();
        this.setCenterX(event.getX());
        this.setCenterY(event.getY());
        x = (getCenterX() - Grid.positionOfYAxis) * Grid.unitOfScale / Grid.increment;
        y = (Grid.positionOfXAxis - getCenterY() + Grid.dY) * Grid.unitOfScale / Grid.increment;
        xx.format("%.2f", x);
        yy.format("%.2f", y);
    }

    GeoCircle(Node center) {
        this();
        this.centerNode = center;
        this.name = centerNode.name.getText();
        this.setCenterX(center.getCenterX());
        this.setCenterY(center.getCenterY());
        x = (getCenterX() - Grid.positionOfYAxis) * Grid.unitOfScale / Grid.increment;
        y = (Grid.positionOfXAxis - getCenterY() + Grid.dY) * Grid.unitOfScale / Grid.increment;
        xx.format("%.2f", x);
        yy.format("%.2f", y);

    }

    public Formatter getRr() {
        return rr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRr(Formatter rr) {
        this.rr = rr;
    }

    public static void redraw() {
        listPane.toFront();
        GeoCircle tmp;
        for (int i = 0; i < geoCircleList.size(); i++) {
            tmp = geoCircleList.get(i);
            double xx = ((tmp.boundNode.getCenterX() - tmp.centerNode.getCenterX()));
            xx *= xx;
            double yy = ((tmp.boundNode.getCenterY() - tmp.centerNode.getCenterY()));
            yy *= yy;
            tmp.setRadius(Math.sqrt(xx + yy));
            tmp.setCenterX(tmp.centerNode.getCenterX());
            tmp.setCenterY(tmp.centerNode.getCenterY());
            tmp.x = (tmp.getCenterX() - Grid.positionOfYAxis) * Grid.unitOfScale / Grid.increment;
            tmp.y = (Grid.positionOfXAxis - tmp.getCenterY() + Grid.dY) * Grid.unitOfScale / Grid.increment;
            tmp.r = tmp.getRadius()*Grid.unitOfScale/Grid.increment;
            tmp.xx = new Formatter();
            tmp.yy = new Formatter();
            tmp.rr = new Formatter();
            tmp.rr.format("%.2f", tmp.r);
            tmp.xx.format("%.2f", tmp.x);
            tmp.yy.format("%.2f", tmp.y);
//            FXMLDocumentController.circleTable.getItems().set(Side.circleData.indexOf(tmp),tmp);
            FXMLDocumentController.circleTable.getItems().set(Side.circleData.indexOf(tmp), tmp);
        }
    }

    public void deleteGeoCircle() {
        geoCircleList.remove(this);
        Grid.pane.getChildren().remove(this);
    }

    public void action(GeoCircle tmp) {
        delete.setOnAction(e -> {
            this.deleteGeoCircle();
        });
        tmp.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            public void handle(ContextMenuEvent event) {
                circleMenu.show(tmp, stage.getX() + event.getX() + 20, stage.getY() + 20 + event.getY());
                FXMLDocumentController.moveGrid = false;
                Grid.bug1 = true;
            }
        });
        this.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (key == 1 && segmentStarted == true) {
                    tmpSeg.setEndX(event.getX());
                    tmpSeg.setEndY(event.getY());
                    tmpSeg.slope = (tmpSeg.startNode.Y - tmpSeg.getEndY()) / ((tmpSeg.startNode.X - tmpSeg.getEndX()));
                    page.redraw(gc);
                } else if (key == 2 && segmentStarted == true) {
//                    canvas.setCursor(Cursor.CROSSHAIR);
                    FXMLDocumentController.event = event;
                    tmpSeg.setEndX(event.getX());
                    tmpSeg.setEndY(event.getY());
                    double a, b, c, d;
                    if (tmpSeg.startNode.getCenterX() != event.getX() + dX) {
                        tmpSeg.slope = ((event.getY()) - tmpSeg.startNode.getCenterY()) / ((event.getX()) - tmpSeg.startNode.getCenterX());
                        a = 0;
                        b = tmpSeg.slope * (a - tmpSeg.startNode.getCenterX()) + tmpSeg.startNode.getCenterY();
                        c = pane.getWidth();
                        d = tmpSeg.slope * (c - tmpSeg.startNode.getCenterX()) + tmpSeg.startNode.getCenterY();
                        tmpSeg.setStartX(a);
                        tmpSeg.setStartY(b);
                        tmpSeg.setEndX(c);
                        tmpSeg.setEndY(d);
                    } else {
                        tmpSeg.setStartX(event.getX());
                        tmpSeg.setStartY(0);
                        tmpSeg.setEndX(event.getX());
                        tmpSeg.setEndY(pane.getHeight());
                    }
                    page.redraw(gc);
                    toolBar.toFront();
                } else if (key == 3 && segmentStarted == true) {
                    if (tmpSeg.parentSegment.getEndX() == tmpSeg.parentSegment.getEndY()) {
                        tmpSeg.setStartX(event.getX());
                        tmpSeg.setStartY(0);
                        tmpSeg.setEndX(event.getX());
                        tmpSeg.setEndY(pane.getHeight());
                    } else {
                        if (tmpSeg.parentSegment.getStartX() == tmpSeg.parentSegment.getEndX()) {
                            tmpSeg.slope = 0;
                        } else {
                            tmpSeg.slope = -1. / tmpSeg.parentSegment.slope;
                        }

                        double a, b, c, d;
                        a = 0;
                        b = tmpSeg.slope * (a - event.getX()) + event.getY();
                        c = pane.getWidth();
                        d = tmpSeg.slope * (c - event.getX()) + event.getY();
                        tmpSeg.setStartX(a);
                        tmpSeg.setStartY(b);
                        tmpSeg.setEndX(c);
                        tmpSeg.setEndY(d);
                    }
                } else if (key == 10 && segmentStarted == true) {
                    for (int i = 0; i < Node.nodeList.size(); i++) {
                        Node.nodeList.get(i).toFront();
                    }

                    double xx = ((event.getX() - tmpCircle.centerNode.getCenterX()));
                    xx *= xx;
                    double yy = ((event.getY() - tmpCircle.centerNode.getCenterY()));
                    yy *= yy;
                    tmpCircle.setRadius(Math.sqrt(xx + yy));
                }
                page.redraw(gc);

            }
        });
    }
}
