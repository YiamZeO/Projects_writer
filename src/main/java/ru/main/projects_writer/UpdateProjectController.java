package ru.main.projects_writer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.main.projects_writer.entities.ProjectEntity;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
public class UpdateProjectController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button canselButton;

    @FXML
    private TextField projectTextField;

    @FXML
    private Button projectsSaveButton;

    @FXML
    private Button projectsDeleteButton;

    @FXML
    private HTMLEditor descriptionEditor;

    private ProjectsWriterController projectsWriterController;

    private Stage currentStage;

    private ProjectEntity currentProjectEntity = null;

    @FXML
    void initialize() {
        projectsSaveButton.setOnMouseClicked(mouseEvent -> updateProjects());
        canselButton.setOnMouseClicked(mouseEvent -> currentStage.close());
        projectsDeleteButton.setOnMouseClicked(mouseEvent -> {
            projectsWriterController.getProjectsList().remove(currentProjectEntity);
            List<ProjectEntity> newProjectsList = new ArrayList<>();
            int counter = 0;
            for (ProjectEntity projectEntity : projectsWriterController.getProjectsList()) {
                projectEntity.setNumber(counter + 1L);
                newProjectsList.add(projectEntity);
                counter++;
            }
            projectsWriterController.getProjectsTable().setItems(FXCollections.observableList(newProjectsList));
            projectsWriterController.sortProjectsTable();
            currentStage.close();
        });
    }

    public void setCurrentProjectEntity(ProjectEntity currentProjectEntity) {
        if (currentProjectEntity != null) {
            this.currentProjectEntity = currentProjectEntity;
            projectTextField.setText(currentProjectEntity.getName());
            descriptionEditor.setHtmlText(currentProjectEntity.getDescription());
            projectsDeleteButton.setDisable(false);
        }
    }

    private void updateProjects() {
        ProjectEntity projectEntity = ProjectEntity.builder()
                .name(projectTextField.getText())
                .description(descriptionEditor.getHtmlText())
                .build();
        if (currentProjectEntity == null) {
            projectEntity.setNumber(projectsWriterController.getProjectsList().size() + 1L);
            projectEntity.setTasksList(new ArrayList<>());
            projectEntity.setCreationDate(LocalDate.now().toString());
        } else {
            projectEntity.setNumber(currentProjectEntity.getNumber());
            projectEntity.setTasksList(currentProjectEntity.getTasksList());
            projectEntity.setCreationDate(currentProjectEntity.getCreationDate());
        }
        projectsWriterController.getProjectsList().remove(currentProjectEntity);
        projectsWriterController.getProjectsList().add(projectEntity);
        projectsWriterController.getProjectsTable()
                .setItems(FXCollections.observableList(projectsWriterController.getProjectsList()));
        projectsWriterController.sortProjectsTable();
        currentStage.close();
    }

}
