/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author 2248826
 */
public class NewFXMain extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        //This is used to connect the primaryStage to another class 
        NewFXMain.primaryStage = primaryStage;
        //loads the greeting fxml file which is powered by the scene builder in our program
        Parent root = FXMLLoader.load(getClass().getResource("GreetingFXML.fxml"));
        Scene scene = new Scene(root);
        //Setting the title to be racer.
        primaryStage.setTitle("Racer");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
