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
public class UpdateTaskWindow extends Application {
    private UpdateTaskController updateTaskController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UpdateProjectWindow.class.getResource("updateTask-view.fxml"));
        Parent root = fxmlLoader.load();
        updateTaskController = fxmlLoader.getController();
        Scene scene = new Scene(root, 703, 752);
        stage.setTitle("Редактирование задачи");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.show();
    }
}
