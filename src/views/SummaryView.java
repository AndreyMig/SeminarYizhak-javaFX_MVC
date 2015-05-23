package views;

import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import controllers.ViewListener;
import model.ElevatorModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SummaryView {

	private static final double SCENE_WIDTH = 600;
	private static final double SCENE_HEIGHT = 400;
	private final ObservableList<ElevatorModel> data = FXCollections
			.observableArrayList();
	private ArrayList<ViewListener> listeners;
	private TableView<ElevatorModel> tableView;

	public SummaryView(Stage stage) {

		listeners = new ArrayList<>();
		createScene(stage);

	}

	public void registerListener(ViewListener l) {
		listeners.add(l);
	}

	private void createScene(Stage stage) {

		Group root = new Group();
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		stage.setScene(scene);

		// set button layout
		Button createButton = new Button("Create");
		createButton.prefWidthProperty().bind(stage.widthProperty().divide(10));

		createButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ElevatorModel em = Main
						.createNewElevetorPanel(SummaryView.this);
				data.add(em);

			}
		});

		TableColumn currentFloorCol = new TableColumn();
		currentFloorCol.setText("Destination floor");
		currentFloorCol.setCellValueFactory(new PropertyValueFactory(
				"destFloorString"));

		TableColumn destFloor = new TableColumn();
		destFloor.setText("Destinaition floor");
		destFloor.setCellValueFactory(new PropertyValueFactory("currentFloor"));

		TableColumn<ElevatorModel, String> comboBoxCol = new TableColumn<ElevatorModel, String>();

		comboBoxCol.setText("curFloor");

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
						return new ComboBoxCell<>(fileNames, SummaryView.this);

					}

				});

		tableView = new TableView<ElevatorModel>();
		tableView.prefWidthProperty().bind(stage.widthProperty().divide(1.2));
		tableView.prefHeightProperty().bind(stage.heightProperty());
		tableView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setItems(data);

		// ElevatorModel em = getModelFromController();
		// data.add(em);

		tableView.getColumns().addAll(comboBoxCol, currentFloorCol);
		hb.getChildren().add(tableView);
		hb.getChildren().add(createButton);
		root.getChildren().add(hb);
		// root.getChildren().add(createButton);

		stage.show();

	}

	public void dataChanged() {
		tableView.getColumns().get(0).setVisible(false);
		tableView.getColumns().get(0).setVisible(true);
	}

	public void fireChangeElevatorImageEvent(String file) {
		for(ViewListener l : listeners)
		{
			l.changeElevatorImage(file);
		}
	}

}
