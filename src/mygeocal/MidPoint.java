/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import static mygeocal.MidPoint.midpoint;

/**
 *
 * @author Tanvir Tareq
 */
public class MidPoint extends Node{
    static int click=0;
    Node startNode, endNode;
    static MidPoint midpoint;
    static Node tempNode;
    static ArrayList<MidPoint> midpointList=new ArrayList<MidPoint> ();

    public MidPoint(double x, double y) {
        super(x, y);
        this.setFill(Color.RED);
    }
    
    public static void evaluateMidPoint(MouseEvent event)
    {
        if(click==0)
        {
            Node n=Node.addNode(event);
            
            tempNode=n;
            click=1;
        }
        else if(click==1)
        {
            click=0;
            
            Node n=Node.addNode(event);
            
            MidPoint mp=new MidPoint((tempNode.getCenterX()+n.getCenterX())/2, (tempNode.getCenterY()+n.getCenterY())/2);
           
            midpointList.add(mp);
            
        }
        
    }
    
    public static void evaluateMidPoint(Node n)
    {
         if(click==0)
        {
           
            
            tempNode=n;
            click=1;
        }
        else if(click==1)
        {
            click=0;
            
           
            
            MidPoint mp=new MidPoint((tempNode.getCenterX()+n.getCenterX())/2, (tempNode.getCenterY()+n.getCenterY())/2);
           
            midpointList.add(mp);
            
        }
       
        
    }
    
    
    public static  void redraw()
    {
        for(int i=0;i<midpointList.size();++i)
        {
            MidPoint mp=midpointList.get(i);
            mp.setCenterX((mp.startNode.getCenterX()+mp.endNode.getCenterX())/2);
            mp.setCenterY((mp.startNode.getCenterY()+mp.endNode.getCenterY())/2);
        }
    }
    
}   
    
    
    
