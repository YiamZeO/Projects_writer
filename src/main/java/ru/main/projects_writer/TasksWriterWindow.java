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
public class TasksWriterWindow extends Application {
    private TasksWriterController tasksWriterController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TasksWriterWindow.class.getResource("tasks-view.fxml"));
        Parent root = fxmlLoader.load();
        tasksWriterController = fxmlLoader.getController();
        tasksWriterController.setCurrentStage(stage);
        Scene scene = new Scene(root, 728, 692);
        stage.setTitle("Задачи проекта");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.show();
    }
}
