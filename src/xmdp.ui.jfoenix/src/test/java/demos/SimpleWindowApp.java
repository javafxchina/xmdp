package demos;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
public class SimpleWindowApp  extends Application{
	 public static void main(String[] args) {
	        launch(args);
	    }
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		VBox v=new VBox();
		 JFXDecorator decorator = new JFXDecorator(stage,v);
	        decorator.setCustomMaximize(true);
	        decorator.setGraphic(new SVGGlyph(""));
	        
	        stage.setTitle("JFoenix Demo");
	        double width = 800;
	        double height = 600;
	        try {
	            Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
	            width = bounds.getWidth();
	            height = bounds.getHeight() ;
	        }catch (Exception e){ }

	        Scene scene = new Scene(decorator, width, height);
	        final ObservableList<String> stylesheets = scene.getStylesheets();
	        stylesheets.addAll(MainDemo.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
	                           MainDemo.class.getResource("/css/jfoenix-design.css").toExternalForm(),
	                           MainDemo.class.getResource("/css/jfoenix-main-demo.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
	}

}
