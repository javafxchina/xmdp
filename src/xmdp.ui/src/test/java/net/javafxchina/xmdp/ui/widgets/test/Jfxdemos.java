package net.javafxchina.xmdp.ui.widgets.test;


import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.javafxchina.xmdp.ui.util.ResourceUtil;

public class Jfxdemos extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("File");

        final StackPane root = new StackPane();
        AnchorPane editorRoot = new AnchorPane();
        editorRoot.getChildren().add(btn);
        root.getChildren().add(editorRoot);

        Scene scene = new Scene(root, 300, 250);
        String cssUrl = ResourceUtil
				.getFileUrlByRelativePath("css/riboon.css");
        scene.getStylesheets().add(cssUrl);

        primaryStage.setScene(scene);
        primaryStage.show();

        HBox fileRoot = new HBox();
        VBox menu = new VBox();
        menu.setStyle("-fx-background-color: blue;");
        menu.setFillWidth(true);
        Button backBtn = new Button("Left Arrow");
        backBtn.setPrefWidth(100);
        backBtn.getStyleClass().add("custom-menu-button");
        backBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                FadeTransition hideFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
                hideFileRootTransition.setFromValue(1.0);
                hideFileRootTransition.setToValue(0.0);

                FadeTransition showEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
                showEditorRootTransition.setFromValue(0.0);
                showEditorRootTransition.setToValue(1.0);

                showEditorRootTransition.play();
                hideFileRootTransition.play();
                root.getChildren().remove(fileRoot);
            }
        });
        Button infoBtn = new Button("Info");
        infoBtn.setPrefWidth(100);
        infoBtn.getStyleClass().add("custom-menu-button");
        Button newBtn = new Button("New");
        newBtn.setPrefWidth(100);
        newBtn.getStyleClass().add("custom-menu-button");
        Button openBtn = new Button("Open");
        openBtn.setPrefWidth(100);
        openBtn.getStyleClass().add("custom-menu-button");
        menu.getChildren().addAll(backBtn,infoBtn, newBtn, openBtn);
        VBox.setVgrow(infoBtn, Priority.ALWAYS);
        fileRoot.getChildren().add(menu);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().add(fileRoot);
                FadeTransition hideEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
                hideEditorRootTransition.setFromValue(1.0);
                hideEditorRootTransition.setToValue(0.0);

                FadeTransition showFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
                showFileRootTransition.setFromValue(0.0);
                showFileRootTransition.setToValue(1.0);
                hideEditorRootTransition.play();
                showFileRootTransition.play();
            }
        });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}