package com.example.task2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene =createScene();
        LoginController controller = new LoginController(scene,primaryStage);
        primaryStage.setTitle("Login Database");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(200);
        primaryStage.show();
    }
    protected Scene createScene(){
        VBox root = new VBox();
        root.setSpacing(10);
        Label lblUsername = new Label("Username:");
        TextField txtUsername = new TextField();
        txtUsername.setMinSize(200,30);
        txtUsername.setMaxSize(200,30);
        txtUsername.setId("Username");
        Label lblPassword = new Label("Password:");
        TextField txtPassword = new TextField();
        txtPassword.setMinSize(200,30);
        txtPassword.setMaxSize(200,30);
        txtPassword.setId("Password");
        Button btnLogin = new Button("Login");
        btnLogin.setId("Login");
        root.getChildren().addAll(lblUsername,txtUsername,lblPassword,txtPassword,btnLogin);
        return new Scene(root);
    }

    public static void main(String[] args) {
        launch();
    }
}