package mygeocal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MyGeoCal extends Application {
    public static String[] args;

    static Parent rename;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FrontPage.fxml"));
        Scene scene = new Scene(root);
        FXMLDocumentController.stage = stage;
        stage.setScene(scene);
        stage.setTitle("GeoCal");


        stage.getIcons().add(new Image("/image/GeoCal.png"));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
