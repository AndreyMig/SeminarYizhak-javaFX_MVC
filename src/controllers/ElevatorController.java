package controllers;

import model.ElevatorModel;
import views.MainView;

public class ElevatorController implements ViewListener, ModelListener{

	
	private MainView mainView;
	private ElevatorModel elevatorModel;
	
	
	public ElevatorController(MainView mainView, ElevatorModel elevatorModel) {
		
		this.mainView = mainView;
		this.elevatorModel = elevatorModel;
		
		
	}


	@Override
	public void changeFloor(int floorNum) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void floorChanged(int floorNum) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
