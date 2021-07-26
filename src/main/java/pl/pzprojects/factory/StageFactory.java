package pl.pzprojects.factory;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.Data;

public class StageFactory {
    private Stage stage;
    private FXMLLoader loader;

    public StageFactory(Stage stage, FXMLLoader loader) {
        this.stage = stage;
        this.loader = loader;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }
}
