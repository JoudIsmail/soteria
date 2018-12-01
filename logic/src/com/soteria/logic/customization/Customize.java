package com.soteria.logic.customization;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Customize {
    private static double x=0,y=0;

    public static void onPress(MouseEvent mouseEvent){

        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        x = stage.getX() - mouseEvent.getScreenX();
        y = stage.getY() - mouseEvent.getScreenY();
    }
    public  static void onDrag(MouseEvent event){

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setX(event.getScreenX() + x);
        stage.setY(event.getScreenY() + y);
    }
    public static void closeStage(Event event){
        Node node = (Node) event.getSource();
        ((Stage) node.getScene().getWindow()).close();
    }
    public static void showError(Image image, String string,ImageView imageView,Button button){
        imageView.setImage(image);
        Node icon= imageView;
        Tooltip tooltip= new Tooltip(string);
        tooltip.setStyle("-fx-text-fill: red; -fx-border-color: red;");
        button.setGraphic(icon);
        button.setTooltip(tooltip);
        button.setAlignment(Pos.CENTER);
    }
    public static void restoreError(Button button){
        ImageView imageView=new ImageView();
        button.setPrefSize(21.6,16);
        showError(null,"",imageView,button);
    }
}
