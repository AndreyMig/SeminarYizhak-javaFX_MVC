package controllers;

public interface ModelListener {

	
	
	 
	public void floorChanged(int oldFloor, int newFloor);
	
	public void elevatorFileChanged(String newFileName);

	public void startElevatorMove(int floor);
	
	
	
	
}
