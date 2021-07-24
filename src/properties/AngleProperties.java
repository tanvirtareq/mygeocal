/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package properties;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mygeocal.Angle;
import mygeocal.Node;
import static properties.NodeProperties.bokri;
import static properties.NodeProperties.stage;

/**
 *
 * @author Tanvir Tareq
 */
public class AngleProperties {
    public static Stage stage;
    static Parent root;
    public static double bokri;
    public static Node a, b, c;
    
    public  void show(double angle, Node a, Node b, Node c) throws IOException
    {
        this.a=a;
        this.b=b;
        this.c=c;
        
        bokri = angle;
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        root = FXMLLoader.load(getClass().getResource("AngleProperties.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.toFront();
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
            
    
}
