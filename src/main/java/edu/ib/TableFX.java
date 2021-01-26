package edu.ib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TableFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {


        Parent root= FXMLLoader.load(getClass().getResource("/fxml/table.fxml"));
        Scene scene= new Scene(root,600,600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("fxml/combo.css");
        primaryStage.setTitle("Table");
        primaryStage.show();
    }
}