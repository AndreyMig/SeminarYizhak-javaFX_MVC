package application;
	
import controllers.ElevatorController;
import model.ElevatorModel;
import views.MainView;
import views.SummaryView;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	
	
	private static final String FXML_FILE_NAME = "controlPanel.fxml";
	


	@Override
	public void start(Stage primaryStage) {

		
		SummaryView view = new SummaryView(primaryStage);
		
//		createNewElevetorPanel(view);
		
	}

	public static void main(String[] args) {
		
		launch(args);
		
	}
	

	public static ElevatorModel createNewElevetorPanel(SummaryView summView){
		Stage s = new Stage();
		ElevatorModel elevatorModel = new ElevatorModel();
		MainView mainView = new MainView(s);
		ElevatorController ec = new ElevatorController(mainView, summView, elevatorModel);
		mainView.createScene(s);
		return elevatorModel;
	}
	
}



