package jfoenix.demos.components;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;


public class AlertDemo extends Application {

    @Override
    public void start(Stage stage) {

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setBody(new Label("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."));

        Button leftButton = new JFXButton("Alert");
        leftButton.setLayoutX(50);
        leftButton.setLayoutY(50);

        final Scene scene = new Scene(new Group(leftButton), 800, 800);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

        JFXAlert<Void> alert = new JFXAlert<>(stage);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(layout);
        alert.initModality(Modality.NONE);
        leftButton.setOnAction(action-> alert.show());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
