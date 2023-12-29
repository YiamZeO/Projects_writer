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
public class ProjectsWriterWindow extends Application {
    private ProjectsWriterController projectsWriterController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectsWriterWindow.class.getResource("projects-view.fxml"));
        Parent root = fxmlLoader.load();
        projectsWriterController = fxmlLoader.getController();
        projectsWriterController.setCurrentStage(stage);
        Scene scene = new Scene(root, 799, 685);
        stage.setTitle("Проекты");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.show();
    }
}