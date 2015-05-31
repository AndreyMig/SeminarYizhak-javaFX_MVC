package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import controllers.ModelListener;

public class ElevatorModel {

	public static String[] imageFileNames = { "defaultElevator.png",
			"elevator-bg.png" };

	private int currentFloor;
	private int destinationFloor;
	private ArrayList<ModelListener> modelListeners;
	private String imageFile = "defaultElevator.png";
	private String destFloorString = "";
	public StringBuilder floorHistory = new StringBuilder();
	public String floorHis = "";
	private boolean[] floors = new boolean[9];

	public ElevatorModel() {
		modelListeners = new ArrayList<ModelListener>();
		this.currentFloor = 1;
		this.destinationFloor = -1;
	}

	public void registerListener(ModelListener listener) {

		modelListeners.add(listener);

	}

	// Called from elevator button pressed
	public boolean addFloor(int newFloor) {
		System.out.println("addFloor() with newFloor = " + newFloor);
		System.out.println("addFloor() with currentFloor = " + currentFloor);
		if (newFloor - currentFloor == 0)
			return true;

		floorHistory.append(newFloor + ", ");

		boolean b = false;
		for (int i = 1; i < floors.length; i++) {
			b |= getFloor(i);
		}

		if (!b) {
			System.out.println("doesnt contains true");
			setFloors(true, newFloor);
			// floors[newFloor] = true;
			fireStartElevatorMoveEvent(newFloor);
		} else {
			setFloors(true, newFloor);
			// floors[newFloor] = true;
		}
		return false;

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
		System.out.println("newFloor =" + newFloor);
		this.currentFloor = newFloor;
		boolean wasTrue = getFloor(newFloor);
		setFloors(false, newFloor);
		return wasTrue;
	}

	public void floorChanged() {
		this.currentFloor = destinationFloor;
	}
//
//	private void fireFloorChangedEvent(int oldFloor, int newFloor) {
//
//		for (ModelListener modelListener : modelListeners) {
//
//			modelListener.floorChanged(oldFloor, newFloor);
//
//		}
//
//	}

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

	public String getDestFloorString() {
		return destFloorString;
	}

	public void changeElevatorImage(String file) {
		this.imageFile = file;

	}

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

		System.out.println("getNextFloor() upDown = " + upDown
				+ " currentFloor2 = " + currentFloor2);
		int nextFloorGoingUp = -1;
		int nextFloorGoingDown = -1;
		for (int i = currentFloor2; i < floors.length; i++) {
			if (getFloor(i)) {
				nextFloorGoingUp = i;
				break;
			}

		}
		System.out.println("nextFloorGoingUp = "+nextFloorGoingUp);
		for (int i = currentFloor2; i > -1; i--) {
			if (getFloor(i)) {
				nextFloorGoingDown = i;
				break;
			}

		}

		for (int i = 0; i < floors.length; i++) {
			System.err.print(floors[i] + " ");
		}
		System.out.println();
		
		System.out.println("nextFloorGoingDown = "+nextFloorGoingDown);
		if(upDown>0)
			return nextFloorGoingUp>0 ? nextFloorGoingUp : nextFloorGoingDown;
		
			return nextFloorGoingDown>0 ? nextFloorGoingDown : nextFloorGoingUp;

	}

}
