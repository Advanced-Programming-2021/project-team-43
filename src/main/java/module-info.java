module apProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires java.sql;
    opens view to javafx.fxml;
    requires javafx.media;
    exports view;
}