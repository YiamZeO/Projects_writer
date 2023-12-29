package ru.main.projects_writer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.main.projects_writer.entities.CompanyEntity;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
public class UpdateCompanyController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button canselButton;

    @FXML
    private Button companyDeleteButton;

    @FXML
    private Button companySaveButton;

    @FXML
    private TextField companyTextField;

    @FXML
    private HTMLEditor descriptionEditor;

    private CompanyWriterController companyWriterController;

    private Stage currentStage;

    private CompanyEntity currentCompanyEntity = null;

    @FXML
    void initialize() {
        companySaveButton.setOnMouseClicked(mouseEvent -> updateCompanies());
        canselButton.setOnMouseClicked(mouseEvent -> currentStage.close());
        companyDeleteButton.setOnMouseClicked(mouseEvent -> {
            companyWriterController.getCompaniesList().remove(currentCompanyEntity);
            List<CompanyEntity> newCompaniesList = new ArrayList<>();
            int counter = 0;
            for (CompanyEntity companyEntity : companyWriterController.getCompaniesList()) {
                companyEntity.setNumber(counter + 1L);
                newCompaniesList.add(companyEntity);
                counter++;
            }
            companyWriterController.getCompanyTable().setItems(FXCollections.observableList(newCompaniesList));
            companyWriterController.sortCompaniesTable();
            currentStage.close();
        });
    }

    public void setCurrentCompanyEntity(CompanyEntity currentCompanyEntity) {
        if (currentCompanyEntity != null) {
            this.currentCompanyEntity = currentCompanyEntity;
            companyTextField.setText(currentCompanyEntity.getName());
            descriptionEditor.setHtmlText(currentCompanyEntity.getDescription());
            companyDeleteButton.setDisable(false);
        }
    }

    private void updateCompanies() {
        CompanyEntity companyEntity = CompanyEntity.builder()
                .name(companyTextField.getText())
                .description(descriptionEditor.getHtmlText())
                .build();
        if (currentCompanyEntity == null) {
            companyEntity.setNumber(companyWriterController.getCompaniesList().size() + 1L);
            companyEntity.setProjectsList(new ArrayList<>());
            companyEntity.setCreationDate(LocalDate.now().toString());
        } else {
            companyEntity.setNumber(currentCompanyEntity.getNumber());
            companyEntity.setProjectsList(currentCompanyEntity.getProjectsList());
            companyEntity.setCreationDate(currentCompanyEntity.getCreationDate());
        }
        companyWriterController.getCompaniesList().remove(currentCompanyEntity);
        companyWriterController.getCompaniesList().add(companyEntity);
        companyWriterController.getCompanyTable()
                .setItems(FXCollections.observableList(companyWriterController.getCompaniesList()));
        companyWriterController.sortCompaniesTable();
        currentStage.close();
    }
}
