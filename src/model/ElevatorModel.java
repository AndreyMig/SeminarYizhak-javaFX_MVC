package model;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import controllers.ModelListener;

public class ElevatorModel {


	public static String[] imageFileNames = {"defaultElevator.png", "elevator-bg.png"};

	
	
	private int currentFloor;
	private int destinationFloor;
	private ArrayList<ModelListener> modelListeners;
	private String imageFile = "defaultElevator.png";
	private String destFloorString = "";
	public StringBuilder floorHistory = new StringBuilder();
	public String floorHis ="";
	
	public ElevatorModel() {
		modelListeners = new ArrayList<ModelListener>();
		this.currentFloor = 1;
		this.destinationFloor = -1;
	}

		public void registerListener(ModelListener listener){
		
			modelListeners.add(listener);			
			
		}
	

	public void changeFloor(int newFloor)
	{
		
		if(newFloor - currentFloor == 0)
			return;
		
		floorHistory.append(newFloor+", ");
		
		this.destinationFloor = newFloor;
		this.destFloorString = ""+this.destinationFloor;
		fireFloorChangedEvent(this.currentFloor, newFloor);
		this.currentFloor = newFloor;
	}
	
	public void floorChanged()
	{
		this.currentFloor = destinationFloor;
	}



	private void fireFloorChangedEvent(int oldFloor, int newFloor) {
		
		for (ModelListener modelListener : modelListeners) {
			
			modelListener.floorChanged(oldFloor, newFloor);
			
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

	public String getDestFloorString() {
		return destFloorString;
	}

	public void changeElevatorImage(String file) {
		this.imageFile = file;
		
	}

	public String getFloorHis() {
		floorHis = floorHistory.toString();
		
		if(floorHis.endsWith(", "))
		{
			floorHis = floorHis.substring(0, floorHis.length()-2);
		}
		
		return floorHis;
	}



	
	
	
	
	
	
}
