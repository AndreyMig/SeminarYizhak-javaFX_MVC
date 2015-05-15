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

	Map<Integer, ToggleButton> toggleButtonsLeft;
	Map<Integer, ToggleButton> toggleButtonsRight;
	
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
		
		listeners = new ArrayList<ViewListener>();
		toggleButtonsLeft = new HashMap<Integer, ToggleButton>();
		toggleButtonsRight= new HashMap<Integer, ToggleButton>();
		createScene( stage);
		
	}


	private void createScene(Stage stage){

			FXMLLoader fxmlRoot = new FXMLLoader();

			Parent root = null;
			try {
				root = (Parent) fxmlRoot.load(new FileInputStream(new File(
						"momo.fxml")));
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
//			statusLabel.scaleYProperty().bind(labelPane.heightProperty().divide(60));
//			
//			statusLabel.scaleXProperty().bind(labelPane.widthProperty().divide(60));
//			Font f = new Font((Double.MAX_VALUE));
//			
//			f.
//			statusLabel.setFont(f);
//			statusLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			
			
			
			
			int floorCounter = NUM_OF_FLOORS;
			for (int i = 1; i < NUM_OF_FLOORS+1; i++) {
				
//				Rectangle r= new Rectangle();
//				r.widthProperty().bind(p.widthProperty());
//				r.heightProperty().bind(p.heightProperty());
//				Color c = i%2==0 ? Color.BLACK : Color.WHITESMOKE;   
//				r.setFill(c);
//				pane.add(r, 1, i);
				
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
			
			Image elevatorImage = new Image(ELEVATOR_INTERIOR_IMAGE);
			elevatorImageView = new ImageView(elevatorImage);
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
	
	
	private void setElevatorButtonListener(ToggleButton t, boolean b) {
			
		t.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.err.println(t.getId()+" Clicked");
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
		
		System.out.println(newFloor);
		System.out.println(oldFloor);
		
		int upDown = newFloor-oldFloor > 0 ? -1 : 1; 
		
		System.out.println("new floor number = "+newFloor);
		//elevatorImageView.yProperty().unbind();
		final Timeline leftDoorTimeline = new Timeline();
		leftDoorTimeline.setCycleCount(1);
		leftDoorTimeline.setAutoReverse(true);
		
		System.out.println("elevator = "+elevatorImageView.yProperty().doubleValue());
		System.out.println("button = "+toggleButtonsLeft.get(newFloor-1).getLayoutY());
		System.out.println("location = "+(toggleButtonsLeft.get(newFloor+upDown).getLayoutY()
				 + toggleButtonsLeft.get(newFloor-1).getHeight()));
		final KeyValue leftDoorKv = new KeyValue(elevatorImageView.yProperty(),(toggleButtonsLeft.get(newFloor).getLayoutY()
				 - toggleButtonsLeft.get(newFloor-1).getHeight()));
		
		//When animation is finished rebind element y property
		 EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {

	        public void handle(ActionEvent t) {

	        	toggleButtonsLeft.get(newFloor).setLayoutY(toggleButtonsLeft.get(newFloor-1).getLayoutY()
				 + toggleButtonsLeft.get(newFloor-1).getHeight());
	        	toggleButtonsLeft.get(newFloor).setSelected(false);
	        	//elevatorImageView.yProperty().bind(toggleButtonsLeft.get(newFloor).layoutYProperty());
	        }
	        
	    };
		
		
		final KeyFrame leftDoorKf = new KeyFrame(Duration.millis(500*abdDiff), onFinished, leftDoorKv);
		leftDoorTimeline.getKeyFrames().add(leftDoorKf);
		
		
		
		
		
		leftDoorTimeline.play();
		
	}
	
	
	
	private void fireUpChangeFloorEvent(int floorNum)
	{
		for (ViewListener l : listeners) {
			l.changeFloor(floorNum);
		}
		
	}
	
	

}
