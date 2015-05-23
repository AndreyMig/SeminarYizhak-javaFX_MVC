package controllers;

import model.ElevatorModel;

public interface ViewListener {

	
	
	public void changeFloor(int floorNum);
	
	public String getElevatorFileName();

	public ElevatorModel getModel();

	public void changeElevatorImage(String file);
	
	
}
