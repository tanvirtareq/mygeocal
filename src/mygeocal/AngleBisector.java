/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.scilab.forge.jlatexmath.PredefMacros;

/**
 *
 * @author Tanvir Tareq
 */
public class AngleBisector extends Segment {

    public static int click = 0;

    double slope;

    private static double evaluateAngle(Node n1, Node n2) {
        try {
            return Math.atan((n1.getCenterY() - n2.getCenterY()) / (n1.getCenterX() - n2.getCenterX()));
        } catch (Exception e) {

            return Math.toRadians(90);
        }

    }
    private double temsx, temsy, temex, temey;

    public Node startNode, middleNode, endNode,finalNode;
    public static AngleBisector line;
    double angle;
    public static ArrayList<AngleBisector> angleBisectorList = new ArrayList<>();

    private AngleBisector() {
//        super.setStrokeWidth(2.5);
//        
        

    }

    public static void drawAngle(MouseEvent event) {
        if (click == 0) {
            click = 1;
            line = new AngleBisector();
            line.startNode = Node.addNode(event);

        } else if (click == 1) {
            click = 2;
            line.middleNode = Node.addNode(event);

        } else if (click == 2) {
            click = 0;
            line.endNode = Node.addNode(event);
            AngleBisector temp = line;
            line = null;
            angleBisectorList.add(temp);

            double m1 = dist(temp.middleNode, temp.startNode);
            double m2 = dist(temp.middleNode, temp.endNode);
            System.out.println(m1 + " " + m2);

            double x = (m1 * temp.endNode.getCenterX() + m2 * temp.startNode.getCenterX()) / (m1 + m2);
            double y = (m1 * temp.endNode.getCenterY() + m2 * temp.startNode.getCenterY()) / (m1 + m2);
//            System.out.println((m1 * temp.endNode.getCenterX() + " " + m2 * temp.startNode.getCenterX()));
            System.out.println(x + " " + y);
            
            temp.finalNode = new Node(x,y);
//            
//            temp.setStartX(temp.middleNode.getCenterX());
//            temp.setStartY(temp.middleNode.getCenterY());
//            
            temp.setEndX(x);
            temp.setEndY(y);
//
//            temp.temsx = temp.getStartX();
//            temp.temsy = temp.getStartY();
//            temp.temex = temp.getEndX();
//            temp.temey = temp.getEndY();
//
//            temp.calculate();

        }

    }

    public static void drawAngle(Node node) {
        if (click == 0) {
            click = 1;
            line = new AngleBisector();
            line.startNode = node;

        } else if (click == 1) {
            click = 2;
            line.middleNode = node;

        } else if (click == 2) {
            click = 0;
            line.endNode = node;
            AngleBisector temp = line;
            line = null;
            angleBisectorList.add(temp);

            double m1 = dist(temp.middleNode, temp.startNode);
            double m2 = dist(temp.middleNode, temp.endNode);
            System.out.println(m1 + " " + m2);

            double x = (m1 * temp.endNode.getCenterX() + m2 * temp.startNode.getCenterX()) / (m1 + m2);
            double y = (m1 * temp.endNode.getCenterY() + m2 * temp.startNode.getCenterY()) / (m1 + m2);
//            System.out.println((m1 * temp.endNode.getCenterX() + " " + m2 * temp.startNode.getCenterX()));
            System.out.println(x + " " + y);

            temp.finalNode = new Node(x,y);
            
            temp.setStartX(temp.middleNode.getCenterX());
            temp.setStartY(temp.middleNode.getCenterY());
//            
            temp.setEndX(x);
            temp.setEndY(y);
//
//            temp.temsx = temp.getStartX();
//            temp.temsy = temp.getStartY();
//            temp.temex = temp.getEndX();
//            temp.temey = temp.getEndY();
//
//            temp.calculate();

        }
    }

