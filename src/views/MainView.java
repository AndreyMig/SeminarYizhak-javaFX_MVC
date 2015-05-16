package views;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import application.Main;
import controllers.ViewListener;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainView {

	
	
	public static Scene scene;
	private ArrayList<ViewListener> listeners;
	
//	private final String BUILDING_IMAGE = "building2.png";
	private final String MAIN_GRID_ID = "#mainGrid";
	private final String ELEVATOR_PANE_ID = "#elevatorPane";
	private final String FXML_FILE_NAME = "momo.fxml";

	private Map<Integer, ToggleButton> toggleButtonsLeft;
	private Map<Integer, ToggleButton> toggleButtonsRight;
	private Timeline elevatorTimeline;
	private final int SCENE_WIDTH = 800;
	
	private final int SCENE_HEIGHT= 1000;
	private final int NUM_OF_FLOORS= 8;
	private ToggleButton aToggleButton;
	private ImageView elevatorImageView;
//	private final String ELEVATOR_INTERIOR_IMAGE = "elev2.png";
	private final String ELEVATOR_INTERIOR_IMAGE = "elevator-bg.png";
	
	//private static final String buildingImage = MainView.class.getResource("images//building.png").toString();
	
	public MainView(Stage stage)
	{
		//initilize array and hashmaps
		listeners = new ArrayList<ViewListener>();
		toggleButtonsLeft = new HashMap<Integer, ToggleButton>();
		toggleButtonsRight= new HashMap<Integer, ToggleButton>();
		
		
		
	}


	public void createScene(Stage stage){

			FXMLLoader fxmlRoot = new FXMLLoader();

			Parent root = null;
			try {
				root = (Parent) fxmlRoot.load(new FileInputStream(new File(
						FXML_FILE_NAME)));
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

		
			Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
			
	
//			TitledPane tPane = (TitledPane) scene.lookup("#TitledPane")
			GridPane gridPane = (GridPane) scene.lookup(MAIN_GRID_ID);
			Pane elevatorPane = (Pane) scene.lookup(ELEVATOR_PANE_ID);
			Pane labelPane = (Pane) scene.lookup("#labelPane");
			Label statusLabel = (Label) scene.lookup("#statusLabel");
			statusLabel.layoutXProperty().bind(stage.widthProperty().divide(2));

			
			
			
			
			int floorCounter = NUM_OF_FLOORS;
			for (int i = 1; i < NUM_OF_FLOORS+1; i++) {
				

				ToggleButton t = new ToggleButton("F"+floorCounter);
				t.setId(""+floorCounter);
				toggleButtonsLeft.put(floorCounter, t);
				setElevatorButtonListener(t, true);
				t.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				ToggleButton t2 = new ToggleButton("F"+floorCounter);
				setElevatorButtonListener(t, false);
				floorCounter--;
				t2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				gridPane.add(t, 0, i);
				gridPane.add(t2, 2, i);
				aToggleButton = t;
			}
			
			String elevatorFileName = fireGetElevatorFileNameEvent();
			
			setElevatorImage(elevatorFileName);
			GridPane.setHalignment(elevatorImageView, HPos.CENTER);
			elevatorImageView.fitWidthProperty().bind(elevatorPane.widthProperty().divide(12));
			elevatorImageView.fitHeightProperty().bind(aToggleButton.heightProperty());
			stage.setScene(scene);
			stage.show();
			
			System.out.println(labelPane.widthProperty());
			
			
			

			elevatorImageView.xProperty().bind(elevatorPane.widthProperty().divide(2.17));
//			elevatorImageView.setLayoutY(toggleButtonsLeft.get(2).getLayoutY());
			elevatorImageView.yProperty().set(toggleButtonsLeft.get(2).getLayoutY());;
			System.out.println(elevatorImageView.yProperty());
//			elevatorImageView.yProperty().bind(toggleButtonsLeft.get(2).layoutYProperty());
			

			
			
			elevatorPane.getChildren().add(elevatorImageView);
	
			
			

//			
			
	
	}
	
	
	private String fireGetElevatorFileNameEvent() {
		
		return listeners.get(0).getElevatorFileName();
	}


	private void setElevatorButtonListener(ToggleButton t, boolean b) {
			
		t.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.err.println(t.getId()+" Clicked");
				if(elevatorImageView.yProperty().doubleValue() == toggleButtonsLeft.get(Integer.parseInt(t.getId())).getLayoutY()
						-toggleButtonsLeft.get(Integer.parseInt(t.getId())).getHeight())
				{
					System.out.println("Blagan");
					t.setSelected(false);
					return;
				}
					
//				t.setDisable(true);
				fireUpChangeFloorEvent(Integer.parseInt(t.getId()));				
			}
			
			
			
		});
		
			
		
		
	}


	public void registerListener(ViewListener listener)
	{
		listeners.add(listener);
	}
	
	
	public void changeElevatorFloor(int oldFloor, int newFloor)
	{
		int abdDiff = Math.abs(newFloor-oldFloor);
		if(elevatorTimeline != null)
			elevatorTimeline.stop();	
		
		System.out.println("new floor "+newFloor);
		System.out.println("old floor "+oldFloor);
		
//		int upDown = newFloor-oldFloor > 0 ? -1 : 1; 
		
		System.out.println("new floor number = "+newFloor);
		//elevatorImageView.yProperty().unbind();
		elevatorTimeline = new Timeline();
		elevatorTimeline.setCycleCount(1);
		elevatorTimeline.setAutoReverse(true);
		
		//System.out.println("elevator = "+elevatorImageView.yProperty().doubleValue());
		//System.out.println("button = "+toggleButtonsLeft.get(newFloor).getLayoutY());
		//System.out.println("location = "+(toggleButtonsLeft.get(newFloor).getLayoutY()
		//		 + toggleButtonsLeft.get(newFloor).getHeight()));
		
		final KeyValue leftDoorKv = new KeyValue(elevatorImageView.yProperty(),(toggleButtonsLeft.get(newFloor).getLayoutY()
				 - toggleButtonsLeft.get(newFloor).getHeight()));
		
		//When animation is finished rebind element y property
		 EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {

	        public void handle(ActionEvent t) {

	        	toggleButtonsLeft.get(newFloor).setLayoutY(toggleButtonsLeft.get(newFloor).getLayoutY()
				 + toggleButtonsLeft.get(newFloor).getHeight());
	        	toggleButtonsLeft.get(newFloor).setSelected(false);
	        	//elevatorImageView.yProperty().bind(toggleButtonsLeft.get(newFloor).layoutYProperty());
	        }
	        
	    };
		
		
		final KeyFrame leftDoorKf = new KeyFrame(Duration.millis(500*abdDiff), onFinished, leftDoorKv);
		elevatorTimeline.getKeyFrames().add(leftDoorKf);
		
		
		
		
		
		elevatorTimeline.play();
		
	}
	
	
	
	private void fireUpChangeFloorEvent(int floorNum)
	{
		for (ViewListener l : listeners) {
			l.changeFloor(floorNum);
		}
		
	}


	public void setElevatorImage(String newFileName) {
		Image elevatorImage = new Image(newFileName);
		elevatorImageView = new ImageView(elevatorImage);
		
	}
	
	

}
