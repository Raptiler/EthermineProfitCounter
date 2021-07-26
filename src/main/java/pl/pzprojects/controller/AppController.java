package pl.pzprojects.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.pzprojects.factory.StageFactory;
import pl.pzprojects.factory.TextFieldsFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private AnchorPane appAnchorPane;

    @FXML
    private Button exitButton;

    @FXML
    private TextField enterAddressTextField;

    @FXML
    private Button calculateButton;

    private final String MAIN_TABLE_VIEW_FXML = "/fxml/main-table-view.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeExitButton();
        initializeCalculateButton();
        enterAddressTextField.setText("0x910090Ea889B64B4e722ea4b8fF6D5e734dFb38F");
    }

    private void initializeCalculateButton() {
        calculateButton.setOnAction(x->{
            String text = enterAddressTextField.getText();
            if(text.length() != 40 && text.length() != 42)
                return;
            TextFieldsFactory.firstTextFieldValue = enterAddressTextField.getText();
            openAppTableView();
        });
    }

    private StageFactory setStage(String FXML) throws IOException {
        Stage stage = new Stage();
        Parent appRoot = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        appRoot = loader.load();
        Scene scene = new Scene(appRoot,1024,768);
        stage.setTitle("Ethermine Payouts");
        stage.setResizable(false);
        stage.setScene(scene);
        return new StageFactory(stage,loader);
    }

    private void openAppTableView(){
        try {
            StageFactory stageFactory = setStage(MAIN_TABLE_VIEW_FXML);
            Stage stage = stageFactory.getStage();
            AppTableController appTableController = stageFactory.getLoader().getController();
            stage.show();
            appTableController.setControllersValues(enterAddressTextField.getText());
            getStage().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeExitButton() {
        exitButton.setOnAction(x -> getStage().close());
    }

    private Stage getStage()
    {
        return (Stage)appAnchorPane.getScene().getWindow();
    }
}
