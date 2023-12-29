package ru.main.projects_writer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.main.projects_writer.entities.CompanyEntity;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
public class CompanyWriterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button companyAddButton;

    @FXML
    private TableView<CompanyEntity> companyTable;

    private List<CompanyEntity> companiesList;

    private Stage currentStage;

    @FXML
    void initialize() {
        companyAddButton.setOnMouseClicked(mouseEvent -> createUpdateCompanyWindow(null));

        companyTable.getColumns().clear();
        TableColumn<CompanyEntity, Long> companyNum = new TableColumn<>("№");
        companyNum.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn<CompanyEntity, String> name = new TableColumn<>("Компания");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<CompanyEntity, String> creationDate = new TableColumn<>("Дата создания");
        creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        companyTable.getSelectionModel().setCellSelectionEnabled(true);
        companyTable.getColumns().addAll(companyNum, name, creationDate);

        companyTable.setRowFactory(rv -> {
            TableRow<CompanyEntity> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                CompanyEntity rowData = row.getItem();
                if (!row.isEmpty()) {
                    if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                        createUpdateCompanyWindow(rowData);
                    }
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        createProjectsWriterWindow(rowData);
                    }
                }
            });
            return row;
        });

        Gson gson = new Gson();
        try (FileReader reader = new FileReader("companies.json")) {
            Type type = new TypeToken<List<CompanyEntity>>() {
            }.getType();
            companiesList = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (companiesList != null) {
            companyTable.setItems(FXCollections.observableList(companiesList));
            sortCompaniesTable();
        } else {
            companiesList = new ArrayList<>();
        }
    }

    public void setCurrentStage(Stage currentStage) {
        if (currentStage != null) {
            this.currentStage = currentStage;
            currentStage.setOnCloseRequest(windowEvent -> {
                Gson gson = new Gson();
                try (FileWriter writer = new FileWriter("companies.json")) {
                    gson.toJson(companiesList, writer);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void createUpdateCompanyWindow(CompanyEntity currentCompanyEntity) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(currentStage);
        UpdateCompanyWindow updateCompanyWindow = new UpdateCompanyWindow();
        try {
            updateCompanyWindow.start(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updateCompanyWindow.getUpdateCompanyController().setCompanyWriterController(this);
        updateCompanyWindow.getUpdateCompanyController().setCurrentStage(stage);
        updateCompanyWindow.getUpdateCompanyController().setCurrentCompanyEntity(currentCompanyEntity);
    }

    private void createProjectsWriterWindow(CompanyEntity currentCompanyEntity) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(currentStage);
        ProjectsWriterWindow projectsWriterWindow = new ProjectsWriterWindow();
        try {
            projectsWriterWindow.start(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        projectsWriterWindow.getProjectsWriterController().getCompanyTextField().setText(currentCompanyEntity.getName());
        projectsWriterWindow.getProjectsWriterController().setProjectsList(currentCompanyEntity.getProjectsList());
        if (currentCompanyEntity.getProjectsList() != null) {
            projectsWriterWindow.getProjectsWriterController().getProjectsTable()
                    .setItems(FXCollections.observableList(currentCompanyEntity.getProjectsList()));
            projectsWriterWindow.getProjectsWriterController().sortProjectsTable();
        }
    }

    public void sortCompaniesTable() {
        TableColumn<CompanyEntity, ?> date = companyTable.getColumns().get(0);
        date.setSortType(TableColumn.SortType.ASCENDING);
        companyTable.getSortOrder().clear();
        companyTable.getSortOrder().add(date);
    }
}
