package model;

import java.util.ArrayList;

import controllers.ModelListener;

public class ElevatorModel {



//	ArrayList<Integer> floors = new ArrayList<Integer>();
	
	
	
	private int currentFloor;
	private int newFloor;
	ArrayList<ModelListener> modelListeners = new ArrayList<ModelListener>();
	
	
	
	
	public ElevatorModel() {
		this.currentFloor = 1;
		this.newFloor = -1;
	}

		public void registerListener(ModelListener listener){
		
			modelListeners.add(listener);			
			
		}
	

	public void changeFloor(int newFloor)
	{
		
		if(newFloor - currentFloor == 0)
			return;
		
		this.newFloor = newFloor;
		
		fireFloorChangedEvent(this.currentFloor, newFloor);
		this.currentFloor = newFloor;
	}
	
	public void floorChanged()
	{
		this.currentFloor = newFloor;
	}



	private void fireFloorChangedEvent(int oldFloor, int newFloor) {
		
		for (ModelListener modelListener : modelListeners) {
			
			modelListener.floorChanged(oldFloor, newFloor);
			
		}
		
		
	}



	
	
	
	
	
	
	
}
