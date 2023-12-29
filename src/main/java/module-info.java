module ru.main.projects_writer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires static lombok;

    exports ru.main.projects_writer;
    opens ru.main.projects_writer to javafx.fxml, com.google.gson;
    exports ru.main.projects_writer.entities;
    opens ru.main.projects_writer.entities to com.google.gson, javafx.fxml;
}