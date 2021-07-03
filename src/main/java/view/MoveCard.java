package view;

import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class MoveCard extends Transition {

    public ImageView imageView;
    public double shib;
    public ImageView imageView2;
    public ImageView lastImageView;
    public ImageView firstImageView;

    public MoveCard(ImageView imageView, ImageView imageView2) {
        shib = (imageView2.getLayoutY() - imageView.getLayoutY()) / (imageView2.getLayoutX() - imageView.getLayoutX());
        this.imageView = imageView;
        this.imageView2 = imageView2;
        this.setCycleDuration(Duration.millis(10000));
        this.setCycleCount(1);
    }


    @Override
    protected void interpolate(double v) {
        if (firstImageView != null)
            imageView.setImage(firstImageView.getImage());
        if (imageView.getLayoutX() != imageView2.getLayoutX() && imageView.getLayoutX() != imageView2.getLayoutX() + 1) {
            imageView.setLayoutX(imageView.getLayoutX() + aaa(imageView2.getLayoutX(), imageView.getLayoutX()));
            imageView.setLayoutY(imageView.getLayoutY() + aaa(imageView2.getLayoutX(), imageView.getLayoutX()) * shib);
        } else {
            this.stop();
            if (lastImageView!=null)
                imageView.setImage(lastImageView.getImage());
            System.out.println("ali2");
        }
    }

    public double aaa(double a, double b) {
        if (a > b) {
            return 2;
        }
        return -2;
    }

    public void setLastImageView(ImageView imageView) {
        lastImageView = imageView;
    }

    public void setFirstImageView(ImageView imageView) {
        firstImageView = imageView;
    }

}