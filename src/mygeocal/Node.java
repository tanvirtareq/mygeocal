package mygeocal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import static mygeocal.FXMLDocumentController.a;
import static mygeocal.FXMLDocumentController.gc;
import static mygeocal.FXMLDocumentController.key;
import static mygeocal.FXMLDocumentController.listPane;
import static mygeocal.FXMLDocumentController.page;
import static mygeocal.FXMLDocumentController.stage;
import static mygeocal.FXMLDocumentController.tmpSeg;
import static mygeocal.FXMLDocumentController.onNode;
import static mygeocal.FXMLDocumentController.segmentStarted;
import static mygeocal.FXMLDocumentController.segmentTable;
import static mygeocal.FXMLDocumentController.tmpCircle;
import static mygeocal.FXMLDocumentController.tmpSeg;
import static mygeocal.Grid.dX;
import static mygeocal.Grid.dY;
import static mygeocal.Grid.pane;
import properties.NodeProperties;

/**
 *
 * @author Kiriti
 */
public class Node extends Circle {

    public static ArrayList<Node> nodeList = new ArrayList<>();
    public ArrayList<GeoCircle> adjacentCircle = new ArrayList<>();
    public ArrayList<Segment> adjacentSegment = new ArrayList<>();
    public ArrayList<StraightLine> adjacentLine = new ArrayList<>();
    public ArrayList<PerpendicularLine> adjacentperpendicular = new ArrayList<>();
    public double x, y, X, Y;

    public String getName() {
        return Name;
    }

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

    public void setName(String Name) {
        this.Name = Name;
    }

    public static char ch = 'A';
    public double tempx, tempy;
    public Label name = new Label();

    public String Name;
    public ContextMenu nodeMenu;
    public MenuItem delete, rename, properties;
    public Formatter xx, yy;

    public Node(double x, double y) {

        super(x, y, 3.6, Color.ROYALBLUE);
        nodeMenu = new ContextMenu();
        delete = new MenuItem("Delete");
        rename = new MenuItem("Rename");
        properties = new MenuItem("Properties");
        delete.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/delete_img.png"))));
        rename.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/rename0_img.png"))));
        properties.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Node Properties.png"))));
        nodeMenu.getItems().addAll(delete, rename, properties);
//        nodeMenu.setOpacity(1);
        X = x;
        Y = y;
        name.setMinWidth(15);
        setStroke(Color.BLACK);
        setStrokeWidth(1.5);
        measure(x, y);
        name.setText(new String(Character.toString(Character.toUpperCase(ch))));
        name.setTextFill(Color.BROWN);
        name.setFont(new Font(15));
        ch++;
        if (ch > 'Z') {
            ch = 'A';
        }
        name.setLayoutX(getCenterX());
        name.setLayoutY(getCenterY());
        name.setPrefWidth(10.);
        name.setPrefHeight(20.);
        page.pane.getChildren().addAll(name);
        Name = name.getText();
        page.pane.getChildren().addAll(this);
        nodeList.add(this);
        actions(this);
        Side.pointTable.getItems().add(this);
    }

    public Node(MouseEvent event, Grid page) // Toolbar e click korle Node create howar kotha/... kintu hoy na... Dont know why?
    {
        this(event.getX(), event.getY());

    }

