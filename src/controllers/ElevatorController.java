package controllers;

import javafx.scene.paint.Color;
import model.ElevatorModel;
import views.ElevatorView;
import views.SummaryView;

public class ElevatorController implements ViewListener, ModelListener{

	
	private ElevatorView elevatorView;
	private ElevatorModel elevatorModel;
	private SummaryView summaryView;
	
	public ElevatorController(ElevatorView mainView, SummaryView summaryView, ElevatorModel elevatorModel) {
		
		this.elevatorView = mainView;
		this.elevatorModel = elevatorModel;
		this.summaryView = summaryView;
		this.elevatorView.registerListener(this);
		this.elevatorModel.registerListener(this);
		this.summaryView.registerListener(this);
		
		
	}


	@Override
	public boolean changeFloor(int newFloor) {
		return this.elevatorModel.addFloor(newFloor);
	}


	@Override
	public void elevatorFileChanged(String newFileName) {
		this.elevatorView.changeElevatorImage(newFileName);
		
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
		System.out.println(file);
		this.elevatorModel.changeElevatorImage(file);
		this.elevatorView.changeElevatorImage(file);
	}


	@Override
	public void elevatorViewClosing() {
		this.summaryView.elevatorViewClosing(this.elevatorModel);
		
	}


	@Override
	public int getCurrentFloor() {
		return elevatorModel.getCurrentFloor();
	}


	@Override
	public boolean updateCurrentFloor(int newFloor) {
		boolean val = elevatorModel.updateCurrentFloor(newFloor);
		this.summaryView.dataChanged();
		return val;
	}


	@Override
	public void startElevatorMove(int floor) {
		elevatorView.startElevatorMove(floor);
	}


	@Override
	public int getNextFloor(int upDown, int currentFloor) {
		return elevatorModel.getNextFloor(upDown, currentFloor);
	}


	@Override
	public String getModelId() {
		return elevatorModel.getModelId();
	}


	@Override
	public void logToGui(String msg, Color c) {
		this.summaryView.logToGui(msg, c);
	}


	@Override
	public void updateFloorStop(int currentFloor) {
		this.elevatorModel.updateFloorStop(currentFloor);
	}


	
	
}
