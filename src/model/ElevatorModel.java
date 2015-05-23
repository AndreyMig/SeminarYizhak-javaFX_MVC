package model;

import java.util.ArrayList;
import java.util.Queue;

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
	private StringBuilder destinationsFormat;
	private String imageFile = "defaultElevator.png";
	private String elevatorPath = "";

	private ArrayList<Integer> destFloors;

	Queue<Integer> floorDestsQueue;
	int first;
	private boolean[] floorStops;

	public ElevatorModel(int numOfFloor) {
		this();
		this.numFloors = numOfFloor;
	}

	public ElevatorModel() {
		modelListeners = new ArrayList<ModelListener>();
		this.currentFloor = 1;
		this.destinationFloor = -1;
		if (this.numFloors > 0)
			floorStops = new boolean[this.numFloors];
		else
			floorStops = new boolean[this.DEAFULT_NUM_OF_FLOORS];
		this.currentElevatorState = elevatorMovement.STOP;

		destFloors = new ArrayList<Integer>();
		destinationsFormat = new StringBuilder();
	}

	public void registerListener(ModelListener listener) {

		modelListeners.add(listener);

	}

	// public void addFloor2(int newFloor) {
	//
	// floorStops[newFloor] = true;
	// if (this.destinationFloor == -1) {
	// this.destinationFloor = newFloor;
	// fireFloorChangedEvent(newFloor);
	// }
	//
	// }

	public void addFloor(int newFloor) {

		int diff = newFloor - this.currentFloor;
//		System.err.println("addFloor() diff= " + diff + " newFloor ="
//				+ newFloor + " elevator state = " + this.currentElevatorState.toString());
		int oldDestFloor = this.destinationFloor;

		if (destFloors.size() == 0) {
//			System.out.println("heeeerrrrreeeeee");
			destFloors.add(newFloor);
			this.destinationFloor = newFloor;
			updateMovementStatus(newFloor, this.currentFloor);
			fireFloorChangedEvent(newFloor);
			// start movement
		} else {

			switch (this.currentElevatorState) {
			case STOP:
				
				break;
				
			case UP:
//				System.out.println("up");
				handleUpAddition(destFloors, newFloor);

				// start moving to dest
				break;
			case DOWN:
//				System.out.println("DOWN");
				handleDownAddition(destFloors, newFloor);
				// start moving to dest
				break;

			default:
				break;
			}

		}
		System.err.println("dests: " + getDestinationsString());
		this.destinationFloor = destFloors.get(0);

		// if (oldDestFloor != this.destinationFloor)
		// fireFloorChangedEvent(newFloor);

	}

	private void handleDownAddition(ArrayList<Integer> destFloors2, int newFloor) {

		if (newFloor < this.currentFloor) {

			for (int i = 0; i < destFloors.size(); i++) {

				int nextFloor = destFloors.get(i);

				if (newFloor > nextFloor) {
					destFloors.add(i, newFloor);
					return;
				} else if (nextFloor > currentFloor) {
					destFloors.add(i, newFloor);
					return;
				}

			}
			destFloors.add(newFloor);

		} else {
			for (int i = destFloors.size() - 1; i > -1; i--) {
					System.out.println("pay attention");
				int nextFloor = destFloors.get(i);
				if (newFloor > nextFloor) {
					destFloors.add(i+1, newFloor);
					break;
				}

			}
		}

	}

	private void handleUpAddition(ArrayList<Integer> destFloors, int newFloor) {

		if (newFloor < this.currentFloor) {
			for (int i = destFloors.size() - 1; i > -1; i--) {

				int nextFloor = destFloors.get(i);
				if (newFloor < nextFloor) {
					destFloors.add(i+1, newFloor);
					
					return;
				}
				
//				//if last floors in array are lower then current
//				if(nextFloor < this.currentFloor)
//				{
//					if (newFloor < nextFloor) {
//						destFloors.add(i, newFloor);
//						
//						return;
//					}
//					
//				}
//				//if last floors in array are higher then current
//				else{
//					if (newFloor < nextFloor) {
//						destFloors.add(i, newFloor);
//						
//						return;
//					}
//				}
				

			}
		} else {
			for (int i = 0; i < destFloors.size(); i++) {

				int nextFloor = destFloors.get(i);

				if (newFloor < nextFloor) {
					destFloors.add(i, newFloor);
					return;
				}

			}
			destFloors.add(newFloor);
		}

	}

	private void updateMovementStatus(int newFloor, int currentFloor) {

		int diff = newFloor - currentFloor;
		if (diff < 0)
			this.currentElevatorState = elevatorMovement.DOWN;
		if (diff > 0)
			this.currentElevatorState = elevatorMovement.UP;
		else if(diff == 0)
			this.currentElevatorState = elevatorMovement.STOP;
		
//		System.out.println("elevator status = " + this.currentElevatorState.toString());

	}

	// public void addFloor(int newFloor) {
	//
	// int diff = newFloor - this.currentFloor;
	// System.out.println("diff= " + diff);
	// int oldDestFloor = this.destinationFloor;
	// floorStops[newFloor - 1] = true;
	// switch (this.currentElevatorState) {
	// case STOP:
	// this.destinationFloor = newFloor;
	// // start moving to dest
	// break;
	//
	// case UP:
	//
	// if (this.destinationFloor > newFloor)
	// this.destinationFloor = newFloor;
	// // start moving to dest
	// break;
	// case DOWN:
	// if (this.destinationFloor < newFloor)
	// this.destinationFloor = newFloor;
	// // start moving to dest
	// break;
	// }
	//
	// if (diff == 0)
	// return;
	// else if (diff > 0)
	// this.currentElevatorState = elevatorMovement.UP;
	// else if (diff < 0)
	// this.currentElevatorState = elevatorMovement.DOWN;
	//
	// if (oldDestFloor != this.destinationFloor)
	// fireFloorChangedEvent(this.destinationFloor);
	// }

	// xxxxxxxxxxxxx
	public void onFloorChanged(int num) {
		this.currentFloor += num;

		System.out.println("onFloorChanged currentFloor =" + this.currentFloor
				+ " DEST = " + this.destinationFloor);

//		if( this.destinationFloor == -1)
//		{
//			this.destinationFloor = destFloors.get(0);
//			fireFloorChangedEvent(this.destinationFloor);
//		}
		
		System.out.println("is dest reached: " + isDestReached());

		if (isDestReached()) {
			System.out.println("dest arr = " + getDestinationsString());
			fireStopForPassengersEvent(this.destinationFloor);
			this.destFloors.remove(0);
			if (destFloors.size() <= 0) {
				System.out.println("NO MORE FLOORS");
				this.destinationFloor = -1;
				return;

			}

			this.destinationFloor = destFloors.get(0);

			// fireFloorChangedEvent(this.destinationFloor);
			// fireStopForPassengersEvent(currentFloor, destinationFloor);
		}
		System.err
				.println("onFloorChanged() dests: " + getDestinationsString());

	}

	private void fireStopForPassengersEvent(int currentFloor) {
		for (ModelListener l : modelListeners) {
			l.stopForPassengers(currentFloor);
		}

	}

	private boolean isDestReached() {
		return this.currentFloor == this.destinationFloor;
	}

	public void floorChanged(int num) {
		this.currentFloor += num;

		int oldDestFloor = this.destinationFloor;
		switch (this.currentElevatorState) {
		case STOP:
			this.destinationFloor = handleStartMovement();

			break;

		case UP:
			this.destinationFloor = handleUpMovement();
			if (this.destinationFloor < 0) {
				this.currentElevatorState = elevatorMovement.STOP;
				this.destinationFloor = handleStartMovement();
			}

			break;
		case DOWN:
			this.destinationFloor = handleDownMovement();
			if (this.destinationFloor < 0) {
				this.currentElevatorState = elevatorMovement.STOP;
				this.destinationFloor = handleStartMovement();
			}
			break;
		}

		if (oldDestFloor != this.destinationFloor && this.destinationFloor > 0)
			fireFloorChangedEvent(this.destinationFloor);
	}

	private int handleDownMovement() {
		for (int i = this.currentFloor - 1; i >= 0; i--) {
			if (floorStops[i] == true) {
				this.destinationFloor = i;
				break;
			}
		}
		return -1;
	}

	private int handleUpMovement() {
		for (int i = this.currentFloor; i < floorStops.length; i++) {
			if (floorStops[i] == true) {
				return this.destinationFloor = i;
			}
		}
		return -1;
	}

	private int handleStartMovement() {
		for (int i = 0; i < floorStops.length; i++) {
			if (floorStops[i] == true) {
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

	public int getDestFloorsSize() {
		return destFloors.size();
	}

	public int getDestinationFloor() {
		return destinationFloor;
	}

	public String getDestinationsString() {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < destFloors.size(); i++) {

			builder.append(destFloors.get(i));
			if (i != destFloors.size() - 1)
				builder.append(", ");

		}

		return builder.toString();

	}

}
