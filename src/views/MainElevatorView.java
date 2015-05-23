package views;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import controllers.ViewListener;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainElevatorView {

	public static Scene scene;
	private ArrayList<ViewListener> listeners;

	// private final String BUILDING_IMAGE = "building2.png";
	private final String MAIN_GRID_ID = "#mainGrid";
	private final String ELEVATOR_PANE_ID = "#elevatorPane";
	private final String FXML_FILE_NAME = "elevatorGrid.fxml";

	private Map<Integer, ToggleButton> toggleButtonsLeft;
	// private Map<Integer, ToggleButton> toggleButtonsRight;
	private Timeline elevatorTimeline;
	private final int SCENE_WIDTH = 600;
	private double location;
	private boolean flag = false;
	private final int SCENE_HEIGHT = 700;
	private final int NUM_OF_FLOORS = 8;
	private ToggleButton aToggleButton;
	private ImageView elevatorImageView;
	private int fixer = 0;
	// private final String ELEVATOR_INTERIOR_IMAGE = "elev2.png";
	// private final String ELEVATOR_INTERIOR_IMAGE = "elevator-bg.png";
	private Stage stage;

	// private static final String buildingImage =
	// MainView.class.getResource("images//building.png").toString();

	public MainElevatorView(Stage stage) {
		// initilize array and hashmaps
		listeners = new ArrayList<ViewListener>();
		toggleButtonsLeft = new HashMap<Integer, ToggleButton>();
		// toggleButtonsRight= new HashMap<Integer, ToggleButton>();
		this.stage = stage;

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
		// gridPane.setBackground(Background.);
		// ColumnConstraints col1 = new ColumnConstraints();
		// col1.setPercentWidth(25);
		// ColumnConstraints col2 = new ColumnConstraints();
		// col2.setPercentWidth(75);
		// gridPane.getColumnConstraints().addAll(col1);

		// Pane labelPane = (Pane) scene.lookup("#labelPane");
		// System.out.println("labelPane = " +labelPane.);
		// Label statusLabel = (Label) scene.lookup("#statusLabel");
		// statusLabel.layoutXProperty().bind(stage.widthProperty().divide(2));

		int numOfFloors = fireGetNumOfFloor();

		int floorCounter = numOfFloors;
		for (int i = 0; i < numOfFloors; i++) {

			ToggleButton t = new ToggleButton("F" + floorCounter);
			t.setId("" + floorCounter);
			toggleButtonsLeft.put(floorCounter, t);
			setElevatorButtonListener(t, true);
			t.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			// t.prefWidthProperty().bind(stage.widthProperty().divide(50));;
			// ToggleButton t2 = new ToggleButton("F"+floorCounter);
			setElevatorButtonListener(t, false);
			floorCounter--;
			// t2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

			if (i > 7) {
				RowConstraints rc = new RowConstraints();
				rc.setVgrow(Priority.SOMETIMES);
				rc.setPrefHeight(30.0);
				rc.setMinHeight(10.0);
				gridPane.getRowConstraints().add(rc);
				// gridPane.setRowSpan(elevatorPane, ++numOfFloors);
			}

			gridPane.add(t, 0, i);

			// gridPane.add(t2, 2, i);
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

		// System.out.println(labelPane.widthProperty());

		elevatorImageView.xProperty().bind(
				elevatorPane.widthProperty().divide(2.17));
		// elevatorImageView.setLayoutY(toggleButtonsLeft.get(2).getLayoutY());
		elevatorImageView.yProperty()
				.set(toggleButtonsLeft.get(1).getLayoutY());
		;
		System.out.println(elevatorImageView.yProperty());
		elevatorImageView.yProperty().bind(
				toggleButtonsLeft.get(1).layoutYProperty());

		location = elevatorImageView.yProperty().doubleValue()
				+ aToggleButton.heightProperty().doubleValue();
		// location = elevatorImageView.yProperty().doubleValue();
		System.out.println(location);
		elevatorImageView.yProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue observable, Number oldValue,
					Number newValue) {

				double diff = newValue.doubleValue() - location;
				if (Math.abs(diff) < 20) {
					// System.out.println(diff);
					return;

				}

				// double mid1 = (newValue.doubleValue() +
				// aToggleButton.heightProperty().doubleValue())/2;
				// // int currentFloor = fireGetCurrentFloorEvent();
				// ////
				// for(Entry<Integer, ToggleButton> e :
				// toggleButtonsLeft.entrySet() )
				// {
				//
				// //if(elevatorImageView.yProperty().doubleValue() ==
				// e.getValue().getLayoutY())
				//
				// double mid2 = (e.getValue().getLayoutY() +
				// e.getValue().heightProperty().doubleValue())/2;
				//
				// if(checkArea(mid1, mid2))
				// System.out.println("dsaasdasjhdsahdsaldhlasdhalsdhaslkdhlaskhdlas");
				// // if(e.getValue().getLayoutY() == newValue.doubleValue())
				// // System.out.println("the num is " + e.getKey());
				//
				//
				// }

				// System.out.println(aToggleButton.heightProperty().doubleValue());
				// System.out.println(Math.abs(newValue.doubleValue() -
				// location));
				if (Math.abs(newValue.doubleValue() - location) >= aToggleButton
						.heightProperty().doubleValue() + 5) {
					location = newValue.doubleValue();

					if (diff > 0)
						fireFloorChangedEvent(-1);
					else
						fireFloorChangedEvent(1);
					// flag = true;
					if (fireGetCurrentFloorEvent() == 4) {
						int x = 1;
						x = x + 1;
					}
				}

			}
		});

		elevatorPane.getChildren().add(elevatorImageView);

		//

	}

	protected boolean checkArea(double mid1, double mid2) {
		double diff = Math.abs(mid1 - mid2);
		if (diff < 2)
			return true;
		return false;
	}

	protected void fireFloorChangedEvent(int i) {
		for (ViewListener l : listeners) {

			l.floorChanged(i);
		}
	}

	private String fireGetElevatorFileNameEvent() {

		return listeners.get(0).getElevatorFileName();
	}

	private int fireGetNumOfFloor() {

		return listeners.get(0).getNumOfFloors();
	}

	private void setElevatorButtonListener(ToggleButton t, boolean b) {

		t.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// System.err.println(t.getId()+" Clicked");
				if (elevatorImageView.yProperty().doubleValue() == toggleButtonsLeft
						.get(Integer.parseInt(t.getId())).getLayoutY()) {
					System.out.println("Blagan");
					t.setSelected(false);
					return;
				}
				if (!t.isSelected()) {
					System.out.println("sadasdasd");
					t.setSelected(true);
					return;
				}

				fireUpChangeFloorEvent(Integer.parseInt(t.getId()));
			}

		});

	}

	public void registerListener(ViewListener listener) {
		listeners.add(listener);
	}

	public void stopForPassengers(int currentFloor) {
		System.out
				.println("stopForPassengers() currentFloor = " + currentFloor);
		this.toggleButtonsLeft.get(currentFloor).setSelected(false);

		// elevatorImageView.yProperty().unbind();
		// if(elevatorTimeline != null)
		// {
		// elevatorTimeline.stop();
		//
		// }
		// System.out.println("stopForPassengers(), next floor = "+nextFloor
		// +" curr = "+currentFloor);
		// // onMovementFinishedMethod(nextFloor, nextFloor-currentFloor);
		// try {
		// synchronized (elevatorTimeline) {
		// elevatorTimeline.wait(1500);
		// }
		//
		//
		// } catch (InterruptedException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		// try {
		// Thread.sleep(1500);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }

		// System.out.println("stopForPassengers(), next floor = "+nextFloor
		// +" curr = "+currentFloor);
		// changeElevatorFloor(currentFloor, nextFloor);
		// return;

	}

	public void changeElevatorFloor(int oldFloor, int newFloor) {

		int diff = newFloor - oldFloor;
		int absdDiff = Math.abs(diff);
		if (elevatorTimeline != null)
			elevatorTimeline.stop();

		System.out.println("new floor " + newFloor);
		System.out.println("old floor " + oldFloor);

		// int upDown = newFloor-oldFloor > 0 ? -1 : 1;

		System.out.println("new floor number = " + newFloor);
		elevatorImageView.yProperty().unbind();
		// stage.setResizable(false);
		elevatorTimeline = new Timeline();
		elevatorTimeline.setCycleCount(1);
		elevatorTimeline.setAutoReverse(true);

		// System.out.println("elevator = "+elevatorImageView.yProperty().doubleValue());
		// System.out.println("button = "+toggleButtonsLeft.get(newFloor).getLayoutY());
		// System.out.println("location = "+(toggleButtonsLeft.get(newFloor).getLayoutY()
		// + toggleButtonsLeft.get(newFloor).getHeight()));

		final KeyValue elevatorKeyValue = new KeyValue(
				elevatorImageView.yProperty(),
				(toggleButtonsLeft.get(newFloor).getLayoutY()));

		// When animation is finished rebind element y property
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent t) {

				onMovementFinishedMethod(newFloor, diff);

			}
		};

		elevatorImageView.yProperty().unbind();
		final KeyFrame leftDoorKf = new KeyFrame(
				Duration.millis(500 * absdDiff), onFinished, elevatorKeyValue);
		elevatorTimeline.getKeyFrames().add(leftDoorKf);

		elevatorTimeline.play();

	}

	private void onMovementFinishedMethod(int newFloor, int diff) {
		toggleButtonsLeft.get(newFloor).setLayoutY(
				toggleButtonsLeft.get(newFloor).getLayoutY());
		toggleButtonsLeft.get(newFloor).setSelected(false);
		// System.out.println("change from on finished");
		// System.out.println("new location = " +location);
		fixer++;
		if (fixer > 1) {
			if (diff < 0)
				fireFloorChangedEvent(-1);
			else {
				fireFloorChangedEvent(1);

			}
			fixer = 0;
		}

		//

		System.out.println("handle! newfloor = " + newFloor);

		elevatorImageView.yProperty().bind(
				toggleButtonsLeft.get(newFloor).layoutYProperty());
		int currentFloor = fireGetCurrentFloorEvent();
		int destFloor = fireGetDestinationFloorEvent();
		System.out.println("onfinish dest = " + destFloor);
		if (destFloor > 0)
			changeElevatorFloor(currentFloor, destFloor);
	}

	private int fireGetCurrentFloorEvent() {
		return listeners.get(0).getCurrentFloor();
	}

	private int fireGetDestinationFloorEvent() {
		return this.listeners.get(0).getDestinationFloor();
	}

	private void fireUpChangeFloorEvent(int floorNum) {
		for (ViewListener l : listeners) {
			l.addFloor(floorNum);

			// l.changeFloor(floorNum);
		}

	}

	public void setElevatorImage(String newFileName) {
		Image elevatorImage = new Image(newFileName);
		elevatorImageView = new ImageView(elevatorImage);

	}

}
