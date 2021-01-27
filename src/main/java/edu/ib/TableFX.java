package edu.ib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Application Crazy Eights.
 * User can play game "Crazy Eights" versus computer opponent.
 */
public class TableFX extends Application {

    /**
     * Launch program
     * @param args unused.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates stage in javaFX where user can play the game.
     * @exception IOException on input error
     * @see IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/table.fxml"));
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("fxml/combo.css");
        primaryStage.setTitle("Crazy Eights");
        primaryStage.show();
    }
}