/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;
import javax.swing.SizeRequirements;
import properties.AngleProperties;

/**
 *
 * @author Tanvir Tareq
 */
public class Angle {
    
    private static int click=0;
    private static Angle angle;

    private static double calculateAngle(Node start, Node middle, Node end) {
        double e=dist(start, middle);
        double s=dist(middle, end);
        double m=dist(start, end);
        
        double an=Math.acos((e*e+s*s-m*m)/(2*e*s));
        return an;
        
    }

    private static void showAngle(double an) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private Node start, middle, end;
    
    private static double dist(Node a, Node b) {
        
        if((a.getCenterY()-b.getCenterY())*(a.getCenterY()-b.getCenterY())==0)
        {
            return Math.abs(a.getCenterX()-b.getCenterX());
        }
        return Math.sqrt((a.getCenterX()-b.getCenterX())*(a.getCenterX()-b.getCenterX())+(a.getCenterY()-b.getCenterY())*(a.getCenterY()-b.getCenterY()));
    }

    private Angle() {
        
    }
    
    public static void evaluateAngle(MouseEvent event)
    {
        if(click==0)
        {
            click=1;
            angle=new Angle();
            angle.start=Node.addNode(event);
        }
        else if(click==1)
        {
            click=2;
            angle.middle=Node.addNode(event);
        }
        else if(click==2)
        {
            click=0;
            angle.end=Node.addNode(event);
            
            double an=calculateAngle(angle.start, angle.middle, angle.end);
            AngleProperties ap=new AngleProperties();
            try {
                ap.show(an, angle.start, angle.middle, angle.end);
            } catch (IOException ex) {
                Logger.getLogger(Angle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void evaluateAngle(Node node)
    {
        if(click==0)
        {
            click=1;
            angle=new Angle();
            angle.start=node;
        }
        else if(click==1)
        {
            click=2;
            angle.middle=node;
        }
        else if(click==2)
        {
            click=0;
            angle.end=node;
            
            double an=calculateAngle(angle.start, angle.middle, angle.end);
            AngleProperties ap=new AngleProperties();
            try {
                ap.show(an, angle.start, angle.middle, angle.end);
            } catch (IOException ex) {
                Logger.getLogger(Angle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
