
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author 2248826
 */
public class MarathonerModel {

    private String name;
    private ImageView imageModel;
    private Image[] imageRunAnimation = new Image[2];
    private int number;
    private double speed;
    private char atribute1;
    private double timeTaken;

    public MarathonerModel(String name, ImageView imageModel, Image[] imageRunAnimation, int number, double timeTaken, char atribute1) {
        this.name = name;
        this.imageModel = imageModel;
        this.imageRunAnimation[0] = imageRunAnimation[0];
        this.imageRunAnimation[1] = imageRunAnimation[1];
        this.number = number;
        this.atribute1 = atribute1;
        this.timeTaken = timeTaken;
        switch (atribute1) {
            case 'a'/*alien*/:
                this.speed += 1;
            case 'r'/*robot*/:
                this.speed += 0.8;
            case 'n'/*ninja*/:
                this.speed += 0.5;
            case 'c'/*crazy woman*/:
                this.speed += 0.2;

        }
    }

    public String getName() {
        return name;
    }

    public ImageView getImageModel() {
        return imageModel;
    }

    public Image[] getImageRunAnimation() {
        return imageRunAnimation;
    }

    public int getNumber() {
        return number;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public char getAtribute1() {
        return atribute1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageModel(ImageView imageModel) {
        this.imageModel = imageModel;
    }

    public void setImageRunAnimation(Image[] imageRunAnimation) {
        this.imageRunAnimation = imageRunAnimation;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTimeTaken(double timeTaken) {
        this.timeTaken = timeTaken;
    }

}