    public static void redraw() {

        AngleBisector tmp;
        for(int i=0;i<angleBisectorList.size();++i)
        {
//            angleBisectorList.get(i).calculate();
           tmp =  angleBisectorList.get(i);
           tmp.setStartX(tmp.middleNode.getCenterX());
           tmp.setStartY(tmp.middleNode.getCenterY());
           tmp.setEndX(tmp.finalNode.getCenterX());
           tmp.setEndY(tmp.finalNode.getCenterY());
        }
    }

    private void calculate() {
        
        this.temsx = this.getStartX();
            this.temsy = this.getStartY();
            this.temex = this.getEndX();
            this.temey = this.getEndY();

        if (this.temsx == this.temex && this.temsy == this.temey) {
            
            if ((this.startNode.getCenterX() - this.endNode.getCenterX()) == 0) {
                setStartX(0);
                setStartY(middleNode.getCenterY());

                setEndX(FXMLDocumentController.pane.getWidth());
                setEndY(middleNode.getCenterY());
                
                return;

            }

            if ((this.startNode.getCenterY() - this.endNode.getCenterY()) == 0) {
                setStartX(middleNode.getCenterX());
                setStartY(0);

                setEndX(middleNode.getCenterX());
                setEndY(FXMLDocumentController.pane.getHeight());
                return;

            }

            double slope = -((this.startNode.getCenterX() - this.endNode.getCenterX()) / (this.startNode.getCenterY() - this.endNode.getCenterY()));

            double a, b, c, d;
            a = 0;
            b = slope* (a - middleNode.getCenterX()) + middleNode.getCenterY();
            setStartX(a);
            c = FXMLDocumentController.pane.getWidth();
//            System.out.println(Math.tan(angle));
            d = slope * (c - middleNode.getCenterX()) + middleNode.getCenterY();
            setStartY(b);
            setEndX(c);
            setEndY(d);
            System.out.println("co linear");
//            
            return;
        }
        
        if ((this.temsx - this.temex) == 0) {
                setStartX(this.middleNode.getCenterX());
                setStartY(0);

                setEndX(this.middleNode.getCenterX());
                setEndY(FXMLDocumentController.pane.getHeight());
                return;

            }

            if ((this.temsy - this.temey) == 0) {
                setStartX(0);
                setStartY(this.middleNode.getCenterY());

                setEndX(FXMLDocumentController.pane.getWidth());
                setEndY(this.middleNode.getCenterY());
                return;

            }

            double slope = ((this.temsy - this.temey) / (this.temsx - this.temex));

            double a, b, c, d;
            a = 0;
            b = slope* (a - middleNode.getCenterX()) + middleNode.getCenterY();
            setStartX(a);
            c = FXMLDocumentController.pane.getWidth();
//            System.out.println(Math.tan(angle));
            d = slope * (c - middleNode.getCenterX()) + middleNode.getCenterY();
            setStartY(b);
            setEndX(c);
            setEndY(d);
//            
            return;
//
//
//        try {
//            System.out.println(Math.toDegrees(Math.tan(angle)));
//
//        } catch (Exception e) {
//            setStartX(middleNode.getCenterX());
//            setStartY(0);
//
//            setEndX(middleNode.getCenterX());
//            setEndY(FXMLDocumentController.pane.getHeight());
//            return;
//        }
//        double a, b, c, d;
//        a = 0;
//        b = Math.tan(angle) * (a - middleNode.getCenterX()) + middleNode.getCenterY();
//        setStartX(a);
//        c = FXMLDocumentController.pane.getWidth();
//        System.out.println(Math.tan(angle));
//        d = Math.tan(angle) * (c - middleNode.getCenterX()) + middleNode.getCenterY();
//        setStartY(b);
//        setEndX(c);
//        setEndY(d);
    }

    private static double dist(Node a, Node b) {

        if ((a.getCenterY() - b.getCenterY()) * (a.getCenterY() - b.getCenterY()) == 0) {
            return Math.abs(a.getCenterX() - b.getCenterX());
        }
        return Math.sqrt((a.getCenterX() - b.getCenterX()) * (a.getCenterX() - b.getCenterX()) + (a.getCenterY() - b.getCenterY()) * (a.getCenterY() - b.getCenterY()));
    }

}
