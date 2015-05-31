package views;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import controllers.ViewListener;

public class MainView {

	public static Scene scene;
	private ArrayList<ViewListener> listeners;

	private final String MAIN_GRID_ID = "#mainGrid";
	private final String ELEVATOR_PANE_ID = "#elevatorPane";
	private final String FXML_FILE_NAME = "elevatorGrid.fxml";

	private Map<Integer, ToggleButton> toggleButtonsLeft;
	private Map<Integer, ToggleButton> toggleButtonsRight;
	private Timeline elevatorTimeline;
	private final int SCENE_WIDTH = 800;

	private final int SCENE_HEIGHT = 1000;
	private final int NUM_OF_FLOORS = 8;
	private ToggleButton aToggleButton;
	private ImageView elevatorImageView;
	
	
	// private final String ELEVATOR_INTERIOR_IMAGE = "elev2.png";
	private final String ELEVATOR_INTERIOR_IMAGE = "elevator-bg.png";

	// private static final String buildingImage =
	// MainView.class.getResource("images//building.png").toString();

	public MainView(Stage stage) {
		// initilize array and hashmaps
		listeners = new ArrayList<ViewListener>();
		toggleButtonsLeft = new HashMap<Integer, ToggleButton>();
		toggleButtonsRight = new HashMap<Integer, ToggleButton>();

	}

	public void createScene(Stage stage) {

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

		// TitledPane tPane = (TitledPane) scene.lookup("#TitledPane")
		GridPane gridPane = (GridPane) scene.lookup(MAIN_GRID_ID);
		Pane elevatorPane = (Pane) scene.lookup(ELEVATOR_PANE_ID);

		int floorCounter = NUM_OF_FLOORS;
		for (int i = 0; i < NUM_OF_FLOORS; i++) {

			ToggleButton t = new ToggleButton("F" + floorCounter);
			t.setId("" + floorCounter);
			toggleButtonsLeft.put(floorCounter, t);
			setElevatorButtonListener(t, true);
			t.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			setElevatorButtonListener(t, false);
			floorCounter--;
			gridPane.add(t, 0, i);
			aToggleButton = t;
		}

		String elevatorFileName = fireGetElevatorFileNameEvent();

		setElevatorImage(elevatorFileName);
		GridPane.setHalignment(elevatorImageView, HPos.CENTER);
		elevatorImageView.fitWidthProperty().bind(
				elevatorPane.widthProperty().divide(12));
		elevatorImageView.fitHeightProperty().bind(
				aToggleButton.heightProperty());
		stage.setScene(scene);
		stage.show();

		elevatorImageView.xProperty().bind(
				elevatorPane.widthProperty().divide(2.17));
		// elevatorImageView.setLayoutY(toggleButtonsLeft.get(2).getLayoutY());
		elevatorImageView.yProperty()
				.set(toggleButtonsLeft.get(1).getLayoutY());

		System.out.println(elevatorImageView.yProperty());
		elevatorImageView.yProperty().bind(
				toggleButtonsLeft.get(1).layoutYProperty());

		elevatorPane.getChildren().add(elevatorImageView);
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	              System.out.println("Stage is closing");
	              fireViewClosingEvent();
	          }
	      }); 
		
		
		
