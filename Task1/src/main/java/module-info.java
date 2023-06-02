module com.example.task1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.task1 to javafx.fxml;
    exports com.example.task1;
}