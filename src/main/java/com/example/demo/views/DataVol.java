package com.example.demo.views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class DataVol extends GridPane {


  Button btnLogin = new Button("Sing In");
  TextField textFieldLogin = new TextField();
  TextField textFieldPassword = new TextField();
  Label labelLogin = new Label("Login");

    public DataVol() {
        this.setHgap(5);
        this.setVgap(5);
        this.add(labelLogin,3,0);
        labelLogin.setFont( new Font("SansSerif",15));
        labelLogin.setStyle("-fx-text-fill: #A9A9A9");
        this.add(textFieldLogin,3,1);
        textFieldPassword.setStyle( "-fx-border-width: 2;"+
                                 "-fx-border-color: #A9A9A9;"+
                                 "-fx-border-radius: 5;");
        this.add(textFieldPassword,3,2);
        btnLogin.setFont(new Font("SansSerif",15));
        btnLogin. setStyle( "-fx-border-radius: 5;" +
                            "-fx-border-width: 2;" +
                            "-fx-text-fill: White;" +
                            "-fx-border-color: blue;" +
                            "-fx-background-color: #24a0ed;");
        setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;" +
                "-fx-background-color: transparent;");

        this.add(btnLogin,3,3);


    }


}
