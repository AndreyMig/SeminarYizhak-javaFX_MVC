package controllers;

import model.ElevatorModel;
import views.MainView;

public class ElevatorController implements ViewListener, ModelListener{

	
	private MainView mainView;
	private ElevatorModel elevatorModel;
	
	
	public ElevatorController(MainView mainView, ElevatorModel elevatorModel) {
		
		this.mainView = mainView;
		this.elevatorModel = elevatorModel;
		this.mainView.registerListener(this);
		this.elevatorModel.registerListener(this);
		
		
	}


	@Override
	public void changeFloor(int newFloor) {
		this.elevatorModel.changeFloor(newFloor);
		System.err.println("xxxxxxxxx");
	}


	@Override
	public void floorChanged(int oldFloor, int newFloor) {
		this.mainView.changeElevatorFloor(oldFloor, newFloor);
		
	}

	
	
	
	
	
	
	
	
	
	
}
