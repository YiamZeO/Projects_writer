package ru.main.projects_writer.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectEntity {
    private Long number;
    private String name;
    private String description;
    private String creationDate;
    private List<TaskEntity> tasksList;
}