    public void measure(double ex, double ey) {
        if ((((int) (ex + Grid.dX - Grid.positionOfYAxis)) % ((int) Grid.increment) == 0)) {
            X = ex;
        } else if (((int) (ex + 1 + Grid.dX - Grid.positionOfYAxis)) % ((int) Grid.increment) == 0) {
            X = ex + 1;
        } else if (((int) (ex + 2 + Grid.dX - Grid.positionOfYAxis)) % ((int) Grid.increment) == 0) {
            X = ex + 2;
        } else if (((int) (ex + 3 + Grid.dX - Grid.positionOfYAxis)) % ((int) Grid.increment) == 0) {
            X = ex + 3;
        } else if (((int) (ex + 4 + Grid.dX - Grid.positionOfYAxis)) % ((int) Grid.increment) == 0) {
            X = ex + 4;
        } else if (((int) (ex - 1 + Grid.dX - Grid.positionOfYAxis)) % ((int) Grid.increment) == 0) {
            X = ex - 1;
        } else if (((int) (ex - 2 + Grid.dX - Grid.positionOfYAxis)) % ((int) Grid.increment) == 0) {
            X = ex - 2;
        } else if (((int) (ex - 3 + Grid.dX - Grid.positionOfYAxis)) % ((int) Grid.increment) == 0) {
            X = ex - 3;
        } else if (((int) (ex - 4 + Grid.dX - Grid.positionOfYAxis)) % ((int) Grid.increment) == 0) {
            X = ex - 4;
        } else {
            X = ex;
        }

        if ((((int) (ey + Grid.dY - Grid.positionOfXAxis)) % ((int) Grid.increment) == 0)) {
            Y = ey;
        } else if (((int) (ey + 1 - Grid.dY - Grid.positionOfXAxis)) % ((int) Grid.increment) == 0) {
            Y = ey + 1;
        } else if (((int) (ey + 2 - Grid.dY - Grid.positionOfXAxis)) % ((int) Grid.increment) == 0) {
            Y = ey + 2;
        } else if (((int) (ey + 3 - Grid.dY - Grid.positionOfXAxis)) % ((int) Grid.increment) == 0) {
            Y = ey + 3;
        } else if (((int) (ey + 4 - Grid.dY - Grid.positionOfXAxis)) % ((int) Grid.increment) == 0) {
            Y = ey + 4;
        } else if (((int) (ey - 1 - Grid.dY - Grid.positionOfXAxis)) % ((int) Grid.increment) == 0) {
            Y = ey - 1;
        } else if (((int) (ey - 2 - Grid.dY - Grid.positionOfXAxis)) % ((int) Grid.increment) == 0) {
            Y = ey - 2;
        } else if (((int) (ey - 3 - Grid.dY - Grid.positionOfXAxis)) % ((int) Grid.increment) == 0) {
            Y = ey - 3;
        } else if (((int) (ey - 4 - Grid.dY - Grid.positionOfXAxis)) % ((int) Grid.increment) == 0) {
            Y = ey - 4;
        } else {
            Y = ey;
        }

        x = ((X + Grid.dX - Grid.positionOfYAxis) * (Grid.unitOfScale)) / (Grid.increment);
        y = ((Grid.positionOfXAxis - (Y - Grid.dY)) * (Grid.unitOfScale)) / (Grid.increment);

        this.setCenterX(Grid.positionOfYAxis + Grid.dX + ((this.x / Grid.unitOfScale) * Grid.increment));

        this.setCenterY(Grid.positionOfXAxis + Grid.dY - ((this.y / Grid.unitOfScale) * Grid.increment));

        xx = new Formatter();
        yy = new Formatter();

        xx.format("%.2f", x);
        yy.format("%.2f", y);

    }

    public static Node addNode(MouseEvent event) {
        Node tmp = new Node(event, FXMLDocumentController.page);
        return tmp;
//        redraw();
    }

    public void moveNode(MouseEvent event, Node tmp, Label name) {
        X = event.getX();
        Y = event.getY();
        x = ((X + Grid.dX - Grid.positionOfYAxis) * (Grid.unitOfScale)) / (Grid.increment);
        y = ((Grid.positionOfXAxis - (Y - Grid.dY)) * (Grid.unitOfScale)) / (Grid.increment);
        tmp.setCenterX(event.getX());
        tmp.setCenterY(event.getY());

        name.setLayoutX(X);
        name.setLayoutY(Y);

        page.clearCanvas(gc);
        page.redraw(gc);
    }

    public static void setTop() {
        int length = nodeList.size();
        for (int i = 0; i < length; i++) {
            nodeList.get(i).toFront();
        }
    }

