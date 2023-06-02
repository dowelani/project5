package com.example.task1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class PersonalDetails extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene =createScene();
        primaryStage.setTitle("Personal Details");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(300);
        primaryStage.show();
    }

    protected Scene createScene() {
        VBox root = new VBox();
        root.setSpacing(10);
        HBox hBox1=new HBox();
        Label lblName = new Label("Name:");
        TextField txtName = new TextField();
        hBox1.getChildren().addAll(lblName,txtName);
        hBox1.setSpacing(100);
        hBox1.setAlignment(Pos.CENTER);

        HBox hBox2=new HBox();
        Label Surname = new Label("Surname:");
        TextField txtSurname = new TextField();
        hBox2.getChildren().addAll(Surname,txtSurname);
        hBox2.setSpacing(85);
        hBox2.setAlignment(Pos.CENTER);


        HBox hBox3=new HBox();
        Label lblStudentNo = new Label("Student number:");
        TextField txtStudentNo = new TextField();
        hBox3.getChildren().addAll(lblStudentNo,txtStudentNo);
        hBox3.setSpacing(45);
        hBox3.setAlignment(Pos.CENTER);

        HBox hBox4=new HBox();
        Label lblCourse = new Label("Course:");
        TextField txtCourse = new TextField();
        hBox4.getChildren().addAll(lblCourse,txtCourse);
        hBox4.setSpacing(95);
        hBox4.setAlignment(Pos.CENTER);

        HBox hBox5=new HBox();
        Label lblYear = new Label("Year:");
        TextField txtYear = new TextField();
        hBox5.getChildren().addAll(lblYear,txtYear);
        hBox5.setSpacing(110);
        hBox5.setAlignment(Pos.CENTER);

        HBox hBox6=new HBox();
        Button btnOk = new Button("Ok");
        Button btnCancel = new Button("Cancel");
        hBox6.getChildren().addAll(btnOk,btnCancel);
        hBox6.setSpacing(3);
        hBox6.setAlignment(Pos.BASELINE_CENTER);

        root.getChildren().addAll(hBox1,hBox2,hBox3,hBox4,hBox5,hBox6);
        return new Scene(root);
    }

    public static void main(String[] args) {
        launch();
    }
}