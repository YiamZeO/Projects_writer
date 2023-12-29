package ru.main.projects_writer.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanyEntity {
    private String name;
    private Long number;
    private String creationDate;
    private String description;
    private List<ProjectEntity> projectsList;
}
