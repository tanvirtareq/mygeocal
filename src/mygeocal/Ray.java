/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author Tanvir Tareq
 */
public class Ray extends Line {

    public static int click = 0;

    Node startNode, endNode;
    double slope;
    boolean isSlopeUndefined = true;
    double angle;
    double referrenceX;
    public static ArrayList<Ray> rayList = new ArrayList<Ray>();
    static Ray ray;

    public static void addRay(MouseEvent event) {
        if (click == 0) {
            click = 1;
            Node sn = Node.addNode(event);

            ray = new Ray(sn, sn);
        } else if (click == 1) {
            click = 0;
            Node n = Node.addNode(event);
            ray.endNode = n;
            calculate(ray);

            rayList.add(ray);

            FXMLDocumentController.pane.getChildren().add(ray);

        }

    }

    public static void addRay(Node node) {
        if (click == 0) {
            click = 1;
            Node sn = node;
            Node en = node;
            ray = new Ray(sn, en);
        } else if (click == 1) {
            click = 0;
            Node n = node;
            ray.endNode = n;
            calculate(ray);

            rayList.add(ray);

            FXMLDocumentController.pane.getChildren().add(ray);

        }

    }

    public Ray(Node node, Node end) {
        setStrokeWidth(2.5);
        startNode = node;
        endNode = end;
        random();
        calculate(this);
        this.startNode.toFront();
        this.endNode.toFront();

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
            this.setStroke(Color.BROWN);
        }
    }
    public static void calculate(Ray aThis) {
        if ((aThis.startNode.getCenterX() - aThis.endNode.getCenterX()) == 0) {
            aThis.setStartX(aThis.startNode.getCenterX());
            aThis.setStartY(aThis.startNode.getCenterY());

            aThis.setEndX(aThis.startNode.getCenterX());
            if (aThis.startNode.getCenterY() >= aThis.endNode.getCenterY()) {
                aThis.setEndY(0);
            } else {
                aThis.setEndY(FXMLDocumentController.pane.getHeight());
            }
            return;

        }

        if ((aThis.startNode.getCenterY() - aThis.endNode.getCenterY()) == 0) {
            aThis.setStartX(aThis.startNode.getCenterX());
            aThis.setStartY(aThis.startNode.getCenterY());
            aThis.setEndY(aThis.startNode.getCenterY());

            if (aThis.startNode.getCenterX() >= aThis.endNode.getCenterX()) {
                aThis.setEndX(0);
            } else {
                aThis.setEndX(FXMLDocumentController.pane.getWidth());
            }

            return;

        }

        double slope = -((aThis.startNode.getCenterY() - aThis.endNode.getCenterY()) / (aThis.startNode.getCenterX() - aThis.endNode.getCenterX()));
        System.out.println("slope " + slope);
        double a, b, c = 0, d;
        aThis.setStartX(aThis.startNode.getCenterX());
        aThis.setStartY(aThis.startNode.getCenterY());

        if (aThis.startNode.getCenterX() <= aThis.endNode.getCenterX()) {
            c = FXMLDocumentController.pane.getWidth();
        }

        if (aThis.startNode.getCenterX() > aThis.endNode.getCenterX()) {
            c = 0;
        }

        d = -slope * (c - aThis.startNode.getCenterX()) + aThis.startNode.getCenterY();

        aThis.setEndX(c);
        aThis.setEndY(d);

        aThis.startNode.toFront();
        aThis.endNode.toFront();
//            
        return;
//
    }

    public static void redraw() {
        for (int i = 0; i < rayList.size(); ++i) {
            calculate(rayList.get(i));
        }
    }

}
