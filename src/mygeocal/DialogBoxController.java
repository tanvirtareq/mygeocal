package mygeocal;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.corba.se.impl.io.IIOPInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class DialogBoxController implements Initializable {
    @FXML
    private JFXButton cancelButton;
    
    @FXML
    private JFXButton okButton;
    @FXML
    public JFXTextField textField;
    @FXML
    public void okButtonPressed(ActionEvent event) {
        FXMLDocumentController.page.redraw(FXMLDocumentController.gc);
        RenameBox.bokri.name.setPrefWidth(textField.getWidth());
        RenameBox.bokri.name.setText(textField.getText());
        RenameBox.bokri.Name = textField.getText();
        Side.pointTable.getItems().set(Side.pointData.indexOf(RenameBox.bokri), RenameBox.bokri);
        
        cancelButtonPressed(event);
    }


    @FXML
    public void cancelButtonPressed(ActionEvent event) {
        RenameBox.stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textField.setText(RenameBox.bokri.name.getText());
    }

}
