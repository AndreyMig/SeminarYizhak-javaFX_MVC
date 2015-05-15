package views;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Timer;

import application.Main;
import controllers.ViewListener;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainView {

	
	
	public static Scene scene;
	private ArrayList<ViewListener> listeners;
	
//	private final String BUILDING_IMAGE = "building2.png";
	private final String MAIN_GRID_ID = "#mainGrid";
	private final String ELEVATOR_PANE_ID = "#elevatorPane";

	
	
	
	private final int SCENE_WIDTH = 800;
	
	private final int SCENE_HEIGHT= 1000;
	private final int NUM_OF_FLOORS= 8;

//	private final String ELEVATOR_INTERIOR_IMAGE = "elev2.png";
	private final String ELEVATOR_INTERIOR_IMAGE = "elevator-bg.png";
	
	//private static final String buildingImage = MainView.class.getResource("images//building.png").toString();
	
	public MainView(Stage stage)
	{
		
		listeners = new ArrayList<ViewListener>();
		createScene( stage);
		

	}


	private void createScene(Stage stage){

			FXMLLoader fxmlRoot = new FXMLLoader();

			Parent root = null;
			try {
				root = (Parent) fxmlRoot.load(new FileInputStream(new File(
						"jojo.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

		
			Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
			
	
			TitledPane tPane = (TitledPane) scene.lookup("#titledPane");
			System.err.println(tPane);
			GridPane gridPane = (GridPane) scene.lookup("#mainGrid");
			Pane elevatorPane = (Pane) scene.lookup(ELEVATOR_PANE_ID);
			System.out.println(gridPane);
			
//			ToggleButton at = null;
//			int floorCounter = NUM_OF_FLOORS;
//			for (int i = 0; i < 8; i++) {
//				
////				Rectangle r= new Rectangle();
////				r.widthProperty().bind(p.widthProperty());
////				r.heightProperty().bind(p.heightProperty());
////				Color c = i%2==0 ? Color.BLACK : Color.WHITESMOKE;   
////				r.setFill(c);
////				pane.add(r, 1, i);
//				
//				ToggleButton t = new ToggleButton("F"+floorCounter);
////				setElevatorButtonListener(t, true);
//				t.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//				ToggleButton t2 = new ToggleButton("F"+floorCounter);
////				setElevatorButtonListener(t, false);
//				floorCounter--;
//				t2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//				gridPane.add(t, 0, i);
//				gridPane.add(t2, 2, i);
//				at = t;
//			}
			
//			Image elevatorImage = new Image(ELEVATOR_INTERIOR_IMAGE);
//			ImageView elevatorImageView = new ImageView(elevatorImage);
//			GridPane.setHalignment(elevatorImageView, HPos.CENTER);
//			elevatorImageView.fitWidthProperty().bind(elevatorPane.widthProperty().divide(12));
//			elevatorImageView.fitHeightProperty().bind(at.heightProperty());
//			stage.setScene(scene);
//			stage.show();
//			
//			System.out.println(elevatorImageView.fitHeightProperty().doubleValue());
//			
//			
//			
//
//			elevatorImageView.xProperty().bind(elevatorPane.widthProperty().divide(2.17));
//			elevatorImageView.yProperty().bind(at.layoutYProperty());
//			
//
//			
//			
//			elevatorPane.getChildren().add(elevatorImageView);
	
			
			
//			elevatorImageView.yProperty().unbind();
//			final Timeline leftDoorTimeline = new Timeline();
//			leftDoorTimeline.setCycleCount(1);
//			leftDoorTimeline.setAutoReverse(true);
//			final KeyValue leftDoorKv = new KeyValue(elevatorImageView.yProperty(), 200);
//			final KeyFrame leftDoorKf = new KeyFrame(Duration.millis(5000), leftDoorKv);
//			leftDoorTimeline.getKeyFrames().add(leftDoorKf);
//			
//			leftDoorTimeline.play();
//			
			
	
	}
	
	
	public void registerListener(ViewListener listener)
	{
		listeners.add(listener);
	}
	
	
	public void changeElevatorFloor(int floorNum)
	{
		
	}
	
	
	
	private void fireUpChangeFloorEvent(int floorNum)
	{
		
		for (ViewListener l : listeners) {
			l.changeFloor(floorNum);
		}
		
	}
	
	

}
