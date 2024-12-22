/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author YOUSSEF
 */
public class GreetingFXMLController implements Initializable {

    private Image[] images = new Image[5];
    @FXML
    private StackPane imagesPane;

    private ImageView[] contestantsImages = new ImageView[images.length];

    private int currentIndex = 0;
    @FXML
    private VBox greetingPane;
    private static MediaPlayer musicPlayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //putting music useing the media object
        Media mainMusic = new Media(getClass().getResource("music/funRun.mp3").toExternalForm());
        //assigning the music object to a player that is in our private field.
        musicPlayer = new MediaPlayer(mainMusic);
        //setting it to be playing constantly
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.setAutoPlay(true);
        createImages();
        imageTransition();
    }

    @FXML
    private void startPressed(ActionEvent event) throws IOException { 
        //musicPlayer.stop();
        new Assignement02().start(NewFXMain.getPrimaryStage());
    }

    private void createImages() {
        //creating all the contestants images
        images[0] = new Image("5Player.png");
        images[1] = new Image("10player.png");
        images[2] = new Image("51Player.png");
        images[3] = new Image("70Player.png");
        images[4] = new Image("85Player.png");

    }

    private void imageTransition() {
        for (int i = 0; i < images.length; i++) {
            contestantsImages[i] = new ImageView(images[i]);
        }
        this.imagesPane.getChildren().add(contestantsImages[currentIndex]);
        

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), contestantsImages[currentIndex]);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(1); 
       
        fadeTransition.setOnFinished(event -> {
            
            currentIndex = (currentIndex + 1) % images.length; // Move to the next image
            ImageView currentImageView = contestantsImages[currentIndex];
            currentImageView.setImage(images[currentIndex]);
            
            // Reset opacity for the next transition
            currentImageView.setOpacity(1.0);
            this.imagesPane.getChildren().remove(0);
            imageTransition();
        });

        // Start the initial transition
        fadeTransition.play();
    }

}
