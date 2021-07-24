package mygeocal;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
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

/**
 *
 * @Kiriti
 */
public class PerpendicularLine extends StraightLine {

    public static ArrayList<PerpendicularLine> perpendicularLineList = new ArrayList<>();
    public ArrayList<PerpendicularLine> adjacentPerpendicularLineList = new ArrayList<>();

    public PerpendicularLine(double X, double Y, Segment tmp) {
        super();
        parentSegment = tmp;
        tmp.childPerpendicular.add(this);
        perpendicularLineList.add(this);
        if (parentSegment.getStartY() == parentSegment.getEndY()) {
            this.setStartX(X);
            this.setStartY(0);
            this.setEndX(X);
            this.setEndY(pane.getHeight());
        } else {
            if (parentSegment.getStartX() == parentSegment.getEndX()) {
                this.slope = 0;
            } else {
                this.slope = -1. / parentSegment.slope;
            }
            double a, b, c, d;
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
    }
    
    //only for bisector to perpendicular
   
    
    void actions(PerpendicularLine tmp)
    {
        delete.setOnAction(e -> {
            tmp.deletePerpedicularLine();

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
    
    public void deletePerpedicularLine()
    {
         for(int i=0;i<adjacentPerpendicularLineList.size();i++)
         {
             adjacentPerpendicularLineList.get(i).deletePerpedicularLine();
         }
         adjacentPerpendicularLineList.clear();
         perpendicularLineList.remove(this);
         Grid.pane.getChildren().remove(this);
    }

    public static void redraw() {listPane.toFront();
        PerpendicularLine tmpSeg;
        Node tmp ;
        for (int i = 0; i < perpendicularLineList.size(); i++) {
//            calculateSlope(perpendicularLineList.get(i));
            tmpSeg = perpendicularLineList.get(i);
            tmp = tmpSeg.singleNode;
            System.out.println( tmpSeg.parentSegment.getStartY() - tmpSeg.parentSegment.getEndY() + "  " +(tmpSeg.parentSegment.getStartX() - tmpSeg.parentSegment.getEndX()) );
            if(tmpSeg.parentSegment.getStartY() == tmpSeg.parentSegment.getEndY() && tmpSeg.parentSegment.getStartX() == tmpSeg.parentSegment.getEndX())
            {
                tmpSeg.setStartX(tmp.getCenterX());
                tmpSeg.setStartY(0);
                tmpSeg.setEndX(tmp.getCenterX());
                tmpSeg.setEndY(pane.getHeight());
                continue;
            }
            
                
            if (tmpSeg.parentSegment.getStartY()- tmpSeg.parentSegment.getEndY() == 0.){
//                System.out.println("yo");
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
    }
}