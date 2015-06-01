package application;
	
import controllers.ElevatorController;
import model.ElevatorModel;
import views.ElevatorView;
import views.SummaryView;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	

	@Override
	public void start(Stage primaryStage) {

		
		SummaryView view = new SummaryView(primaryStage);
		
	}

	public static void main(String[] args) {
		
		launch(args);
		
	}
	

	public static ElevatorModel createNewElevetorPanel(SummaryView summView){
		Stage s = new Stage();
		ElevatorModel elevatorModel = new ElevatorModel();
		ElevatorView mainView = new ElevatorView(s);
		ElevatorController ec = new ElevatorController(mainView, summView, elevatorModel);
		mainView.createScene(s);
		return elevatorModel;
	}
	
}



