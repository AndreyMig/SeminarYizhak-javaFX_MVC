package views;

import java.util.ArrayList;
import java.util.Arrays;

import model.ElevatorModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class SummaryView {

	private static final double SCENE_WIDTH = 600;
	private static final double SCENE_HEIGHT = 400;
	private static final String POSTIVE_INTEGER_REGEX = "^[1-9]\\d*$";
	private static final int MAX_NUM_FLOORS = 20;

	private enum TextFieldStatus {
		NOT_POS_INT, BIGGER_THEN_MAX, ACCEPTABLE_NUM_OF_FLOORS
	}

	private TextField numOfFloorsTextField;

	public SummaryView(Stage stage) {

		createScene(stage);

	}

	private void createScene(Stage stage) {

		Group root = new Group();
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		stage.setScene(scene);

		VBox vb = new VBox();

		// set button layout
		Button createButton = new Button("Create");
		createButton.prefWidthProperty().bind(stage.widthProperty().divide(6));
		setCreateButtonListener(createButton);
		// set label
		Label l = new Label("Number of floors");

		// set field text
		numOfFloorsTextField = new TextField();
		numOfFloorsTextField.prefWidthProperty().bind(
				stage.widthProperty().divide(6));
		// setTextFieldListener(tf);

		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(l, numOfFloorsTextField, createButton);

		final ObservableList<ElevatorModel> data = FXCollections
				.observableArrayList();

		checkTextField();
		TableColumn currentFloorCol = new TableColumn();
		currentFloorCol.setText("Current floor");
		currentFloorCol.setCellValueFactory(new PropertyValueFactory(
				"currentFloor"));

		TableColumn destFloor = new TableColumn();
		destFloor.setText("Destinaition floor");
		destFloor.setCellValueFactory(new PropertyValueFactory("currentFloor"));

		TableColumn<ElevatorModel, String> comboBoxCol = new TableColumn<ElevatorModel, String>();

		comboBoxCol.setText("Elevator style");

		comboBoxCol.setMinWidth(50);
		

		ArrayList<String> fileNames = new ArrayList<String>(
				Arrays.asList(ElevatorModel.imageFileNames));

		comboBoxCol
				.setCellValueFactory(new PropertyValueFactory<ElevatorModel, String>(
						"curFloor"));

		comboBoxCol
				.setCellFactory(new Callback<TableColumn<ElevatorModel, String>, TableCell<ElevatorModel, String>>() {

					@Override
					public TableCell<ElevatorModel, String> call(
							TableColumn<ElevatorModel, String> arg0) {

						return new ComboBoxCell<>(fileNames);

					}

				});
		
		
		TableColumn elevatorPath = new TableColumn();
		currentFloorCol.setText("Elevator path");
		currentFloorCol.setCellValueFactory(new PropertyValueFactory(
				"currentFloor"));
		

		TableView<ElevatorModel> tableView = new TableView<ElevatorModel>();
		tableView.prefWidthProperty().bind(stage.widthProperty().divide(1.4));
		tableView.prefHeightProperty().bind(stage.heightProperty());
		tableView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setItems(data);
		data.add(new ElevatorModel());
		data.add(new ElevatorModel());
		data.add(new ElevatorModel());
		data.add(new ElevatorModel());
		data.add(new ElevatorModel());

		tableView.getColumns().addAll(comboBoxCol, currentFloorCol);
		hb.getChildren().add(tableView);
		hb.getChildren().add(vb);
		root.getChildren().add(hb);
		// root.getChildren().add(createButton);
		createDialogWithText("Only integers smaller then " + MAX_NUM_FLOORS + " Please");
		stage.show();

	}

	private void setCreateButtonListener(Button createButton) {
		createButton.addEventHandler(ActionEvent.ACTION,
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						switch (checkTextField()) {
						case BIGGER_THEN_MAX:
						case NOT_POS_INT:

							createDialogWithText("Only integers smaller then " + MAX_NUM_FLOORS + " Please");
							
							break;

						case ACCEPTABLE_NUM_OF_FLOORS:
									
							break;
						}
					}
				});

	}

	private TextFieldStatus checkTextField() {

		String numOfFloors = numOfFloorsTextField.getText();

		boolean isPosInt = numOfFloors.matches(POSTIVE_INTEGER_REGEX);

		if (!isPosInt)
			return TextFieldStatus.NOT_POS_INT;

		int numOfFloorsInt = Integer.parseInt(numOfFloors);

		if (numOfFloorsInt > 20)
			return TextFieldStatus.BIGGER_THEN_MAX;

		return TextFieldStatus.ACCEPTABLE_NUM_OF_FLOORS;
	}

	public void createDialogWithText(String text) {
		Stage dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);
		
		VBox v = new  VBox();
		v.setAlignment(Pos.CENTER);
		v.getChildren().add(new Label(text));
		Scene s = new Scene(v, 250,150);
		dialog.setScene(s);
		dialog.show();
	}


}
