package com.example.task1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene =createScene();
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(230);
        primaryStage.show();
    }
    protected Scene createScene() {
        VBox root=new VBox();
        HBox hBox=new HBox();
        Label lblView = new Label("View");
        lblView.setMinSize(10,10);
        Label lblEdit = new Label("Edit");
        lblEdit.setMinSize(10,10);
        Label lblHelp = new Label("Help");
        lblHelp.setMinSize(10,10);
        hBox.getChildren().addAll(lblView,lblEdit,lblHelp);
        hBox.setSpacing(20);
        TextField textField = new TextField();
        textField.setMinSize(60,60);
        textField.setAlignment(Pos.BASELINE_RIGHT);
        textField.setText("0");
        textField.setMaxSize(220,220);
        VBox vBox=new VBox();
        HBox hBox1=new HBox();
        Button btnMR = new Button("MR");
        btnMR.setMinSize(40,40);
        Button btnMS = new Button("MS");
        btnMS.setMinSize(40,40);
        Button btnMPlus = new Button("M+");
        btnMPlus.setMinSize(40,40);
        Button btnMMinus = new Button("M-");
        btnMMinus.setMinSize(40,40);
        Button btnMC = new Button("MC");
        btnMC.setMinSize(40,40);
        hBox1.getChildren().addAll(btnMC,btnMR,btnMS,btnMPlus,btnMMinus);
        hBox1.setSpacing(5);
        HBox hBox2=new HBox();
        Button btnBack = new Button("←");
        btnBack.setMinSize(40,40);
        Button btnCE = new Button("CE");
        btnCE.setMinSize(40,40);
        Button btnC = new Button("C");
        btnC.setMinSize(40,40);
        Button btnPlusMinus = new Button("±");
        btnPlusMinus.setMinSize(40,40);
        Button btnSquare = new Button("√");
        btnSquare.setMinSize(40,40);
        hBox2.getChildren().addAll(btnBack,btnCE,btnC,btnPlusMinus,btnSquare);
        hBox2.setSpacing(5);
        HBox hBox3=new HBox();
        Button btn7 = new Button("7");
        btn7.setMinSize(40,40);
        Button btn8 = new Button("8");
        btn8.setMinSize(40,40);
        Button btn9 = new Button("9");
        btn9.setMinSize(40,40);
        Button btnSlash = new Button("/");
        btnSlash.setMinSize(40,40);
        Button btnPercent = new Button("%");
        btnPercent.setMinSize(40,40);
        hBox3.getChildren().addAll(btn7,btn8,btn9,btnSlash,btnPercent);
        hBox3.setSpacing(5);
        HBox hBox4=new HBox();
        Button btn4 = new Button("4");
        btn4.setMinSize(40,40);
        Button btn5 = new Button("5");
        btn5.setMinSize(40,40);
        Button btn6 = new Button("6");
        btn6.setMinSize(40,40);
        Button btnTimes = new Button("*");
        btnTimes.setMinSize(40,40);
        Button btn1x = new Button("1/x");
        btn1x.setMinSize(40,40);
        hBox4.getChildren().addAll(btn4,btn5,btn6,btnTimes,btn1x);
        hBox4.setSpacing(5);

        VBox vb=new VBox();
        HBox hb1=new HBox();
        Button btn1 = new Button("1");
        btn1.setMinSize(40,40);
        Button btn2 = new Button("2");
        btn2.setMinSize(40,40);
        Button btn3 = new Button("3");
        btn3.setMinSize(40,40);
        Button btnMinus = new Button("-");
        btnMinus.setMinSize(40,40);
        hb1.getChildren().addAll(btn1,btn2,btn3,btnMinus);
        hb1.setSpacing(5);
        HBox hb2=new HBox();
        Button btn0 = new Button("0");
        btn0.setMinSize(85,40);
        Button btn = new Button(".");
        btn.setMinSize(40,40);
        Button btnPlus = new Button("+");
        btnPlus.setMinSize(40,40);
        hb2.getChildren().addAll(btn0,btn,btnPlus);
        hb2.setSpacing(5);
        Button btnEqual = new Button("=");
        btnEqual.setMinSize(40,85);
        vb.getChildren().addAll(hb1,hb2);
        vb.setSpacing(5);

        HBox hb=new HBox();
        hb.getChildren().addAll(vb,btnEqual);
        hb.setSpacing(5);

        vBox.getChildren().addAll(textField,hBox1,hBox2,hBox3,hBox4,hb);
        vBox.setSpacing(5);
        root.getChildren().addAll(hBox,vBox);
        return new Scene(root);
    }
}
