/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import com.sun.javafx.geom.Shape;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Tanvir Tareq
 */
public class GeoEllipse extends Ellipse{
    static int click=0;
    double tangentOfMajorAxis, tangentOfDirectrics;
    boolean tangentOfMajorAxisUndefined=false;
    
    static  ArrayList<GeoEllipse> ellipseList=new ArrayList<GeoEllipse>();

    private static double dist(Node a, Node b) {
        
        if((a.getCenterY()-b.getCenterY())*(a.getCenterY()-b.getCenterY())==0)
        {
            return Math.abs(a.getCenterX()-b.getCenterX());
        }
        return Math.sqrt((a.getCenterX()-b.getCenterX())*(a.getCenterX()-b.getCenterX())+(a.getCenterY()-b.getCenterY())*(a.getCenterY()-b.getCenterY()));
    }
    Node epicentre1, epicentre2, point;
    Node centre;
    

    private GeoEllipse(MouseEvent event) {
        super(event.getX(), event.getY());
        Node node=Node.addNode(event);
        epicentre1=epicentre2=centre=node;
        this.setRadiusX(0);
        this.setRadiusY(0);
        this.setStrokeWidth(2.);
        this.setFill(Color.TRANSPARENT);
        this.toBack();
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
        
        FXMLDocumentController.pane.getChildren().add(this);
        ellipseList.add(this);
    }
    
    public static void modifyEllipse(GeoEllipse elipse,MouseEvent event )
    {
        Node node=Node.addNode(event);
        if(click==1)
        {
            elipse.epicentre2=node;
            
            click=2;
//            elipse.centre=Node.addNode(event);
            elipse.centre.setCenterX((elipse.epicentre1.getCenterX()+elipse.epicentre2.getCenterX())/2);
            elipse.centre.setCenterY((elipse.epicentre1.getCenterY()+elipse.epicentre2.getCenterY())/2);
//            elipse.centre.X=(elipse.epicentre1.getCenterX()+elipse.epicentre2.getCenterX())/2;
//            elipse.centre.Y=(elipse.epicentre1.getCenterY()+elipse.epicentre2.getCenterY())/2;
            
//            elipse.centre.setterXYPixel((elipse.epicentre1.getCenterX()+elipse.epicentre2.getCenterX())/2, (elipse.epicentre1.getCenterY()+elipse.epicentre2.getCenterY())/2);
            
            elipse.setCenterX(elipse.centre.getCenterX());
            elipse.setCenterY(elipse.centre.getCenterY());
             
             double angle=0;
            try {
                elipse.tangentOfMajorAxis =(elipse.epicentre1.getCenterY()-elipse.epicentre2.getCenterY())/(elipse.epicentre1.getCenterX()-elipse.epicentre2.getCenterX());
                angle=Math.atan(elipse.tangentOfMajorAxis);
                angle=Math.toDegrees(angle);
                elipse.tangentOfMajorAxisUndefined=false;
                
            } catch (Exception e) {
                
                angle=Math.toRadians(90);
                elipse.tangentOfMajorAxisUndefined=true;
                        
            }
            
            elipse.setRotate(angle);
          
        }
        else if(click==2)
        {
            click=0;
            double d1=dist(node, elipse.epicentre1);
            double d2=dist(node, elipse.epicentre2);
            double f=dist(elipse.epicentre1, elipse.epicentre2);
            
            double a=d1+d2;
            double b=Math.sqrt(a*a-f*f);
            elipse.setRadiusX(a/2);
            elipse.setRadiusY(b/2);
            elipse.point=node;
            elipse.properties(elipse);
            
            }
        
    }
    public static GeoEllipse addEllipse(MouseEvent event)
    {
        if(click==0)
        {
            click=1;
           return new GeoEllipse(event);
            
        }
        return null;
    }
    
    public  static void redraw()
    {
        for(int i=0;i<ellipseList.size();++i)
        {
            ellipseList.get(i).setCenterX((ellipseList.get(i).epicentre1.getCenterX()+ellipseList.get(i).epicentre2.getCenterX())/2);
            ellipseList.get(i).setCenterY((ellipseList.get(i).epicentre1.getCenterY()+ellipseList.get(i).epicentre2.getCenterY())/2);
            double d1=dist(ellipseList.get(i).point, ellipseList.get(i).epicentre1);
            double d2=dist(ellipseList.get(i).point, ellipseList.get(i).epicentre2);
            double f=dist(ellipseList.get(i).epicentre2, ellipseList.get(i).epicentre1);
            double a=d1+d2;
            double b=Math.sqrt(a*a-f*f);
            ellipseList.get(i).setRadiusX(a/2);
            ellipseList.get(i).setRadiusY(b/2);
                   
        }
        
    }
    
    
    
    String properties(GeoEllipse ellipse)
    {
        String equation="";
        if(tangentOfMajorAxisUndefined==false)
        {
            Node node;
            if(ellipse.epicentre1.getCenterX()<ellipse.epicentre2.getCenterX())
                node=ellipse.epicentre1;
            
            else node=ellipse.epicentre2;
            
            double a=Grid.unitOfScale*dist(ellipse.epicentre1, ellipse.point)/Grid.increment;
            double b=Grid.unitOfScale*dist(ellipse.epicentre2, ellipse.point)/Grid.increment;
            double d=Grid.unitOfScale*dist(ellipse.epicentre1, ellipse.epicentre2)/Grid.increment;
            
            double s=((a+b)-d)/2.0;
            double d1=d/2.0;
            
            double p=(ellipse.epicentre1.x+ellipse.epicentre2.x)/2.0;
            double q=(ellipse.epicentre1.y+ellipse.epicentre2.y)/2.0;
            
            double dx1=s*(node.x-p)/d1;
            double dy1=s*(node.y-q)/d1;
            
            double x1=node.x+dx1;
            double y1=node.y+dy1;
            System.out.println(x1+"x1 y1"+y1);
                    
            
//            double e=d1/(s+d1);
            double e=d/((a+b));
            System.out.println(e);
            
            double l=s/e;
            
            double dx=l*(x1-node.x)/s;
            double dy=l*(y1-node.y)/s;
            
            double x=x1+dx;
            double y=y1+dy;
            
            tangentOfDirectrics=-1/tangentOfMajorAxis;
            
            Double alp=node.x;
            Double beta=node.y;
            Double m=tangentOfDirectrics;
            
            Double c=ellipse.point.y-tangentOfDirectrics*ellipse.point.x;
            
            Double A=e*e*m*m-1-tangentOfDirectrics*tangentOfDirectrics;
            
            Double B=e*e-1-tangentOfDirectrics*tangentOfDirectrics;
            
            Double C=-2*tangentOfDirectrics*e*e;
            
            Double D=e*e*2*m*c+2*alp+2*node.x*tangentOfDirectrics*tangentOfDirectrics;
            
            Double E=-2*c*e*e+2*beta+2*beta*m*m;
            
            Double F=e*e*c*c-alp*alp-beta*beta-alp*alp*m*m-beta*beta*m*m;
            
            equation+=A.toString()+"x^2+"+B.toString()+"y^2+"+C.toString()+"xy+"+D.toString()+"x+"+E.toString()+"y+"+F.toString();
            System.out.println(equation);
            
        }
        
        return  equation;
    }
    
    
    
}
