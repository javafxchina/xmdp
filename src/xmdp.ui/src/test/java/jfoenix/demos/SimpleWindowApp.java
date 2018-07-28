package jfoenix.demos;

import java.util.List;

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
	            Rectangle2D bounds = getBiggestViewableRectangle(stage);
	            width = bounds.getWidth();
	            height = bounds.getHeight() ;
	        }catch (Exception e){ }

	        Scene scene = new Scene(decorator, width, height);
	        final ObservableList<String> stylesheets = scene.getStylesheets();
	        stylesheets.addAll(
//	        		MainDemo.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
//	                           MainDemo.class.getResource("/css/jfoenix-design.css").toExternalForm(),
	                           MainDemo.class.getResource("/css/jfoenix-main-demo.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
	}
	 protected Rectangle2D getBiggestViewableRectangle(Stage stage) {

	        Rectangle2D res;

	        if (Screen.getScreens().size() == 1) {
	            res = Screen.getPrimary().getVisualBounds();
	        } else {
	            Rectangle2D stageRect = new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
	            List<Screen> screens = Screen.getScreensForRectangle(stageRect);

	            // The stage is entirely rendered on one screen, which is either the
	            // primary one or not, we don't care here.
//	            if (screens.size() == 1) {
	                res = screens.get(0).getVisualBounds();
//	            } else {
	                // The stage is spread over several screens.
	                // We compute the surface of the stage on each on the involved
	                // screen to select the biggest one == still to be implemented.
//	                TreeMap<String, Screen> sortedScreens = new TreeMap<>();
	//
//	                for (Screen screen : screens) {
//	                    computeSurface(screen, stageRect, sortedScreens);
//	                }
	//
//	                res = sortedScreens.get(sortedScreens.lastKey()).getVisualBounds();
//	            }
	        }

	        return res;
	    }

}
