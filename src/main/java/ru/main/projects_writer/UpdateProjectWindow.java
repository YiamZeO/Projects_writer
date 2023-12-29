package ru.main.projects_writer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class UpdateProjectWindow extends Application {

    private UpdateProjectController updateProjectController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UpdateProjectWindow.class.getResource("updateProject-view.fxml"));
        Parent root = fxmlLoader.load();
        updateProjectController = fxmlLoader.getController();
        Scene scene = new Scene(root, 700, 739);
        stage.setTitle("Редактирование проекта");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.show();
    }
}
