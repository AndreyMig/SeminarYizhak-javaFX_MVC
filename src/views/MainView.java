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

	private Map<Integer, ToggleButton> toggleButtonsMap;
	private Timeline elevatorTimeline;

	private final int SCENE_WIDTH = 800;

	private final int SCENE_HEIGHT = 1000;
	private final int NUM_OF_FLOORS = 8;
	private ToggleButton aToggleButton;
	private ImageView elevatorImageView;

	public MainView(Stage stage) {
		// initilize array and hashmaps
		listeners = new ArrayList<ViewListener>();
		toggleButtonsMap = new HashMap<Integer, ToggleButton>();
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

		GridPane gridPane = (GridPane) scene.lookup(MAIN_GRID_ID);
		Pane elevatorPane = (Pane) scene.lookup(ELEVATOR_PANE_ID);

		addButtonsToGridPane(gridPane);

		// get default elevator image from model and set it
		String elevatorFileName = fireGetElevatorFileNameEvent();
		setElevatorImage(elevatorFileName);

		GridPane.setHalignment(elevatorImageView, HPos.CENTER);
		elevatorImageView.fitWidthProperty().bind(
				elevatorPane.widthProperty().divide(12));
		elevatorImageView.fitHeightProperty().bind(
				aToggleButton.heightProperty());
		stage.setScene(scene);
		stage.show();

		setInitialBinds(elevatorPane);

		elevatorPane.getChildren().add(elevatorImageView);

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				System.out.println("Stage is closing");
				fireViewClosingEvent();
			}
		});

	}

	private void setInitialBinds(Pane elevatorPane) {

		elevatorImageView.xProperty().bind(
				elevatorPane.widthProperty().divide(2.17));
		elevatorImageView.yProperty().set(toggleButtonsMap.get(1).getLayoutY());
		elevatorImageView.yProperty().bind(
				toggleButtonsMap.get(1).layoutYProperty());
	}

	private void addButtonsToGridPane(GridPane gridPane) {
		int floorCounter = NUM_OF_FLOORS;
		for (int i = 0; i < NUM_OF_FLOORS; i++) {

			ToggleButton t = new ToggleButton("F" + floorCounter);
			t.setId("" + floorCounter);
			toggleButtonsMap.put(floorCounter, t);
			setElevatorButtonListener(t, true);
			t.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			setElevatorButtonListener(t, false);
			floorCounter--;
			gridPane.add(t, 0, i);
			aToggleButton = t;
		}

	}

	protected void fireViewClosingEvent() {
		for (ViewListener l : listeners) {
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
				System.err.println("Button "+t.getId()+" clicked");
				if (elevatorImageView.yProperty().doubleValue() == toggleButtonsMap
						.get(Integer.parseInt(t.getId())).getLayoutY()) {
					t.setSelected(false);
					System.err.println("Button action stoped");
					return;
				}

				t.setDisable(true);
				boolean isSameFloor = fireUpChangeFloorEvent(Integer.parseInt(t.getId()));

				if(isSameFloor)
				{
					t.setSelected(false);
					t.setDisable(false);
				}
				
			}

		});

	}

	public void registerListener(ViewListener listener) {
		listeners.add(listener);
	}

	public void startElevatorMove(int floor) {

		int currentFloor = fireGetCurrentFloorEvent();
		if (floor - currentFloor > 0)
			moveFloor(1);
		else
			moveFloor(-1);
	}
	
	public void moveFloor(int upDown) {
		int currentFloor = fireGetCurrentFloorEvent();
		elevatorImageView.yProperty().unbind();
		elevatorTimeline = new Timeline();
		elevatorTimeline.setCycleCount(1);
		elevatorTimeline.setAutoReverse(true);
		final KeyValue kv = new KeyValue(elevatorImageView.yProperty(),
				toggleButtonsMap.get(currentFloor + upDown).getLayoutY());

		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent t) {

				int newCurFloor = currentFloor + upDown;
				System.out.println("finished");
				boolean isStop = fireUpCurrentFloorUpdate(newCurFloor);
				toggleButtonsMap.get(newCurFloor).setSelected(false);
				toggleButtonsMap.get(newCurFloor).setDisable(false);
				int nextFloor = fireGetNextFloorEvent(upDown, newCurFloor);
				System.out.println("nextFloor = " + nextFloor);
				if (nextFloor <= 0) {
					elevatorImageView.yProperty()
							.bind(toggleButtonsMap.get(newCurFloor)
									.layoutYProperty());
					return;
				}

				final int diff = nextFloor - newCurFloor;

				int nextElvatorMove = diff < 0 ? -1 : 1;

				stopAtStationAndCallNext(isStop, nextElvatorMove);

			}

		};

		final KeyFrame mainKeyFrame = new KeyFrame(Duration.millis(300),
				onFinished, kv);
		elevatorTimeline.getKeyFrames().add(mainKeyFrame);

		elevatorTimeline.play();

	}

	public void stopAtStationAndCallNext(boolean stopElevator, int upDown) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					if (stopElevator)
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
		for (ViewListener l : listeners) {
			a = l.updateCurrentFloor(newFloor);
		}
		return a;
	}

	private int fireGetCurrentFloorEvent() {
		return listeners.get(0).getCurrentFloor();
	}

	private boolean fireUpChangeFloorEvent(int floorNum) {
		boolean val = false;
		for (ViewListener l : listeners) {
			val = l.changeFloor(floorNum);
		}
		return val;
	}

	public void setElevatorImage(String newFileName) {
		Image elevatorImage = new Image(newFileName);
		elevatorImageView = new ImageView(elevatorImage);
	}

	public void changeElevatorImage(String newFileName) {
		Image elevatorImage = new Image(newFileName);
		elevatorImageView.setImage(elevatorImage);
	}



}
