package model;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.paint.Stop;
import controllers.ModelListener;

public class ElevatorModel {

	public static String[] imageFileNames = { "defaultElevator.png",
			"elevator-bg" };

	private final int DEAFULT_NUM_OF_FLOORS = 8;

	private enum elevatorMovement {
		STOP, UP, DOWN
	}

	private int numFloors = -1;
	private elevatorMovement currentElevatorState;
	private int currentFloor;
	private int destinationFloor;

	private ArrayList<ModelListener> modelListeners;
	private String imageFile = "defaultElevator.png";
	private String elevatorPath = "";

	private boolean[] floorStops;

	public ElevatorModel(int numOfFloor) {
		this();
		this.numFloors = numOfFloor;
	}

	public ElevatorModel() {
		modelListeners = new ArrayList<ModelListener>();
		this.currentFloor = 1;
		this.destinationFloor = -1;
		if(this.numFloors > 0)
			floorStops = new boolean[this.numFloors];
		else
			floorStops = new boolean[this.DEAFULT_NUM_OF_FLOORS];
		this.currentElevatorState = elevatorMovement.STOP;
	}

	public void registerListener(ModelListener listener) {

		modelListeners.add(listener);

	}

	public void addFloor(int newFloor) {

		int diff = newFloor - this.currentFloor;
		System.out.println("diff= "+diff);
		int oldDestFloor = this.destinationFloor;
		floorStops[newFloor] = true;
		switch (this.currentElevatorState) {
		case STOP:
			this.destinationFloor = newFloor;
			// start moving to dest
			break;

		case UP:

			if (this.destinationFloor > newFloor)
				this.destinationFloor = newFloor;
			// start moving to dest
			break;
		case DOWN:
			if (this.destinationFloor < newFloor)
				this.destinationFloor = newFloor;
			// start moving to dest
			break;
		}

		if (diff == 0)
			return;
		else if (diff > 0)
			this.currentElevatorState = elevatorMovement.UP;
		else if (diff < 0)
			this.currentElevatorState = elevatorMovement.DOWN;
		
		if(oldDestFloor != this.destinationFloor)
			fireFloorChangedEvent(this.destinationFloor);
	}

	
	
	public void floorChanged(int newFloor) {
		this.currentFloor = newFloor;

		
		switch (this.currentElevatorState) {
		case STOP:
			this.destinationFloor = handleStartMovement();
			
			
			break;

		case UP:
			this.destinationFloor = handleUpMovement();
			if(this.destinationFloor < 0)
			{
				this.currentElevatorState = elevatorMovement.STOP;
				this.destinationFloor = handleStartMovement();
			}
			
			break;
		case DOWN:
			this.destinationFloor = handleDownMovement();
			if(this.destinationFloor < 0)
			{
				this.currentElevatorState = elevatorMovement.STOP;
				this.destinationFloor = handleStartMovement();
			}
			break;
		}
		
	
		
		if(this.destinationFloor > 0)
			fireFloorChangedEvent(this.destinationFloor);
	}

	private int handleDownMovement() {
		for (int i = this.currentFloor-1; i >= 0; i--) {
			if(floorStops[i] == true)
			{
				this.destinationFloor = i;
				break;
			}
		}
		return -1;
	}

	private int handleUpMovement() {
		for (int i = this.currentFloor; i < floorStops.length; i++) {
			if(floorStops[i] == true)
			{
				return this.destinationFloor = i;
			}
		}
		return -1;
}

	private int handleStartMovement() {
		for (int i = 0; i < floorStops.length; i++) {
			if(floorStops[i] == true)
			{
				return this.destinationFloor = i;
			}
		}
		return this.currentFloor;
		
}

	private void fireFloorChangedEvent(int newFloor) {

		for (ModelListener modelListener : modelListeners) {

			modelListener.floorChanged(this.currentFloor, newFloor);

		}

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

	public int getNumOfFloors() {
		return (this.numFloors > 0) ? this.numFloors
				: this.DEAFULT_NUM_OF_FLOORS;
	}

}
