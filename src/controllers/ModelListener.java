package controllers;

import javafx.scene.paint.Color;

public interface ModelListener {

	
	
	 
	
	public void elevatorFileChanged(String newFileName);

	public void startElevatorMove(int floor);

	public void logToGui(String msg, Color c);
	
	
	
	
}