//		changeElevFloor222222(1, 1);
//		changeElevatorFloor(1, 3);
//		moveFloor(1);
	}

	protected void fireViewClosingEvent() {
		for(ViewListener l : listeners)
		{
			l.elevatorViewClosing();
		}
	}

	private String fireGetElevatorFileNameEvent() {

		return listeners.get(0).getElevatorFileName();
	}

	private void setElevatorButtonListener(ToggleButton t, boolean b) {

		t.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
//				System.err.println(t.getId() + " Clicked");
				
			
				if (elevatorImageView.yProperty().doubleValue() == toggleButtonsLeft
						.get(Integer.parseInt(t.getId())).getLayoutY()) {
					t.setSelected(false);
					return;
				}
				
				
				t.setDisable(true);
				
//				for(Entry<Integer, ToggleButton> e : toggleButtonsLeft.entrySet())
//				{
//					if(Integer.parseInt(t.getId()) != e.getKey())
//					{
//						e.getValue().setSelected(false);
//					}
//				}
//				if(t.isSelected())
//				{
//					System.out.println("dasdaddaadsda");
//					t.setSelected(true);
//					return;
//				}
				fireUpChangeFloorEvent(Integer.parseInt(t.getId()));
				
			}

		});

	}

	public void registerListener(ViewListener listener) {
		listeners.add(listener);
	}


	
	
	public void moveFloor(int upDown)
	{
		int currentFloor = fireGetCurrentFloorEvent();
		elevatorImageView.yProperty().unbind();
		elevatorTimeline = new Timeline();
		elevatorTimeline.setCycleCount(1);
		elevatorTimeline.setAutoReverse(true);
		final KeyValue kv = new KeyValue(elevatorImageView.yProperty(),
				toggleButtonsLeft.get(currentFloor+upDown).getLayoutY());
		
		
		
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent t) {

					int newCurFloor = currentFloor+upDown;
					System.out.println("finished");
					boolean isStop = fireUpCurrentFloorUpdate(newCurFloor);
					toggleButtonsLeft.get(newCurFloor).setSelected(false);
					toggleButtonsLeft.get(newCurFloor).setDisable(false);
//					int nextFloor = -1;
//					if(newCurFloor == 8 || newCurFloor == 1)
//						nextFloor = fireGetNextFloorEvent(-1*upDown, newCurFloor);
//					else
					int nextFloor = fireGetNextFloorEvent(upDown, newCurFloor);
					System.out.println("nextFloor = " +nextFloor);
					if(nextFloor<=0)
					{
						elevatorImageView.yProperty().bind(
								toggleButtonsLeft.get(newCurFloor).layoutYProperty());
						return;
					}
						
					
					final int diff = nextFloor-newCurFloor;
					
					int nextElvatorMove = diff<0 ? -1 : 1;
					
					
					stopAtStationAndCallNext(isStop, nextElvatorMove);
				
				
			
			}

		};
		

		final KeyFrame leftDoorKf = new KeyFrame(
				Duration.millis(300), onFinished, kv);
		elevatorTimeline.getKeyFrames().add(leftDoorKf);

		elevatorTimeline.play();
		

	}
	
	
	public void stopAtStationAndCallNext(boolean stopElevator, int upDown){
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					if(stopElevator)
						Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				moveFloor(upDown);
			}
		});
		
		th.start();
		
		
		
	}
	
	protected int fireGetNextFloorEvent(int upDown, int currentFloor) {
		return listeners.get(0).getNextFloor(upDown, currentFloor);
	}

	protected boolean fireUpCurrentFloorUpdate(int newFloor) {
		boolean a = false;
		for(ViewListener l : listeners)
		{
			a = l.updateCurrentFloor(newFloor);
		}
		return a;
	}

	private int fireGetCurrentFloorEvent() {
		return listeners.get(0).getCurrentFloor();
	}

	public void changeElevatorFloor(int oldFloor, int newFloor) {
		double abdDiff = Math.abs(newFloor - oldFloor);

		abdDiff  = Math.abs(elevatorImageView.getY() - toggleButtonsLeft.get(newFloor).getLayoutY());
//		System.out.println("new absDiff = " + abdDiff) ;
		if (elevatorTimeline != null)
			elevatorTimeline.stop();

		elevatorImageView.yProperty().unbind();
		elevatorTimeline = new Timeline();
		elevatorTimeline.setCycleCount(1);
		elevatorTimeline.setAutoReverse(true);

		final KeyValue kv = new KeyValue(elevatorImageView.yProperty(),
				(toggleButtonsLeft.get(newFloor).getLayoutY()));

		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent t) {

				toggleButtonsLeft.get(newFloor).setLayoutY(
						toggleButtonsLeft.get(newFloor).getLayoutY()
								+ toggleButtonsLeft.get(newFloor).getHeight());
				toggleButtonsLeft.get(newFloor).setSelected(false);
				

				elevatorImageView.yProperty().bind(
						toggleButtonsLeft.get(newFloor).layoutYProperty());
			}

		};

		final KeyFrame leftDoorKf = new KeyFrame(
				Duration.millis(5 * abdDiff), onFinished, kv);
		elevatorTimeline.getKeyFrames().add(leftDoorKf);

		elevatorTimeline.play();

	}

	
	private int getFloorbyY(){
		
		System.out.println("elevator y =  " + elevatorImageView.getY());
		
		
		
		return 0;
		
	}
	
	
	private void fireUpChangeFloorEvent(int floorNum) {
		for (ViewListener l : listeners) {
			l.changeFloor(floorNum);
		}

	}

	public void setElevatorImage(String newFileName) {
		Image elevatorImage = new Image(newFileName);
		elevatorImageView = new ImageView(elevatorImage);
		
//		elevatorImageView.setVisible(false);
//		elevatorImageView.setVisible(true);

	}
	
	public void changeElevatorImage(String newFileName)
	{
		Image elevatorImage = new Image(newFileName);
		elevatorImageView.setImage(elevatorImage);
	}

	public void startElevatorMove(int floor) {
		
		int currentFloor = fireGetCurrentFloorEvent();
		if(floor - currentFloor >0)
			moveFloor(1);
		else
			moveFloor(-1);
	}

}
