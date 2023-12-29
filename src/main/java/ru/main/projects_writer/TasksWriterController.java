package ru.main.projects_writer;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.main.projects_writer.entities.TaskEntity;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
public class TasksWriterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField projectTextField;

    @FXML
    private Button tasksAddButton;

    @FXML
    private TableView<TaskEntity> tasksTable;

    private List<TaskEntity> tasksList;

    private Stage currentStage;

    @FXML
    void initialize() {
        tasksTable.getColumns().clear();
        TableColumn<TaskEntity, Long> number = new TableColumn<>("№");
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn<TaskEntity, String> task = new TableColumn<>("Задача");
        task.setCellValueFactory(new PropertyValueFactory<>("task"));
        TableColumn<TaskEntity, String> creationDate = new TableColumn<>("Дата создания");
        creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        tasksTable.getSelectionModel().setCellSelectionEnabled(true);
        tasksTable.getColumns().addAll(number, task, creationDate);
        tasksTable.setRowFactory(tv -> {
            TableRow<TaskEntity> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                TaskEntity rowData = row.getItem();
                if (!row.isEmpty()) {
                    if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                        createUpdateTaskWindow(rowData);
                    }
                }
            });
            return row;
        });

        tasksAddButton.setOnMouseClicked(mouseEvent -> createUpdateTaskWindow(null));
    }

    private void createUpdateTaskWindow(TaskEntity taskEntity) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(currentStage);
        UpdateTaskWindow updateTaskWindow = new UpdateTaskWindow();
        try {
            updateTaskWindow.start(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updateTaskWindow.getUpdateTaskController().setStage(stage);
        updateTaskWindow.getUpdateTaskController().setTasksWriterController(this);
        updateTaskWindow.getUpdateTaskController().setCurrentTaskEntity(taskEntity);
    }

    public void sortTasksTable() {
        TableColumn<TaskEntity, ?> date = tasksTable.getColumns().get(0);
        date.setSortType(TableColumn.SortType.ASCENDING);
        tasksTable.getSortOrder().clear();
        tasksTable.getSortOrder().add(date);
    }

}

