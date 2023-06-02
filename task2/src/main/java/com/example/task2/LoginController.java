package com.example.task2;

import com.example.task2.Database.DatabaseController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {


    private TextField txtUsername;
    private TextField txtPassword;
    private Button btnLogin;
    public LoginController(Scene scene, Stage stage){
        connectToUI(scene,stage);
    }
    public void connectToUI(Scene scene,Stage stage) {
        StringProperty username=new SimpleStringProperty("");
        StringProperty password=new SimpleStringProperty("");
        txtUsername=(TextField) scene.lookup("#Username");
        txtPassword=(TextField) scene.lookup("#Password");
        btnLogin=(Button) scene.lookup("#Login");

        txtUsername.textProperty().bindBidirectional(username);
        txtPassword.textProperty().bindBidirectional(password);

        btnLogin.setOnAction(event->{
            DatabaseController database=new DatabaseController();
            database.connectToDatabase(username.getValue(),password.getValue());
            if(database.isValidConnection()){
            mainUI(stage,database);}
        });

    }
    public void mainUI(Stage main, DatabaseController database){
        database.albums(main);
    }

}