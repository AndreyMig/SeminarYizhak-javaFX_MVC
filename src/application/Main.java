package application;
	
import java.io.File;
import java.io.FileInputStream;

import controllers.ElevatorController;
import model.ElevatorModel;
import views.ElevatorView;
import views.MainView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	
	
	
	@Override
	public void start(Stage primaryStage) {
		
		MainView mainView = new MainView(primaryStage);
		ElevatorModel elevatorModel = new ElevatorModel();
		ElevatorController ec = new ElevatorController(mainView, elevatorModel);
		
	}

	public static void main(String[] args) {
		
		launch(args);
		
		
		
	}
}
