package controllers;

public interface ViewListener {

	
	
//	public void changeFloor(int floorNum);
	
	public void addFloor(int floorNum);
	
	public String getElevatorFileName();
	
	public int getNumOfFloors();
	
	public void floorChanged(int floorNum);
	
	public int getDestinationFloor();
	
	public int getCurrentFloor();
	
}
