package mygeocal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableView;
import impl.org.controlsfx.skin.DecorationPane;
import static java.io.File.separator;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import properties.NodeProperties;

/**
 *
 * @author HP
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    public static AnchorPane pane, listPane, root, topPane, pointPane;
    @FXML
    public static JFXTabPane tabPane;
    @FXML
    public static SplitPane spltPane;
    @FXML
    public static Button zoomInButton, zoomOutButton, newNodeButton;
    @FXML
    public static ToggleButton Button;
    @FXML
    public static GraphicsContext gc;
    @FXML
    public static JFXToggleButton GP;
    @FXML
    public static ToolBar toolBar;
    @FXML
    public static Canvas canvas;
    @FXML
    public static Image segmentImage, segmentImageClicked;
    @FXML
    public static Tooltip selectTT, newNodeTT, segmentTT, graphPaperTT;
    @FXML
    public static Separator separator;
    @FXML
    public static DecorationPane front;

    @FXML
    public static TableColumn nameColumn, coColumnX, coColumnY, circleName, circleCenterX, circleCenterY, circleRadius;
    @FXML
    public static TableColumn segmentName,segmentStart,segmentEnd;
    @FXML
    public static TableColumn lineName,lineEquation;
    public static Stage stage;
    public static Segment temporarySegment;
    public static ArrayList<Segment> segments = new ArrayList<>();

    // Variables............
    static boolean willGraphPaperExist, everythingIsNormal, moveGrid = true;
    public static int key = -1;
    public static int count = 0;
    double positionOfXAxis;
    double positionOfYAxis;
    double unitOfScale;
    double increment;
    double mousePressedX;
    double mousePressedY;
    double dX, dY;
    public static Grid page;
    static double a, b, c, d;
    public static VBox sideBar;

    public void fadeInTransition() {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setNode(pane);
        fade.setFromValue(0.5);
        fade.setToValue(1);
        fade.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        fadeInTransition();
        Side.decorateSidePane();
        gc = canvas.getGraphicsContext2D();
        dX = canvas.getLayoutX();
        dY = canvas.getLayoutY();
        page = new Grid(gc, pane, dX, dY);
        everythingIsNormal = true;
        imageLoad();
        actions();
        page.redraw(gc);
        keyVal[1] = 1;
        keyVal[2] = 3; // segment
        keyVal[3] = 10; // circle
        keyVal[4] = 15; // ellips
        keyVal[5] = 9; // angle
        listPane.toFront();
    }

    void actions() {
        canvas.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (key == 0 || key == 1 || key == 2) {
                    canvas.setCursor(Cursor.CROSSHAIR);
                } else {
                    canvas.setCursor(Cursor.CLOSED_HAND);
                }
            }
        });
        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (key == 1 && segmentStarted == true) {
                    canvas.setCursor(Cursor.CROSSHAIR);
                    tmpSeg.setEndX(event.getX() + dX);
                    tmpSeg.setEndY(event.getY() + dY);
                    tmpSeg.slope = (tmpSeg.startNode.Y - tmpSeg.getEndY()) / ((tmpSeg.startNode.X - tmpSeg.getEndX()));
                    page.redraw(gc);
                } else if (key == 2 && segmentStarted == true) {
                    canvas.setCursor(Cursor.CROSSHAIR);
                    FXMLDocumentController.event = event;
                    tmpSeg.setEndX(event.getX() + dX);
                    tmpSeg.setEndY(event.getY() + dY);
                    if (tmpSeg.startNode.getCenterX() != event.getX() + dX) {
                        tmpSeg.slope = ((event.getY() + dY) - tmpSeg.startNode.getCenterY()) / ((event.getX() + dX) - tmpSeg.startNode.getCenterX());
                        a = 0;
                        b = tmpSeg.slope * (a - tmpSeg.startNode.getCenterX()) + tmpSeg.startNode.getCenterY();
                        c = pane.getWidth();
                        d = tmpSeg.slope * (c - tmpSeg.startNode.getCenterX()) + tmpSeg.startNode.getCenterY();
                        tmpSeg.setStartX(a);
                        tmpSeg.setStartY(b);
                        tmpSeg.setEndX(c);
                        tmpSeg.setEndY(d);
                    } else {
                        tmpSeg.setStartX(event.getX() + dX);
                        tmpSeg.setStartY(0);
                        tmpSeg.setEndX(event.getX() + dX);
                        tmpSeg.setEndY(pane.getHeight());
                    }
                    page.redraw(gc);
                    toolBar.toFront();
                } else if (key == 3 && segmentStarted == true) {
                    if (tmpSeg.parentSegment.getEndX() == tmpSeg.parentSegment.getEndY()) {
                        tmpSeg.setStartX(event.getX() + dX);
                        tmpSeg.setStartY(0);
                        tmpSeg.setEndX(event.getX() + dX);
                        tmpSeg.setEndY(pane.getHeight());
                    } else {
                        if (tmpSeg.parentSegment.getStartX() == tmpSeg.parentSegment.getEndX()) {
                            tmpSeg.slope = 0;
                        } else {
                            tmpSeg.slope = -1. / tmpSeg.parentSegment.slope;
                        }

                        double a, b, c, d;
                        a = 0;
                        b = tmpSeg.slope * (a - event.getX() + dX) + dY + event.getY();
                        c = pane.getWidth();
                        d = tmpSeg.slope * (c - event.getX() + dX) + dY + event.getY();
                        tmpSeg.setStartX(a);
                        tmpSeg.setStartY(b);
                        tmpSeg.setEndX(c);
                        tmpSeg.setEndY(d);
                    }
                } else if (key == 10 && segmentStarted == true) {
                    for (int i = 0; i < Node.nodeList.size(); i++) {
                        Node.nodeList.get(i).toFront();
                    }
                    Node.redraw();
                    double xx = ((event.getX() + dX - tmpCircle.centerNode.getCenterX()));
                    xx *= xx;
                    double yy = ((event.getY() + dY - tmpCircle.centerNode.getCenterY()));
                    yy *= yy;
                    tmpCircle.setRadius(Math.sqrt(xx + yy));
                }
                page.redraw(gc);
            }

        });
    }
    public static MouseEvent event;

    @FXML
    public static void mouseInSidebar() {
        page.redraw(gc);
        toolBar.toFront();
    }
    @FXML
    public JFXTextField yTextField, xTextField, nameTextField;
    @FXML
    public static TableView pointTable, segmentTable, lineTable,circleTable;
    @FXML
    public static JFXButton  selectBtn, btn0,btn1, btn2, btn3, btn4, btn5, btn7,btn8, btn11, btn12, area,clear,midPointButton;
    public static MenuItem segmentItem, straightLineItem, perpendicularLineItem, perpendicularBisectorItem;
    @FXML
    public static ContextMenu contextMenu01, contextMenu02;

    public void imageLoad() {
        zoomInButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/zoomIn.png"))));
        zoomOutButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/zoomOut.png"))));
        selectBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Select Selected.png"))));
        btn1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Segment.png"))));
        btn0.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/New Node.png"))));
        btn2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Perpendicular.png"))));
        btn3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Circle.png"))));
        btn4.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Ellipse.png"))));
        btn5.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Angle.png"))));
        btn7.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/AngleBisector.png"))));
        btn8.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Ray.png"))));
        area.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Area.png"))));
        clear.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Clear.jpg"))));
        midPointButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Midpoint.png"))));
        segmentItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Segment.png"))));
        straightLineItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Line.png"))));
        perpendicularLineItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Perpendicular.png"))));
        perpendicularBisectorItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Bisector.png"))));

    }

    public void clear() {
        page.redraw(gc);
    }

    public void createPointPressed() {
        page.redraw(gc);
        count++;
        Double xd = Double.parseDouble(xTextField.getText().toString());
        Double yd = Double.parseDouble(yTextField.getText().toString());
        double xxd = xd;
        double yyd = yd;
        System.out.println(increment + " " + unitOfScale);
        double x = (((xxd) * Grid.increment / Grid.unitOfScale) + Grid.positionOfYAxis);
        double y = (-((yyd) * Grid.increment / Grid.unitOfScale) + Grid.positionOfXAxis + Grid.dY);
//        double x=xxd*increment;
//        double y=yyd*increment;

        System.out.println(x + " " + y);
        Node point = new Node(x, y);
        if (count != 0 && equation != null) {
            equation.addNode(point);
        }
        if (equation != null && count == equation.numberOfNode) {
            equation.analyse();
        }

    }
    public static int keyVal[] = new int[20];

    // keyVal -> 1 er under e
    public void segmentPressed() {
        unselect();
        keyVal[1] = 1;
        key = 1;
        btn1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Segment Selected.png"))));
//        menuIV01.setImage(new Image(getClass().getResourceAsStream("/image/Segment Selected.png")));
    }

    // keyVal -> 1 er under e
    public void straightLinePressed() {
        unselect();
        keyVal[1] = 2;
        key = 2;
        btn1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Line Selected.png"))));
    }

    public void perpendicularLinePressed() {
        unselect();
        keyVal[2] = 3;
        key = 3;
        btn2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Perpendicular Selected.png"))));
    }

    public void bisectorItemPressed() {
        unselect();
        keyVal[2] = 5;
        key = 5;
        btn2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Bisector Selected.png"))));
    }

    @FXML
    public  void unselect() {
        selectBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Select.png"))));
        btn0.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/New Node.png"))));
        if (keyVal[1] == 1) {
            btn1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Segment.png"))));
        } else if (keyVal[1] == 2) {
            btn1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Line.png"))));
        }
        if (keyVal[2] == 3) {
            btn2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Perpendicular.png"))));
        } else if (keyVal[2] == 5) {
            btn2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Bisector.png"))));
        }
        if (keyVal[3] == 10) {
            btn3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Circle.png"))));
        }
        btn4.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Ellipse.png"))));
        btn5.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Angle.png"))));
        btn7.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/AngleBisector.png"))));
        btn8.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Ray.png"))));
        midPointButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Midpoint.png"))));
        moveGrid = true;
        Grid.bug1 = false;
        segmentStarted = false;
        onNode = false;
    }

    @FXML
    public void selectBtnPressed() {
        unselect();
        key = -1;
        selectBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Select Selected.png"))));
        allRedraw();
    }
    @FXML
    public void midPointButtonPressed(){
        unselect();
        key = 30;
        midPointButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Midpoint Selected.png"))));
    }
    @FXML
    public void btn0Pressed() {
        unselect();
        key = 0;
        btn0.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/New Node Selected.png"))));
        allRedraw();
    }

    @FXML
    public void btn1Pressed() {
        unselect();
        key = keyVal[1];
        if (keyVal[1] == 1) {
            btn1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Segment Selected.png"))));
        } else if (keyVal[1] == 2) {
            btn1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Line Selected.png"))));
        }
        allRedraw();
    }

    @FXML
    public void btn2Pressed() {
        unselect();
        key = keyVal[2];
        if (keyVal[2] == 3) {
            btn2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Perpendicular Selected.png"))));
        } else if (keyVal[2] == 5) {
            btn2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Bisector Selected.png"))));
        }
        allRedraw();

    }

    @FXML
    public void btn3Pressed() {
        unselect();
        key = keyVal[3];
        if (keyVal[3] == 10) {
            btn3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Circle Selected.png"))));
        }
        allRedraw();
    }

    @FXML
    public void btn4Pressed() {
        unselect();
        key = keyVal[4];
        btn4.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Ellipse Selected.png"))));
        allRedraw();
    }

    @FXML
    public void btn5Pressed() {
        unselect();
        key = keyVal[5];
        btn5.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Angle Selected.png"))));
        allRedraw();
    }


    @FXML
    public void btn6Pressed() {
        unselect();
//        System.out.println("button6");
        are=new Area();
        key = 8; /// Finding Area;
        allRedraw();
    }

    @FXML
    public void btn7Pressed() {
        unselect();
        key = 7;//AngleBisector
        btn7.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/AngleBisector Selected.png"))));
        allRedraw();
    }
    @FXML
    public void btn8Pressed(){
        unselect();
        key = 11;//Ray
        btn8.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Ray Selected.png"))));
//        are=new Area();
    }

    @FXML
    public void btn11Pressed() throws IOException {
        unselect();
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        root = FXMLLoader.load(getClass().getResource("EquationInputBox.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.toFront();
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        selectBtnPressed();
        page.redraw(gc);
    }

//    @FXML
//    public void btn12Pressed() throws IOException {
//        unselect();
//        key = 12;
//        equation = new GetEquation();
//        page.redraw(gc);
//    }
    
    @FXML
    public void clearEverythingPressed(){
        unselect();
        
        AngleBisector.angleBisectorList.clear();
        Bisector.bisectorList.clear();
        GeoCircle.geoCircleList.clear();
        GeoEllipse.ellipseList.clear();
        Node.nodeList.clear();
        PerpendicularLine.perpendicularLineList.clear();
        Ray.rayList.clear();
        Segment.segmentList.clear();
        Side.circleData.clear();
        Side.pointData.clear();
        Side.segmentData.clear();
        StraightLine.straightLineList.clear();
        pane.getChildren().clear();
        pane.getChildren().addAll(toolBar,listPane,separator,canvas);
        pane.getChildren().addAll(Grid.xAxis,Grid.yAxis);
                
    }

    public GraphicsContext getGc() {
        return gc;
    }

    @FXML
    public void zoomInButtonPressed(ActionEvent event) {
        page.zoomInButtonPressed(event);
        allRedraw();
    }

    @FXML
    public void scrollZoom(ScrollEvent event) {
        page.scrollZoom(event);
        allRedraw();
    }

    @FXML
    public void zoomOutButtonPressed(ActionEvent event) {
        page.zoomOutButtonPressed(event);
        allRedraw();
    }

    @FXML
    public void graphPaperButtonPressed(ActionEvent event) {
        page.graphPaperButtonPressed(event);
        toolBar.toFront();
        listPane.toFront();
    }

    void clearCanvas(GraphicsContext gc) {
        gc.clearRect(gc.getCanvas().getBoundsInLocal().getMinX(), gc.getCanvas().getBoundsInLocal().getMinY(), gc.getCanvas().getBoundsInLocal().getMaxX(), gc.getCanvas().getBoundsInLocal().getMaxY());
    }

    public void mousePressedPosition(MouseEvent event) {
        listPane.toFront();
        if (Grid.bug1) {
            Grid.bug1 = false;
            moveGrid = true;
//            return;
        }
        if (moveGrid == false) {
//            moveGrid = true;
            return;
        }

        if (key == -1) // mouse pressed normaly....
        {
            page.tmpX = page.positionOfXAxis;
            page.tmpY = page.positionOfYAxis;
            page.mousePressedX = event.getX();
            page.mousePressedY = event.getY();
        }

    }

    public static boolean segmentStarted = false, onNode = false;
    public static Segment tmpSeg;
    public static GetEquation equation;

    public void mouseClicked(MouseEvent event) {
        listPane.toFront();
        if (Grid.bug1) {
            Grid.bug1 = false;
            moveGrid = true;
        }
        if (moveGrid == false) {
//            moveGrid = true;
            return;
        }
        toolBar.toFront();
        page.redraw(gc);
        if (key == 0) {
            if (onNode == false) {
                Node point = Node.addNode(event);
                
            }
        } else if (key == 1) {
            if (segmentStarted == false && onNode == false) {
                tmpSeg = new Segment(event);
                segmentStarted = true;
                tmpSeg.startNode.adjacentSegment.add(tmpSeg);
            } else if (segmentStarted == true && onNode == false) {
                tmpSeg.setEndX(event.getX());
                tmpSeg.setEndY(event.getY());
                tmpSeg.endNode = Node.addNode(event);
                tmpSeg.endNode.adjacentSegment.add(tmpSeg);
                tmpSeg.name += tmpSeg.endNode.name.getText() ;
                tmpSeg.xx.format("%.2f", (tmpSeg.endNode.getCenterX()-Grid.positionOfYAxis)*Grid.unitOfScale/Grid.increment);
                tmpSeg.yy.format("%.2f", (Grid.positionOfXAxis - tmpSeg.endNode.getCenterY() + Grid.dY) * Grid.unitOfScale / Grid.increment);
                tmpSeg.end = "(" + tmpSeg.xx + "," + tmpSeg.yy+")";
                segmentTable.getItems().add(tmpSeg);
                segmentStarted = false;
                Segment.redraw();
            }
        } else if (key == 2) {
            if (segmentStarted == false && onNode == false) {
                tmpSeg = new StraightLine(event);
                segmentStarted = true;
                tmpSeg.startNode.adjacentLine.add((StraightLine) tmpSeg);
            } else if (segmentStarted == true && onNode == false) {
                tmpSeg.endNode = Node.addNode(event);
                tmpSeg.endNode.adjacentLine.add((StraightLine) tmpSeg);
                Node.redraw();
                segmentStarted = false;
                StraightLine.redraw();
            }
        } else if (key == 3) {
            if (segmentStarted == true && onNode == false) {
                tmpSeg.singleNode = Node.addNode(event);
                tmpSeg.singleNode.adjacentperpendicular.add((PerpendicularLine) tmpSeg);

                segmentStarted = false;
                PerpendicularLine.redraw();
            }

        }else if(key == 6 && onNode == false){
            Ray.addRay(event);
        }
        
        else if (key == 7 && onNode == false) {

            AngleBisector.drawAngle(event);

        } else if (key == 8 && onNode == false) {

            Area.takeNode(event, are);
            
        } else if (key == 9 && onNode == false) {
            Angle.evaluateAngle(event);

        } else if (key == 10) {
            if (segmentStarted == false && onNode == false) {
                tmpCircle = new GeoCircle(event);
                segmentStarted = true;
                tmpCircle.centerNode.adjacentCircle.add(tmpCircle);

            } else if (segmentStarted == true && onNode == false) {
                tmpCircle.boundNode = Node.addNode(event);
                double xx = ((tmpCircle.boundNode.getCenterX() - tmpCircle.centerNode.getCenterX()));
                xx *= xx;
                double yy = ((tmpCircle.boundNode.getCenterY() - tmpCircle.centerNode.getCenterY()));
                yy *= yy;
                tmpCircle.setRadius(Math.sqrt(xx + yy));
                segmentStarted = false;
                tmpCircle.boundNode.adjacentCircle.add(tmpCircle);
                tmpCircle.r = tmpCircle.getRadius() * Grid.unitOfScale / Grid.increment;
                tmpCircle.rr.format("%.2f", tmpCircle.r);
                FXMLDocumentController.circleTable.getItems().add(tmpCircle);
                GeoCircle.redraw();
            }
            
        } 
        else if(key==11 && onNode==false)
        {
            Ray.addRay(event);
        }
        
        else if (key == 15 && onNode == false) {
            if (GeoEllipse.click == 0) {
                tmpellipse = GeoEllipse.addEllipse(event);
            } else {
                GeoEllipse.modifyEllipse(tmpellipse, event);
            }

        }
        else if(key == 30 && onNode == false)
        {
            MidPoint.evaluateMidPoint(event);
        }

        onNode = false;
//        page.redraw(gc);
        Node.redraw();
    }
    public static GeoCircle tmpCircle;
    public static GeoEllipse tmpellipse;
    public static Area are;
    public void mouseDragged(MouseEvent event) {
        listPane.toFront();
        if (key == -1 && moveGrid) {
            page.changeAxes(event.getX(), event.getY());
            allRedraw();
            toolBar.toFront();
        }
    }

    public static void allRedraw() {
        Node.redraw();
        Segment.redraw();
        Equation.redraw();
        StraightLine.redraw();
        PerpendicularLine.redraw();
        GeoCircle.redraw();
        Bisector.redraw();
        GeoEllipse.redraw();
        AngleBisector.redraw();
        Ray.redraw();
        Area.redraw();
        page.redraw(gc);
        toolBar.toFront();
        listPane.toFront();
    }

    void changeAxes(double x, double y) {
        listPane.toFront();
        page.changeAxes(x, y);
    }
}
