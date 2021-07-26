package pl.pzprojects.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import pl.pzprojects.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}