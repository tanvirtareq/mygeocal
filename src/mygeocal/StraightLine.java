package mygeocal;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import static mygeocal.FXMLDocumentController.event;
import static mygeocal.FXMLDocumentController.gc;
import static mygeocal.FXMLDocumentController.key;
import static mygeocal.FXMLDocumentController.listPane;
import static mygeocal.FXMLDocumentController.onNode;
import static mygeocal.FXMLDocumentController.page;
import static mygeocal.FXMLDocumentController.pane;
import static mygeocal.FXMLDocumentController.segmentStarted;
import static mygeocal.FXMLDocumentController.stage;
import static mygeocal.FXMLDocumentController.tmpSeg;
import static mygeocal.FXMLDocumentController.toolBar;
import static mygeocal.Grid.dX;
import static mygeocal.Grid.dY;

/**
 *
 *
 * @Kiriti
 */
public class StraightLine extends Segment {

    public double lineStartX, lineStartY, lineEndX, lineEndY;
    public static ArrayList<StraightLine> straightLineList = new ArrayList<>();

    public StraightLine() {

    }

    public StraightLine(double startX, double startY) {
        super(startX, startY);

    }

    public StraightLine(MouseEvent event) {
        this(event.getX(), event.getY());
        startNode = Node.addNode(event);
        straightLineList.add(this);
    }

    public StraightLine(Node startNode) {
        this(startNode.getCenterX(), startNode.getCenterY());
        this.startNode = startNode;
        straightLineList.add(this);
        startNode.toFront();
    }

    void actions(StraightLine tmp) {
        delete.setOnAction(e -> {
            tmp.deleteStraightLine();

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

    public void deleteStraightLine() {
        for (int i = 0; i < childPerpendicular.size(); i++) {
            childPerpendicular.get(i).deletePerpedicularLine();
        }
        childPerpendicular.clear();
        straightLineList.remove(this);
        Grid.pane.getChildren().remove(this);
    }

    public static void redraw() {
        listPane.toFront();
        double a, b, c, d;
        Node start, end;
        for (int i = 0; i < straightLineList.size(); i++) {
            calculateSlope(straightLineList.get(i));
            if (straightLineList.get(i).endNode == null) {
                straightLineList.get(i).setStartX(straightLineList.get(i).startNode.getCenterX());
                straightLineList.get(i).setStartY(straightLineList.get(i).startNode.getCenterY());
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
                continue;
            }
            start = straightLineList.get(i).startNode;
            end = straightLineList.get(i).endNode;
            if (start.getCenterX() != end.getCenterX()) {
                a = 0;
                straightLineList.get(i).slope = (end.getCenterY() - start.getCenterY()) / (end.getCenterX() - start.getCenterX());
                b = straightLineList.get(i).slope * (a - start.getCenterX()) + start.getCenterY();
                c = FXMLDocumentController.pane.getWidth();
                d = straightLineList.get(i).slope * (c - start.getCenterX()) + start.getCenterY();
                straightLineList.get(i).setStartX(a);
                straightLineList.get(i).setStartY(b);
                straightLineList.get(i).setEndX(c);
                straightLineList.get(i).setEndY(d);
            } else {
                straightLineList.get(i).setStartX(start.getCenterX());
                straightLineList.get(i).setStartY(0);
                straightLineList.get(i).setEndX(start.getCenterX());
                straightLineList.get(i).setEndY(FXMLDocumentController.pane.getHeight());
            }
            
        }
    }

}