    public static void redraw() {
        listPane.toFront();
        int length = nodeList.size();
        for (int i = 0; i < length; i++) {
            nodeList.get(i).setCenterX(Grid.positionOfYAxis + Grid.dX + ((nodeList.get(i).x / Grid.unitOfScale) * Grid.increment));
            nodeList.get(i).setCenterY(Grid.positionOfXAxis + Grid.dY - ((nodeList.get(i).y / Grid.unitOfScale) * Grid.increment));
            nodeList.get(i).name.setLayoutX(nodeList.get(i).getCenterX());
            nodeList.get(i).name.setLayoutY(nodeList.get(i).getCenterY());
            if ((nodeList.get(i).getCenterX() <= FXMLDocumentController.canvas.getLayoutX()) || (nodeList.get(i).getCenterY() <= FXMLDocumentController.canvas.getLayoutY())) {
                nodeList.get(i).setVisible(false);
                nodeList.get(i).name.setVisible(false);
            } else {
                nodeList.get(i).setVisible(true);
                nodeList.get(i).name.setVisible(true);
            }

        }
    }

    public void deleteNode() {
        Node tmp = this;
        Side.pointTable.getItems().remove(this);
        for (int i = 0; i < adjacentSegment.size(); i++) {
            adjacentSegment.get(i).deleteSegment();
        }
        for (int i = 0; i < adjacentLine.size(); i++) {
            adjacentLine.get(i).deleteStraightLine();
        }
        for (int i = 0; i < adjacentperpendicular.size(); i++) {
            adjacentperpendicular.get(i).deletePerpedicularLine();
        }
        for (int i = 0; i < adjacentCircle.size(); i++) {
            if (adjacentCircle.get(i) != null) {
                adjacentCircle.get(i).deleteGeoCircle();
            } else {
                System.out.println(i);
            }
        }
        tmp.adjacentSegment.clear();
        tmp.adjacentLine.clear();
        tmp.adjacentperpendicular.clear();
        tmp.adjacentCircle.clear();
        nodeList.remove(tmp);
        Grid.pane.getChildren().remove(tmp);
        Grid.pane.getChildren().remove(tmp.name);
    }

