package com.example.task1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrintDialog extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene =createScene();
        primaryStage.setTitle("Print Dialog");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(300);
        primaryStage.show();
    }

    protected Scene createScene() {
        HBox root=new HBox();
        root.setSpacing(5);
        VBox vBox=new VBox();
        vBox.setSpacing(8);
        VBox vBox1=new VBox();
        VBox vBox2=new VBox();
        Label lblPrinter = new Label("Printer: Epson EpI-700");
        RadioButton r1 = new RadioButton("Image");
        r1.setSelected(true);
        RadioButton r2 = new RadioButton("Text");
        RadioButton r3 = new RadioButton("Code");
        vBox2.getChildren().addAll(r1,r2,r3);
        HBox hBox=new HBox();
        Label lblQuality = new Label("Print Quality:  ");
        String quality[] = { "low", "middle", "high" };
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(quality));
        VBox vb=new VBox(comboBox);
        CheckBox checkBox=new CheckBox();
        Label lblPrint = new Label("Print to file");
        hBox.getChildren().addAll(lblQuality,vb,checkBox,lblPrint);
        hBox.setSpacing(5);
        vBox.getChildren().addAll(lblPrinter,vBox2,hBox);

        Button btnOk = new Button("Ok");
        btnOk.setMaxSize(70,50);
        Button btnCancel = new Button("Cancel");
        btnCancel.setMaxSize(70,50);
        Button btnSetup = new Button("Setup");
        btnSetup.setMaxSize(70,50);
        Button btnHelp = new Button("Help");
        btnHelp.setMaxSize(70,50);

        vBox1.getChildren().addAll(btnOk,btnCancel,btnSetup,btnHelp);

        vBox2.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        vBox1.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.BASELINE_CENTER);
        root.getChildren().addAll(vBox,vBox1);

        return new Scene(root);
    }
}
