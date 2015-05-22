package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controllers.ElevatorController;
import model.ElevatorModel;
import views.ComboBoxCell;
import views.MainElevatorView;
import views.SummaryView;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class Main extends Application {

	private static final String FXML_FILE_NAME = "controlPanel.fxml";

	@Override
	public void start(Stage primaryStage) {

		SummaryView view = new SummaryView(primaryStage);

		createNewElevetorPanel(view, -1);

	}

	public static void main(String[] args) {

		launch(args);

	}

	public static void createNewElevetorPanel(SummaryView view, int numOfFloors) {
		Stage s = new Stage();
		ElevatorModel elevatorModel = null;
		if (numOfFloors > 0)
			elevatorModel = new ElevatorModel();
		else
			elevatorModel = new ElevatorModel(numOfFloors);
		MainElevatorView mainElevatorView = new MainElevatorView(s);
		ElevatorController ec = new ElevatorController(mainElevatorView, view,
				elevatorModel);
		mainElevatorView.createScene(s);
	}

}