    public void actions(Node tmp) {
        delete.setOnAction(e -> {
            tmp.deleteNode();
        });

        rename.setOnAction(e -> {
            RenameBox renameBox = new RenameBox();
            try {
                renameBox.go(tmp);
            } catch (IOException ex) {
                Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        properties.setOnAction(e -> {
            System.out.println("vooo");
            NodeProperties propertiesBox = new NodeProperties();
            try {
                propertiesBox.go(tmp);
            } catch (IOException ex) {
                Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        tmp.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            public void handle(ContextMenuEvent event) {
                nodeMenu.show(tmp, stage.getX() + event.getX() + 20, stage.getY() + 20 + event.getY());
                FXMLDocumentController.moveGrid = false;
                Grid.bug1 = true;
            }
        });
        tmp.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

//               circle.setVisible(false);
            }
        });

        tmp.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLDocumentController.everythingIsNormal = false;
//                tmp.setRadius(4);
                tmp.setCursor(Cursor.HAND);
                page.redraw(gc);
                FXMLDocumentController.toolBar.toFront();
                if (key == 1 && segmentStarted == true) {

                    tmpSeg.setEndX(tmp.getCenterX());
                    tmpSeg.setEndY(tmp.getCenterY());
                    page.redraw(gc);
                } else if (key == 2 && segmentStarted == true) {
                    if (tmpSeg.startNode.getCenterX() != tmp.getCenterX()) {
                        double a, b, c, d;
                        tmpSeg.slope = ((tmp.getCenterY()) - tmpSeg.startNode.getCenterY()) / ((tmp.getCenterX()) - tmpSeg.startNode.getCenterX());
                        a = 0;
                        b = tmpSeg.slope * (a - tmpSeg.startNode.getCenterX()) + tmpSeg.startNode.getCenterY();
                        c = pane.getWidth();
                        d = tmpSeg.slope * (c - tmpSeg.startNode.getCenterX()) + tmpSeg.startNode.getCenterY();
                        tmpSeg.setStartX(a);
                        tmpSeg.setStartY(b);
                        tmpSeg.setEndX(c);
                        tmpSeg.setEndY(d);
                    } else {
                        tmpSeg.setStartX(tmp.getCenterX());
                        tmpSeg.setStartY(0);
                        tmpSeg.setEndX(tmp.getCenterX());
                        tmpSeg.setEndY(pane.getHeight());
                        page.redraw(gc);
                    }
                } else if (key == 3 && segmentStarted == true) {
                    if (tmpSeg.parentSegment.getEndX() == tmpSeg.parentSegment.getEndY()) {
                        tmpSeg.setStartX(tmp.getCenterX());
                        tmpSeg.setStartY(0);
                        tmpSeg.setEndX(tmp.getCenterX());
                        tmpSeg.setEndY(pane.getHeight());
                    } else {
                        if (tmpSeg.parentSegment.getStartX() == tmpSeg.parentSegment.getEndX()) {
                            tmpSeg.slope = 0;
                        } else {
                            tmpSeg.slope = -1. / tmpSeg.parentSegment.slope;
                        }

                        double a, b, c, d;
                        a = 0;
                        b = tmpSeg.slope * (a - tmp.getCenterX()) + tmp.getCenterY();
                        c = pane.getWidth();
                        d = tmpSeg.slope * (c - tmp.getCenterX()) + tmp.getCenterY();
                        tmpSeg.setStartX(a);
                        tmpSeg.setStartY(b);
                        tmpSeg.setEndX(c);
                        tmpSeg.setEndY(d);
                    }

                }
                tmp.toFront();
            }
        });
        tmp.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    FXMLDocumentController.moveGrid = false;
                    Grid.bug1 = false;
                }
            }
        });

        tmp.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                FXMLDocumentController.moveGrid = true;
            }
        });

        tmp.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (Grid.bug1) {
                    return;
                }
                measure(event.getX(), event.getY());
                Side.pointTable.getItems().set(Side.pointData.indexOf(tmp), tmp);
                moveNode(event, tmp, name);
                Segment.redraw();
                StraightLine.redraw();
                PerpendicularLine.redraw();
                GeoCircle.redraw();
                Bisector.redraw();
                GeoEllipse.redraw();
            }
        });

        tmp.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                page.redraw(gc);
                if (key == 1) {
                    if (segmentStarted == false) {
                        segmentStarted = true;
                        onNode = true;
                        tmpSeg = new Segment(tmp);
                        tmp.adjacentSegment.add(tmpSeg);
                    } else {
                        onNode = true;
                        segmentStarted = false;
                        tmpSeg.setEndX(tmp.getCenterX());
                        tmpSeg.setEndY(tmp.getCenterY());
                        tmpSeg.endNode = tmp;
                        tmp.adjacentSegment.add(tmpSeg);
                        tmpSeg.name += tmpSeg.endNode.name.getText();
                        tmpSeg.xx.format("%.2f", (tmpSeg.endNode.getCenterX() - Grid.positionOfYAxis) * Grid.unitOfScale / Grid.increment);
                        tmpSeg.yy.format("%.2f", (Grid.positionOfXAxis - tmpSeg.endNode.getCenterY() + Grid.dY) * Grid.unitOfScale / Grid.increment);
                        tmpSeg.end = "(" + tmpSeg.xx + "," + tmpSeg.yy + ")";

                        segmentTable.getItems().add(tmpSeg);
                        tmp.toFront();
                        page.redraw(gc);
                    }
                } else if (key == 2 && onNode == false) {
                    if (segmentStarted == false) {
                        segmentStarted = true;
                        onNode = true;
                        tmpSeg = new StraightLine(tmp);

                        tmp.adjacentLine.add((StraightLine) tmpSeg);
                    } else {
                        page.redraw(gc);
                        onNode = true;
                        segmentStarted = false;
                        double a, b, c, d;
                        if (tmpSeg.startNode.getCenterX() != tmp.getCenterX()) {
                            tmpSeg.slope = ((tmp.getCenterY()) - tmpSeg.startNode.getCenterY()) / ((tmp.getCenterX()) - tmpSeg.startNode.getCenterX());
                            a = 0;
                            b = tmpSeg.slope * (a - tmpSeg.startNode.getCenterX()) + tmpSeg.startNode.getCenterY();
                            c = pane.getWidth();
                            d = tmpSeg.slope * (c - tmpSeg.startNode.getCenterX()) + tmpSeg.startNode.getCenterY();
                            tmpSeg.setStartX(a);
                            tmpSeg.setStartY(b);
                            tmpSeg.setEndX(c);
                            tmpSeg.setEndY(d);
                        } else {
                            page.redraw(gc);
                            tmpSeg.setStartX(tmp.getCenterX());
                            tmpSeg.setStartY(0);
                            tmpSeg.setEndX(tmp.getCenterX());
                            tmpSeg.setEndY(pane.getHeight());
                        }
                        tmpSeg.endNode = tmp;
                        tmp.adjacentSegment.add(tmpSeg);
                        tmpSeg.endNode.adjacentLine.add((StraightLine) tmpSeg);
                        tmp.toFront();
                        page.redraw(gc);
                    }
                } else if (key == 3 && onNode == false)// perpendicular ........
                {
                    if (segmentStarted == true && onNode == false) {
                        tmpSeg.singleNode = tmp;
                        if (tmpSeg.parentSegment.getEndX() == tmpSeg.parentSegment.getEndY()) {
                            tmpSeg.setStartX(tmp.getCenterX());
                            tmpSeg.setStartY(0);
                            tmpSeg.setEndX(tmp.getCenterX());
                            tmpSeg.setEndY(pane.getHeight());
                        } else {
                            if (tmpSeg.parentSegment.getStartX() == tmpSeg.parentSegment.getEndX()) {
                                tmpSeg.slope = 0;
                            } else {
                                tmpSeg.slope = -1. / tmpSeg.parentSegment.slope;
                            }

                            double a, b, c, d;
                            a = 0;
                            b = tmpSeg.slope * (a - tmp.getCenterX()) + tmp.getCenterY();
                            c = pane.getWidth();
                            d = tmpSeg.slope * (c - tmp.getCenterX()) + tmp.getCenterY();
                            tmpSeg.setStartX(a);
                            tmpSeg.setStartY(b);
                            tmpSeg.setEndX(c);
                            tmpSeg.setEndY(d);
                        }
                        segmentStarted = false;
                        tmpSeg.singleNode.adjacentperpendicular.add((PerpendicularLine) tmpSeg);
                    }
                    tmp.toFront();
                } else if (key == 11 && onNode == false) {
                    Ray.addRay(tmp);
                    onNode = true;
                } else if (key == 7 && onNode == false) {
                    AngleBisector.drawAngle(tmp);
                    onNode = true;
                } else if (key == 8 && onNode == false) {
                    onNode = true;
                    Area.takeNode(tmp, FXMLDocumentController.are);

                } else if (key == 9 && onNode == false) {
                    onNode = true;
                    Angle.evaluateAngle(tmp);
                } else if (key == 10 && onNode == false) {
                    if (segmentStarted == false && onNode == false) {
                        onNode = true;
                        tmpCircle = new GeoCircle(tmp);
                        segmentStarted = true;
                        tmp.adjacentCircle.add(tmpCircle);
                        tmp.toFront();
                    } else if (segmentStarted == true && onNode == false) {
                        onNode = true;
                        tmpCircle.boundNode = tmp;
                        double xx = ((tmpCircle.boundNode.getCenterX() - tmpCircle.centerNode.getCenterX()));
                        xx *= xx;
                        double yy = ((tmpCircle.boundNode.getCenterY() - tmpCircle.centerNode.getCenterY()));
                        yy *= yy;
                        tmpCircle.setRadius(Math.sqrt(xx + yy));
                        tmp.adjacentCircle.add(tmpCircle);
                        tmpCircle.r = tmpCircle.getRadius() * Grid.unitOfScale / Grid.increment;
                        tmpCircle.rr.format("%.2f", tmpCircle.r);
                        FXMLDocumentController.circleTable.getItems().add(tmpCircle);
                        segmentStarted = false;
                        tmp.toFront();
                    }

                }
                else if(key == 30 && onNode ==false)
                {
                    onNode = true;
                    MidPoint.evaluateMidPoint(tmp);
                }
                listPane.toFront();
            }
        });
    }

}
