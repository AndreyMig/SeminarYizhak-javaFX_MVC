package controllers;

import model.ElevatorModel;
import views.MainView;
import views.SummaryView;

public class ElevatorController implements ViewListener, ModelListener{

	
	private MainView mainView;
	private ElevatorModel elevatorModel;
	private SummaryView summaryView;
	
	public ElevatorController(MainView mainView, SummaryView summaryView, ElevatorModel elevatorModel) {
		
		this.mainView = mainView;
		this.elevatorModel = elevatorModel;
		this.summaryView = summaryView;
		this.mainView.registerListener(this);
		this.elevatorModel.registerListener(this);
		this.summaryView.registerListener(this);
		
		
	}


	@Override
	public void changeFloor(int newFloor) {
		this.elevatorModel.changeFloor(newFloor);
	}


	@Override
	public void floorChanged(int oldFloor, int newFloor) {
		this.mainView.changeElevatorFloor(oldFloor, newFloor);
		this.summaryView.dataChanged();
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
	public ElevatorModel getModel() {
		return elevatorModel;
	}


	@Override
	public void changeElevatorImage(String file) {
		this.elevatorModel.changeElevatorImage(file);
		this.mainView.changeElevatorImage(file);
	}


	@Override
	public void elevatorViewClosing() {
		this.summaryView.elevatorViewClosing(this.elevatorModel);
		
	}

	
	
	
	
	
	
	
	
	
	
}
