package controllers;

import model.ElevatorModel;
import views.MainElevatorView;
import views.SummaryView;

public class ElevatorController implements ViewListener, ModelListener{

	
	private MainElevatorView mainView;
	private ElevatorModel elevatorModel;
	private SummaryView summaryView;
	
	public ElevatorController(MainElevatorView mainView, SummaryView summaryView, ElevatorModel elevatorModel) {
		
		this.mainView = mainView;
		this.elevatorModel = elevatorModel;
		this.summaryView = summaryView;
		this.mainView.registerListener(this);
		this.elevatorModel.registerListener(this);

	}


//	@Override
//	public void changeFloor(int newFloor) {
//		this.elevatorModel.changeFloor(newFloor);
//	}

	@Override
	public void addFloor(int floorNum) {
		elevatorModel.addFloor(floorNum);
		
	}


	@Override
	public void floorChanged(int oldFloor, int newFloor) {
		this.mainView.changeElevatorFloor(oldFloor, newFloor);
		
	}


	@Override
	public void elevatorFileChanged(String newFileName) {
		this.mainView.setElevatorImage(newFileName);
		
	}


	@Override
	public String getElevatorFileName() {
		return this.elevatorModel.getImageFile();
	}


	@Override
	public int getNumOfFloors() {
		return this.elevatorModel.getNumOfFloors();
	}


	@Override
	public void floorChanged(int num) {
		this.elevatorModel.onFloorChanged(num);
		
	}


	@Override
	public void stopForPassengers(int currentFloor) {
		mainView.stopForPassengers(currentFloor);
		
	}


	@Override
	public int getDestinationFloor() {
		return elevatorModel.getDestinationFloor();
	}


	@Override
	public int getCurrentFloor() {
		return elevatorModel.getCurrentFloor();
	}


	
	
	
	
	
	
	
	
	
	
	
}
