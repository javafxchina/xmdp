package net.javafxchina.xmdp.ui.widgets.test;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.javafxchina.xmdp.ui.widgets.RibbonHome;

public class RibbonHomeTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();

		Scene scene = new Scene(root, 800, 600, Color.AZURE);

		RibbonHome rm = new RibbonHome();
		root.setCenter(rm);
		//全屏
		rm.prefHeightProperty().bind(scene.heightProperty());
		rm.prefWidthProperty().bind(scene.widthProperty());

		primaryStage.setScene(scene);
		primaryStage.show();

		rm.addMenuButton("b3", new Button("hello3"));
		rm.addMenuButton("b4", new Button("hello4"));
		rm.addMenuButton("b5", new Button("hello5"));
		rm.addMenuButton("b6", new Button("hello6"));
		rm.addMenuButton("b7", new Button("hello7"));
		rm.addMenuButton("b8", new Button("hello8"));
		rm.addMenuButton("b9", new Button("hello9"));
		rm.addMenuButton("b10", new Button("hello10"));
		rm.addMenuButton("b11", new Button("hello11"));
		rm.addMenuButton("b12", new Button("hello12"));

		rm.addPageButton("b3", "buttonKey11", new Button("menu1"));
		rm.addPageButton("b3", "buttonKey12", new Button("menu1"));
		rm.addPageButton("b3", "buttonKey13", new Button("menu1"));
		
		rm.addPageButton("b3", "groupName", "buttonKey1", new Button("menu1"));
		rm.addPageButton("b3", "groupName", "buttonKey2", new Button("menu1"));
		rm.addPageButton("b3", "groupName", "buttonKey3", new Button("menu1"));
		rm.addPageButton("b3", "groupName", "buttonKey4", new Button("menu1"));
		rm.addPageButton("b3", "groupName", "buttonKey5", new Button("menu1"));
		rm.addPageButton("b3", "groupName", "buttonKey6", new Button("menu1"));
		
		
		
		rm.addPageButton("b10", "groupName", "buttonKey", new Button("menu1"));
		rm.addPageButton("b10", "", "buttonKey2", new Button("menu2"));
		
		rm.addPageButton("b10", "groupName2", "buttonKey", new Button("menu1"));
		
		rm.showFirstPage();
	}

	public static void main(String[] args) {
		RibbonHomeTest.launch(args);
	}
}
