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
import mygeocal.Node;
import mygeocal.Segment;

/**
 *
 * @author Tanvir Tareq
 */
public class SegmentProperties {
    
    public static Stage stage;
    Parent root;
    public static Segment bokri;
    public void go(Segment tmp) throws IOException {
        bokri = tmp;
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        root = FXMLLoader.load(getClass().getResource("SegmentProperties.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.toFront();
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
    }
    
}
