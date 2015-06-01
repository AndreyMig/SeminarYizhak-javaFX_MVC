package controllers;

import javafx.scene.paint.Color;
import views.ElevatorView;
import model.ElevatorModel;

public interface ViewListener {

	
	
	public boolean changeFloor(int floorNum);
	
	public String getElevatorFileName();

	public ElevatorModel getModel();

	public void changeElevatorImage(String file);
	
	public void elevatorViewClosing();

	public int getCurrentFloor();

	public boolean updateCurrentFloor(int newFloor);

	public int getNextFloor(int upDown, int currentFloor);

	public String getModelId();

	void logToGui(String msg, Color c);

	public void updateFloorStop(int currentFloor);
	
}
