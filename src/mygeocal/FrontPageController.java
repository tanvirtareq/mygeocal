/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import static mygeocal.Grid.dY;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class FrontPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public AnchorPane frontPage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
        Stage stage = (Stage) frontPage.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        Scene scene = new Scene(root);
        }
        catch(Exception ex)
        {
            
        }
    }

    public void fadeOut() {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        fade.setNode(frontPage);
        fade.setFromValue(1);
        fade.setToValue(0.2);
        fade.setOnFinished(e -> {
            loadNext();
        });
        fade.play();

    }

    private void loadNext() {
        try {
            Stage stage = (Stage) frontPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
            Scene scene = new Scene(root);
            FXMLDocumentController.stage = stage;
            stage.setScene(scene);
//            stage.setTitle("GeoCal");
//        stage.initStyle(StageStyle.TRANSPARENT);
            stage.widthProperty().addListener((obs, oldVal, newVal) -> {
                FXMLDocumentController.separator.setPrefWidth((double) newVal);
                FXMLDocumentController.toolBar.setPrefWidth((double) newVal);
                FXMLDocumentController.toolBar.setMaxWidth((double) newVal);
                FXMLDocumentController.pane.setMaxWidth((double) newVal);
                FXMLDocumentController.canvas.setWidth((double) newVal - FXMLDocumentController.tabPane.getWidth() - 18.);
                FXMLDocumentController.page.clearCanvas(FXMLDocumentController.page.gc);
                FXMLDocumentController.page.redraw(FXMLDocumentController.page.gc);
            });

            stage.heightProperty().addListener((obs, oldVal, newVal) -> {
                FXMLDocumentController.pane.setMaxHeight((double) newVal);
                FXMLDocumentController.canvas.setHeight((double) newVal - dY);
                FXMLDocumentController.page.clearCanvas(FXMLDocumentController.page.gc);
                FXMLDocumentController.page.redraw(FXMLDocumentController.page.gc);
            });
//            stage.getIcons().add(new Image("/image/GeoCal.png"));
            stage.show();
        } catch (Exception ex) {

        }
    }
}
