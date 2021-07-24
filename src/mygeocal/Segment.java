package mygeocal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import static mygeocal.FXMLDocumentController.gc;
import static mygeocal.FXMLDocumentController.key;
import static mygeocal.FXMLDocumentController.listPane;
import static mygeocal.FXMLDocumentController.page;
import static mygeocal.FXMLDocumentController.stage;
import static mygeocal.FXMLDocumentController.tmpSeg;
import static mygeocal.FXMLDocumentController.onNode;
import static mygeocal.FXMLDocumentController.segmentStarted;
import static mygeocal.FXMLDocumentController.pane;
import static mygeocal.FXMLDocumentController.stage;
import static mygeocal.FXMLDocumentController.tmpSeg;
import static mygeocal.FXMLDocumentController.toolBar;
import properties.NodeProperties;
import properties.SegmentProperties;

/**
 *
 * @author HP
 */
public class Segment extends Line {

    // ei duita Perpendicular er jonno
    public Segment parentSegment;
    public Bisector parentBisector;
    public Node singleNode;
    public String name;
    public String start, end;
    public Formatter x = new Formatter();
    public Formatter y = new Formatter();
    public Formatter xx = new Formatter();
    public Formatter yy = new Formatter();

    public String getName() {
        return name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Node startNode, endNode;
    public static ArrayList<Segment> segmentList = new ArrayList();
    public ArrayList<PerpendicularLine> childPerpendicular = new ArrayList<>();
//    public ArrayList<Node> childNode  = new ArrayList<>();
    public Bisector childBisector;
    public ContextMenu segmentMenu;
    public MenuItem delete, properties;
    public double slope;

    public Segment() {
        super();
        this.setStroke(Color.BROWN);
        this.setStrokeWidth(2.5);
        segmentMenu = new ContextMenu();
        delete = new MenuItem("Delete");
        properties = new MenuItem("Properties");
        delete.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/delete_img.png"))));
        properties.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Node Properties.png"))));
        segmentMenu.getItems().addAll(delete, properties);
//        segmentMenu.setOpacity(1);
        Grid.pane.getChildren().add(this);
    }
    public void random()
    {
        Random rand=new Random();
        int x=rand.nextInt(5);
        if(x==0)
        {
            this.setStroke(Color.ROYALBLUE);
        }
        else if(x==1)
        {
            this.setStroke(Color.GREEN);
        }
        else if(x==2)
        {
            this.setStroke(Color.RED);
        }
        else if(x==3)
        {
            this.setStroke(Color.ORANGE);
        }
        else if(x==4)
        {
            this.setStroke(Color.DARKSALMON);
        }
    }
    public Segment(double startX, double startY) {
        this();
        this.setStartX(startX);
        this.setStartY(startY);
        this.setEndX(startX);
        this.setEndY(startY);
        Node.setTop();
        actions(this);
    }

    Segment(MouseEvent event) {
        this(event.getX(), event.getY());
        segmentList.add(this);
        startNode = Node.addNode(event);
        name = startNode.name.getText();
        System.out.println(name);
        x.format("%.2f", (startNode.getCenterX() - Grid.positionOfYAxis) * Grid.unitOfScale / Grid.increment);
        y.format("%.2f", (Grid.positionOfXAxis - startNode.getCenterY() + Grid.dY) * Grid.unitOfScale / Grid.increment);
        start = "(" + x + "," + y + ")";
    }

    Segment(Node startNode) {
        this(startNode.getCenterX(), startNode.getCenterY());
        this.startNode = startNode;
        segmentList.add(this);
        name = startNode.name.getText();
        x.format("%.2f", (startNode.getCenterX() - Grid.positionOfYAxis) * Grid.unitOfScale / Grid.increment);
        y.format("%.2f", (Grid.positionOfXAxis - startNode.getCenterY() + Grid.dY) * Grid.unitOfScale / Grid.increment);

        start = "(" + x + "," + y + ")";
        startNode.toFront();
    }

    public static void calculateSlope(Segment seg) {
        if (seg.getStartX() != seg.getEndX()) {
            seg.slope = (seg.getStartY() - seg.getEndY()) / (seg.getStartX() - seg.getEndX());
        }
    }

    public static void redraw() {
        listPane.toFront();
        for (int i = 0; i < segmentList.size(); i++) {
            calculateSlope(segmentList.get(i));
            segmentList.get(i).setStartX(segmentList.get(i).startNode.getCenterX());
            segmentList.get(i).setStartY(segmentList.get(i).startNode.getCenterY());
            if (segmentList.get(i).endNode == null) {
                continue;
            }
            segmentList.get(i).x = new Formatter();
            segmentList.get(i).y = new Formatter();
            segmentList.get(i).xx = new Formatter();
            segmentList.get(i).yy = new Formatter();
            segmentList.get(i).x.format("%.2f", (segmentList.get(i).startNode.getCenterX() - Grid.positionOfYAxis) * Grid.unitOfScale / Grid.increment);
            segmentList.get(i).y.format("%.2f", (Grid.positionOfXAxis - segmentList.get(i).startNode.getCenterY() + Grid.dY) * Grid.unitOfScale / Grid.increment);
            segmentList.get(i).xx.format("%.2f", (segmentList.get(i).endNode.getCenterX() - Grid.positionOfYAxis) * Grid.unitOfScale / Grid.increment);
            segmentList.get(i).yy.format("%.2f", (Grid.positionOfXAxis - segmentList.get(i).endNode.getCenterY() + Grid.dY) * Grid.unitOfScale / Grid.increment);
            FXMLDocumentController.segmentTable.getItems().set(Side.segmentData.indexOf(segmentList.get(i)),segmentList.get(i));
            segmentList.get(i).setEndX(segmentList.get(i).endNode.getCenterX());
            segmentList.get(i).setEndY(segmentList.get(i).endNode.getCenterY());
            
            
        }
    }

    public void deleteSegment() {
        for (int i = 0; i < childPerpendicular.size(); i++) {
            childPerpendicular.get(i).deletePerpedicularLine();
        }
//        for(int i=0;i<childNode.size();i++)
//        {
//            System.out.println("hoise");
//            childNode.get(i).deleteNode();
//        }

        if (childBisector != null) {
            childBisector.deleteBisector();
        }
        childPerpendicular.clear();
        segmentList.remove(this);
//        childNode.clear();
        Grid.pane.getChildren().remove(this);
    }
    public static Segment bokri;

    void actions(Segment tmp) {
        delete.setOnAction(e -> {
            tmp.deleteSegment();

        });
        tmp.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if ((key == 1 || key == 2 || key == 10) && (segmentStarted == true)) {
                    tmp.setCursor(Cursor.CROSSHAIR);
                } else {
                    tmp.setCursor(Cursor.HAND);
                }
            }
        });
        properties.setOnAction(e -> {
            SegmentProperties Box = new SegmentProperties();
            try {
                Box.go(tmp);
            } catch (IOException ex) {
                Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        tmp.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            public void handle(ContextMenuEvent event) {
                segmentMenu.show(tmp, stage.getX() + event.getX(), stage.getY() + event.getY());
                FXMLDocumentController.moveGrid = false;
                Grid.bug1 = true;
            }
        });
        tmp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                if(key == 0 && onNode == false)
//                {
//                    childNode.add(Node.addNode(event));
//                    
//                }
                if (key == 3) {
                    if (segmentStarted == false && onNode == false) {
                        tmpSeg = new PerpendicularLine(event.getX(), event.getY(), tmp);
                        tmp.childPerpendicular.add((PerpendicularLine) tmpSeg);
                        segmentStarted = true;
                        onNode = true;
                    }
                } else if (key == 5) {
                    if (childBisector == null) {
                        childBisector = new Bisector(tmp);
                        toolBar.toFront();
                    }
                }
            }
        });

    }
}
