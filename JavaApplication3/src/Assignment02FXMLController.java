/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author 2248826
 */
public class Assignment02FXMLController implements Initializable {

    @FXML
    private Button start;

    private TranslateTransition run1;
    private TranslateTransition run2;
    private TranslateTransition run3;
    private TranslateTransition run4;
    private TranslateTransition run5;
    private TranslateTransition[] runs = {run1, run2, run3, run4, run5};

    private Image[] runner1Animation = {new Image("10PlayerAnim1.png"), new Image("10PlayerAnim2.png")};
    private Image[] runner2Animation = {new Image("85PlayerAnim1.png"), new Image("85PlayerAnim2.png")};
    private Image[] runner3Animation = {new Image("70PlayerAnim1.png"), new Image("70PlayerAnim2.png")};
    private Image[] runner4Animation = {new Image("5PlayerAnim1.png"), new Image("5PlayerAnim2.png")};
    private Image[] runner5Animation = {new Image("51PlayerAnim1.png"), new Image("51PlayerAnim2.png")};

    @FXML
    private ImageView runner2;
    @FXML
    private ImageView runner3;

    @FXML
    private Button stopBtn;
    @FXML
    private Button exitButton;
    @FXML
    private Line runLine3;
    @FXML
    private ImageView runner4;
    @FXML
    private ImageView runner5;

    boolean isPlaying = true;
    @FXML
    private Button betButton;
    @FXML
    private Slider bettingAmountSlider;
    @FXML
    private ListView<String> bettingList;
    @FXML
    private ImageView alienPlane2;
    @FXML
    private ImageView alienPlane1;
    @FXML
    private Label moneyLabel;

    private final ArrayList<MarathonerModel> marathoners = new ArrayList<>();

    private static MediaPlayer mainMusicPlayer;
    @FXML
    private Line line1;
    @FXML
    private ImageView runner1;

    private ArrayList<ImageView> runners = new ArrayList<>();

    private AnimationTimer travelTime;
    private long previousTime = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        runners.add(runner1);
        runners.add(runner2);
        runners.add(runner3);
        runners.add(runner4);
        runners.add(runner5);

        initializeMarathoners();
        initializeAliens();
        moneyLabel.setText(String.format("%.2f %s", money, " $"));
        ObservableList l = FXCollections.observableArrayList();
        for (int i = 0; i < 5; i++) {
            l.add(marathoners.get(i).getName());
        }

        bettingList.setItems(l);

        Media media = new Media(getClass().getResource("music/MusicAmb.mp3").toExternalForm());

        mainMusicPlayer = new MediaPlayer(media);
        mainMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mainMusicPlayer.play();

