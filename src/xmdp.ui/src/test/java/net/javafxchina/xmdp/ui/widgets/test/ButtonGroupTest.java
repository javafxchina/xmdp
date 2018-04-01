package net.javafxchina.xmdp.ui.widgets.test;



import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.javafxchina.xmdp.ui.widgets.ButtonGroup;

public class ButtonGroupTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root=new VBox();
		
		ButtonGroup bg=new ButtonGroup();
		root.getChildren().add(bg);
		ButtonGroup bg2=new ButtonGroup();
		root.getChildren().add(bg2);
		Scene scene=new Scene(root,800,600,Color.AZURE);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		bg.setStyle("-fx-background-color: red");
		bg.setTitle("hello World");
		bg.setGap(35);
		Button btn=new Button("hello2\nhi");
		btn.setAlignment(Pos.CENTER);
		btn.setPrefHeight(100);
		btn.setPrefWidth(100);
		bg.addButton("b2", btn);
		bg.addButton("b3", new Button("hello3"));
		bg.addButton("b4", new Button("hello4"));
		bg.addButton("b5", new Button("hello5"));
		bg.addButton("b6", new Button("hello6"));
		bg.addButton("b7", new Button("hello7"));
		bg.addButton("b8", new Button("hello8"));
		bg.addButton("b9", new Button("hello9"));
		bg.addButton("b10", new Button("hello10"));
		bg.addButton("b11", new Button("hello11"));
		bg.addButton("b12", new Button("hello12"));
		
		bg2.setStyle("-fx-background-color: blue");
		bg2.setTitle("hello World");
		Button btn2=new Button("hello2\nhi");
		btn2.setAlignment(Pos.CENTER);
		bg2.addButton("b2", btn2);
		bg2.addButton("b3", new Button("hello3"));
		bg2.addButton("b4", new Button("hello4"));
		
	}

	public static void main(String[] args) {
		ButtonGroupTest.launch(args);
	}
}
