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

/**
 *
 * @author HP
 */
public class NodeProperties {
    public static Stage stage;
    Parent root;
    public static Node bokri;
    public void go(Node tmp) throws IOException {
        bokri = tmp;
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        root = FXMLLoader.load(getClass().getResource("NodePropertiesFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.toFront();
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
    }
}
