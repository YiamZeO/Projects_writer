package ru.main.projects_writer.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskEntity {
    private Long number;
    private String task;
    private String description;
    private String creationDate;
}