        travelTime = new AnimationTimer() {

            @Override
            public void handle(long currentTime) {
                double elapsedTime = (currentTime - previousTime) / 1000000000.0;

                if (elapsedTime > 0.1) {
                    animationIndex = (animationIndex + 1) % 2;
                    for (int i = 0; i < 5; i++) {
                        if (runners.get(i).getTranslateX() == runs[i].getToX()) {
                            continue;
                        }
                        runners.get(i).setImage(marathoners.get(i).getImageRunAnimation()[animationIndex]);
                    }
                    previousTime = currentTime;
                }

            }

            @Override

            public void start() {
                previousTime = System.nanoTime();
                super.start();

            }

            @Override
            public void stop() {
                previousTime = -1;
                super.stop();
            }
        };
    }

    private double money = 1000;

    @FXML
    private void startPressed(ActionEvent event) {
        translationAnimationMaker();
        betButton.setDisable(true);
        start.setDisable(true);

        for (int i = 0; i < 5; i++) {
            marathoners.get(i).setTimeTaken(Math.random() * 3 + 2);
            runs[i].setDuration(Duration.seconds(marathoners.get(i).getTimeTaken()));
        }
        travelTime.start();
        for (int i = 0; i < 5; i++) {
            runs[i].play();
        }

        double longestTime = 0;
        int index = -1;
        for (int i = 0; i < 5; i++) {
            double time = marathoners.get(i).getTimeTaken();
            if (longestTime < time) {
                longestTime = time;
                index = i;
            }
        }

        runs[index].setOnFinished(e -> {
            runFinished();
            //start.setDisable(false);
        });
    }

    @FXML
    private void stopPressed(ActionEvent event) {
        if (isPlaying) {
            travelTime.stop();
            isPlaying = false;
            for (int i = 0; i < 5; i++) {
                runs[i].pause();
            }
        } else {
            for (int i = 0; i < 5; i++) {
                if (runners.get(i).getTranslateX() < runs[i].getToX()) {
                    runs[i].play();
                }
            }
            travelTime.start();
            isPlaying = true;
        }
    }

    @FXML
    private void exitPressed(ActionEvent event) {
        System.exit(0);
    }

    private void runFinished() {
        travelTime.stop();

        Label message = new Label("Race has been finished!");

        double minTime = 100;
        int index = -1;

        for (int i = 0; i < 5; i++) {
            double time = marathoners.get(i).getTimeTaken();
            if (minTime >= time) {

                minTime = time;
                index = i;
            }
        }

        message.setText(message.getText() + "\nThe winner is ");

        MarathonerModel marathoner = marathoners.get(index);
        message.setText(message.getText() + "\n" + marathoner.getName() + " #" + marathoner.getNumber());

        if (marathoners.get(index).getName().equals(selectedPlayer)) {
            money += 2 * bettingAmount;
            message.setText(message.getText() + "\nCongratulations, you bet on the right Player!");

        }
        selectedPlayer = "";
        moneyLabel.setText(String.format("%.2f %s", money, " $"));

        VBox root = new VBox(message);

        message.setFont(
                new Font(20));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 500, 500);
        Stage stage = new Stage();

        stage.setScene(scene);

        stage.setTitle(
                "Race Finished");
        stage.show();

        betButton.setDisable(
                false);

        start.setDisable(
                false);
    }

    private double bettingAmount;
    private String selectedPlayer;

    @FXML
    private void betPressed(ActionEvent event) {
        bettingAmount = bettingAmountSlider.getValue();
        if (bettingAmount == 0) {
            return;
        }
        if (this.money < bettingAmount) {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Sorry, you have less money than what you want to bet.");
            a.showAndWait();
            return;
        }

        selectedPlayer = bettingList.getSelectionModel().getSelectedItem();

        if (selectedPlayer == null) {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Make sure to select the player to bet on.");
            a.showAndWait();
            return;
        }

        money -= bettingAmount;
        moneyLabel.setText(String.format("%.2f %s", money, " $"));
        betButton.setDisable(true);

    }

    private void initializeMarathoners() {
        
        marathoners.add(new MarathonerModel("Jeane", runner1, runner1Animation, 10, 0, 'c'));
        marathoners.add(new MarathonerModel("George", runner2, runner2Animation, 85, 0, 'f'));
        marathoners.add(new MarathonerModel("Kon Fu Master", runner3, runner3Animation, 70, 0, 'n'));
        marathoners.add(new MarathonerModel("Xf(x)^2/dt", runner4, runner4Animation, 5, 0, 'r'));
        marathoners.add(new MarathonerModel("Specimen 9862", runner5, runner5Animation, 51, 0, 'a'));
        

    }

    private int animationIndex = 0;

    private void translationAnimationMaker() {

        /*line1.setTranslateY(-10);
        line1.setTranslateX(10);*/
       
        for (int i = 0; i < 5; i++) {
            runners.get(i).setImage(marathoners.get(i).getImageRunAnimation()[animationIndex]);
            runs[i] = new TranslateTransition(Duration.seconds(1), runners.get(i));
            runs[i].setFromX(0);
            runs[i].setToX(850);

        }

    }

    private static MediaPlayer playerTalk;
    private static MediaPlayer playerAlienShip;

    private void initializeAliens() {
        TranslateTransition t1Alien1 = new TranslateTransition(Duration.seconds(1));
        t1Alien1.setToY(-100);
        t1Alien1.setFromY(0);
        t1Alien1.setFromX(0);
        t1Alien1.setToX(-500);
        TranslateTransition t2Alien1 = new TranslateTransition(Duration.seconds(1.5));
        t2Alien1.setToY(-200);
        t2Alien1.setFromY(-100);
        t2Alien1.setFromX(-500);
        t2Alien1.setToX(30);
        TranslateTransition t3Alien1 = new TranslateTransition(Duration.seconds(2));
        t3Alien1.setToY(-350);
        t3Alien1.setFromY(-200);
        t3Alien1.setFromX(30);
        t3Alien1.setToX(-500);
        SequentialTransition sAlien1 = new SequentialTransition(alienPlane1, t1Alien1, t2Alien1, t3Alien1);
        sAlien1.setAutoReverse(true);
        sAlien1.setCycleCount((int) Duration.INDEFINITE.toSeconds());

        TranslateTransition t1Alien2 = new TranslateTransition(Duration.seconds(2));
        t1Alien2.setToY(0);
        t1Alien2.setFromY(0);
        t1Alien2.setFromX(0);
        t1Alien2.setToX(-500);
        ScaleTransition t2Alien2 = new ScaleTransition(Duration.seconds(2));
        t2Alien2.setToX(4);
        t2Alien2.setToY(4);
        ParallelTransition pAlien2 = new ParallelTransition(alienPlane2, t1Alien2, t2Alien2);
        pAlien2.setAutoReverse(true);
        pAlien2.setCycleCount((int) Duration.INDEFINITE.toSeconds());
        pAlien2.setDelay(Duration.seconds(1.5));
        sAlien1.setDelay(Duration.seconds(1));
        ParallelTransition Aliens = new ParallelTransition(pAlien2, sAlien1);

        Media alienTalk = new Media(getClass().getResource("music/alienShouts.mp3").toExternalForm());

        playerTalk = new MediaPlayer(alienTalk);
        playerTalk.setCycleCount(MediaPlayer.INDEFINITE);
        playerTalk.setVolume(0.3);

        Media alienShip = new Media(getClass().getResource("music/alienShip.mp3").toExternalForm());

        playerAlienShip = new MediaPlayer(alienShip);
        playerAlienShip.setCycleCount(MediaPlayer.INDEFINITE);
        playerAlienShip.setVolume(0.4);

        playerAlienShip.play();
        playerTalk.play();
        Aliens.play();
    }

}
