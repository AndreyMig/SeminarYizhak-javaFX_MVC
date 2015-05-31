package model;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import controllers.ModelListener;

public class ElevatorModel {

	public static String[] imageFileNames = { "defaultElevator.png",
			"elevator-bg.png" };

	private int currentFloor;
	private int destinationFloor;
	private ArrayList<ModelListener> modelListeners;
	private String imageFile = "defaultElevator.png";
	public StringBuilder floorHistory = new StringBuilder();
	public String floorHis = "";
	private String modelId; 
	private boolean[] floors = new boolean[9];
	private static int numOfModels = 0;
	
	
	public ElevatorModel() {
		modelListeners = new ArrayList<ModelListener>();
		this.currentFloor = 1;
		this.destinationFloor = -1;
		this.modelId = "ElevatorModel "+ ++numOfModels;
	}

	public void registerListener(ModelListener listener) {

		modelListeners.add(listener);

	}

	// Called from elevator button pressed
	public boolean addFloor(int newFloor) {
		
		if (newFloor - currentFloor == 0)
			return true;

//		floorHistory.append(newFloor + ", ");

		boolean b = false;
		for (int i = 1; i < floors.length; i++) {
			b |= getFloor(i);
		}

		if (!b) {
			setFloors(true, newFloor);
			
			fireLogToGuiEvent(modelId+" has started moving \n", Color.GREEN);
			
			fireStartElevatorMoveEvent(newFloor);
		} else {
			setFloors(true, newFloor);
			// floors[newFloor] = true;
		}
		return false;

	}

	private void fireLogToGuiEvent(String msg, Color c) {
		for(ModelListener l : modelListeners){
			l.logToGui(msg, c);
		}
	}

	private void fireStartElevatorMoveEvent(int newFloor) {
		for (ModelListener l : modelListeners) {
			l.startElevatorMove(newFloor);
		}
	}

	private synchronized void setFloors(boolean b, int index) {

		floors[index] = b;

	}

	private synchronized boolean getFloor(int index) {

		return floors[index];

	}

	public boolean updateCurrentFloor(int newFloor) {
		this.currentFloor = newFloor;
		boolean wasTrue = getFloor(newFloor);
		setFloors(false, newFloor);
		return wasTrue;
	}

	public void floorChanged() {
		this.currentFloor = destinationFloor;
	}


	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public void changeElevatorImage(String file) {
		this.imageFile = file;
		System.out.println(file);
		fireChangeImageEvent(this.imageFile);

	}

	private void fireChangeImageEvent(String file) {
		for(ModelListener l : modelListeners){
			l.elevatorFileChanged(file);
		}
	}

//	public void addStop
	
	public String getFloorHis() {
		floorHis = floorHistory.toString();

		if (floorHis.endsWith(", ")) {
			floorHis = floorHis.substring(0, floorHis.length() - 2);
		}

		return floorHis;
	}

	// public int getNextFloor2(){
	//
	// }

	public int getNextFloor(int upDown, int currentFloor2) {

		int nextFloorGoingUp = -1;
		int nextFloorGoingDown = -1;
		for (int i = currentFloor2; i < floors.length; i++) {
			if (getFloor(i)) {
				nextFloorGoingUp = i;
				break;
			}

		}
		for (int i = currentFloor2; i > -1; i--) {
			if (getFloor(i)) {
				nextFloorGoingDown = i;
				break;
			}

		}

		if(nextFloorGoingUp<0 &&nextFloorGoingDown<0)
		{
			fireLogToGuiEvent(modelId+" has stopped for passengers at floor "+currentFloor + "\n", Color.BLACK);
			fireLogToGuiEvent(modelId+" has stoped moving \n", Color.RED);
		}
		
		if(upDown>0)
			return nextFloorGoingUp>0 ? nextFloorGoingUp : nextFloorGoingDown;
		
			return nextFloorGoingDown>0 ? nextFloorGoingDown : nextFloorGoingUp;

	}

	public String getModelId() {
		return modelId;
	}

	public void updateFloorStop(int currentFloor) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				fireLogToGuiEvent(modelId+" has stopped for passengers at floor "+currentFloor + "\n", Color.BLACK);
			}
		});
	
	}

	
	
}
