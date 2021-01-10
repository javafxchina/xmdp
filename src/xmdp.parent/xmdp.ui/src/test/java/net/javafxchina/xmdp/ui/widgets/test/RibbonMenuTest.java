package net.javafxchina.xmdp.ui.widgets.test;



import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.javafxchina.xmdp.ui.widgets.RibbonMenu;

public class RibbonMenuTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root=new BorderPane();
		
		
		Scene scene=new Scene(root,800,600,Color.AZURE);
		
		RibbonMenu rm=new RibbonMenu();
		root.setLeft(rm);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Button btn=new Button("hello2\nhi");
		btn.setAlignment(Pos.CENTER);
//		rm.setTopButton(btn);
		rm.addButton("b3", new Button("hello3"));
		rm.addButton("b4", new Button("hello4"));
		rm.addButton("b5", new Button("hello5"));
		rm.addButton("b6", new Button("hello6"));
		rm.addButton("b7", new Button("hello7"));
		rm.addButton("b8", new Button("hello8"));
		rm.addButton("b9", new Button("hello9"));
		rm.addButton("b10", new Button("hello10"));
		rm.addButton("b11", new Button("hello11"));
		rm.addButton("b12", new Button("hello12"));
	}

	public static void main(String[] args) {
		RibbonMenuTest.launch(args);
	}
}
