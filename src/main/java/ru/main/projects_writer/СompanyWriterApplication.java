package ru.main.projects_writer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class СompanyWriterApplication extends Application {
    private CompanyWriterController companyWriterController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(СompanyWriterApplication.class.getResource("company-view.fxml"));
        Parent root = fxmlLoader.load();
        companyWriterController = fxmlLoader.getController();
        companyWriterController.setCurrentStage(stage);
        Scene scene = new Scene(root, 799, 609);
        stage.setTitle("Компаtgниfи");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
