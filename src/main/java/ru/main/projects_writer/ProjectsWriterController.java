package ru.main.projects_writer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.main.projects_writer.entities.ProjectEntity;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
public class ProjectsWriterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button projectsAddButton;

    @FXML
    private TextField companyTextField;

    @FXML
    private TableView<ProjectEntity> projectsTable;

    private List<ProjectEntity> projectsList;

    private Stage currentStage;

    @FXML
    void initialize() {
        projectsAddButton.setOnMouseClicked(mouseEvent -> createUpdateProjectWindow(null));

        projectsTable.getColumns().clear();
        TableColumn<ProjectEntity, Long> projectNum = new TableColumn<>("№");
        projectNum.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn<ProjectEntity, String> name = new TableColumn<>("Проект");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<ProjectEntity, String> creationDate = new TableColumn<>("Дата создания");
        creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        projectsTable.getSelectionModel().setCellSelectionEnabled(true);
        projectsTable.getColumns().addAll(projectNum, name, creationDate);

        projectsTable.setRowFactory(rv -> {
            TableRow<ProjectEntity> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                ProjectEntity rowData = row.getItem();
                if (!row.isEmpty()) {
                    if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                        createUpdateProjectWindow(rowData);
                    }
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        createTasksWriterWindow(rowData);
                    }
                }
            });
            return row;
        });
    }

    private void createUpdateProjectWindow(ProjectEntity currentProjectEntity) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(currentStage);
        UpdateProjectWindow updateProjectWindow = new UpdateProjectWindow();
        try {
            updateProjectWindow.start(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updateProjectWindow.getUpdateProjectController().setProjectsWriterController(this);
        updateProjectWindow.getUpdateProjectController().setCurrentStage(stage);
        updateProjectWindow.getUpdateProjectController().setCurrentProjectEntity(currentProjectEntity);
    }

    private void createTasksWriterWindow(ProjectEntity currentProjectEntity) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(currentStage);
        TasksWriterWindow tasksWriterWindow = new TasksWriterWindow();
        try {
            tasksWriterWindow.start(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tasksWriterWindow.getTasksWriterController().getProjectTextField().setText(currentProjectEntity.getName());
        tasksWriterWindow.getTasksWriterController().setTasksList(currentProjectEntity.getTasksList());
        if (currentProjectEntity.getTasksList() != null) {
            tasksWriterWindow.getTasksWriterController().getTasksTable()
                    .setItems(FXCollections.observableList(currentProjectEntity.getTasksList()));
            tasksWriterWindow.getTasksWriterController().sortTasksTable();
        }
    }

    public void sortProjectsTable() {
        TableColumn<ProjectEntity, ?> date = projectsTable.getColumns().get(0);
        date.setSortType(TableColumn.SortType.ASCENDING);
        projectsTable.getSortOrder().clear();
        projectsTable.getSortOrder().add(date);
    }
}