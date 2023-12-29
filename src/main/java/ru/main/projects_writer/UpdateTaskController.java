package ru.main.projects_writer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.main.projects_writer.entities.TaskEntity;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
public class UpdateTaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button canselButton;

    @FXML
    private HTMLEditor descriptionEditor;

    @FXML
    private Button taskSaveButton;

    @FXML
    private TextField taskTextField;

    @FXML
    private Button tasksDeleteButton;

    private TaskEntity currentTaskEntity = null;

    private Stage stage;

    private TasksWriterController tasksWriterController;

    @FXML
    void initialize() {
        taskSaveButton.setOnMouseClicked(mouseEvent -> updateTasks());
        canselButton.setOnMouseClicked(mouseEvent -> stage.close());
        tasksDeleteButton.setOnMouseClicked(mouseEvent -> {
            tasksWriterController.getTasksList().remove(currentTaskEntity);
            List<TaskEntity> newTasksList = new ArrayList<>();
            int counter = 0;
            for (TaskEntity taskEntity : tasksWriterController.getTasksList()) {
                taskEntity.setNumber(counter + 1L);
                newTasksList.add(taskEntity);
                counter++;
            }
            tasksWriterController.getTasksTable().setItems(FXCollections.observableList(newTasksList));
            tasksWriterController.sortTasksTable();
            stage.close();
        });
    }

    public void setCurrentTaskEntity(TaskEntity currentTaskEntity) {
        if (currentTaskEntity != null) {
            this.currentTaskEntity = currentTaskEntity;
            taskTextField.setText(currentTaskEntity.getTask());
            descriptionEditor.setHtmlText(currentTaskEntity.getDescription());
            tasksDeleteButton.setDisable(false);
        }
    }

    private void updateTasks() {
        TaskEntity task = TaskEntity.builder()
                .task(taskTextField.getText())
                .description(descriptionEditor.getHtmlText())
                .build();
        if (currentTaskEntity != null) {
            task.setNumber(currentTaskEntity.getNumber());
            task.setCreationDate(currentTaskEntity.getCreationDate());
        } else {
            task.setNumber(tasksWriterController.getTasksList().size() + 1L);
            task.setCreationDate(LocalDate.now().toString());
        }
        tasksWriterController.getTasksList().remove(currentTaskEntity);
        tasksWriterController.getTasksList().add(task);
        tasksWriterController.getTasksTable()
                .setItems(FXCollections.observableList(tasksWriterController.getTasksList()));
        tasksWriterController.sortTasksTable();
        stage.close();
    }
}

