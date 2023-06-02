module com.example.task2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires mssql.jdbc;
    requires java.naming;

    opens com.example.task2 to javafx.fxml;
    exports com.example.task2;
}