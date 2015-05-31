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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SummaryView {

	private static final double SCENE_WIDTH = 600;
	private static final double SCENE_HEIGHT = 400;
	private final ObservableList<ElevatorModel> data = FXCollections
			.observableArrayList();
	private ArrayList<ViewListener> listeners;
	private TableView<ElevatorModel> tableView;
	private String family = "Helvetica";
	private TextFlow textFlow;
	private ScrollPane scrollPane;

	public SummaryView(Stage stage) {

		listeners = new ArrayList<>();
		createScene(stage);

	}

	public void registerListener(ViewListener l) {
		listeners.add(l);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createScene(Stage stage) {

		Group root = new Group();
		HBox hb = new HBox(10);
		VBox leftArea = new VBox();
		leftArea.setStyle("-fx-border-color: black;");
		hb.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		stage.setScene(scene);

		// set button layout
		Button createButton = new Button("Create");
		createButton.prefWidthProperty().bind(stage.widthProperty().divide(10));

		TableColumn modelIdCol = new TableColumn();
		modelIdCol.setText("Model id");
		modelIdCol.setCellValueFactory(new PropertyValueFactory("modelId"));

		TableColumn currentFloor = new TableColumn();
		currentFloor.setText("Current floor");
		currentFloor.setCellValueFactory(new PropertyValueFactory(
				"currentFloor"));

		// TableColumn floorHistory = new TableColumn();
		// floorHistory.setText("Floor stops");
		// floorHistory.setCellValueFactory(new
		// PropertyValueFactory("floorHis"));

		TableColumn<ElevatorModel, String> comboBoxCol = new TableColumn<ElevatorModel, String>();

		comboBoxCol.setText("Elevator image");

		comboBoxCol.setMinWidth(50);

		ArrayList<String> fileNames = new ArrayList<String>(
				Arrays.asList(ElevatorModel.imageFileNames));

		comboBoxCol
				.setCellValueFactory(new PropertyValueFactory<ElevatorModel, String>(
						"imageFile"));

		comboBoxCol
				.setCellFactory(new Callback<TableColumn<ElevatorModel, String>, TableCell<ElevatorModel, String>>() {
					@Override
					public TableCell<ElevatorModel, String> call(
							TableColumn<ElevatorModel, String> arg0) {
						return new ComboBoxCell<>(fileNames, SummaryView.this);
					}

				});

		createButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ElevatorModel em = Main
						.createNewElevetorPanel(SummaryView.this);
				data.add(em);
			}
		});

		tableView = new TableView<ElevatorModel>();
		tableView.prefWidthProperty().bind(stage.widthProperty().divide(1.2));
		tableView.prefHeightProperty().bind(stage.heightProperty().divide(1.8));
		// tableView.setMaxSize(Double.MAX_VALUE, );
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setItems(data);

		tableView.getColumns().addAll(modelIdCol, currentFloor, comboBoxCol);
		scrollPane = new ScrollPane();
		textFlow = new TextFlow();
		textFlow.setPadding(new Insets(20, 20, 20, 20));
		scrollPane.prefHeightProperty().bind(stage.heightProperty().divide(2.65));
		scrollPane.prefWidthProperty().bind(stage.widthProperty().divide(1.2));
		scrollPane.setContent(textFlow);
		leftArea.getChildren().addAll(tableView, scrollPane);

		hb.getChildren().add(leftArea);
		hb.getChildren().add(createButton);
		root.getChildren().add(hb);
		// root.getChildren().add(createButton);W

		stage.show();

	}

	public synchronized void logToGui(String msg, Color c) {
		Text text = new Text(msg);
		text.setFill(c);
		text.setFont(Font.font(family, 20));
		textFlow.getChildren().add(text);
		scrollPane.setVvalue(scrollPane.getHmax());

	}

	public void dataChanged() {
		tableView.getColumns().get(0).setVisible(false);
		tableView.getColumns().get(0).setVisible(true);
	}

	@SuppressWarnings("rawtypes")
	public void fireChangeElevatorImageEvent(String file, ComboBoxCell cell) {

		TableRow row = (TableRow) cell.getParent();

		data.get(row.getIndex()).changeElevatorImage(file);

	}

	@SuppressWarnings("rawtypes")
	public String getCurrentImageFileName(ComboBoxCell cell) {
		TableRow row = (TableRow) cell.getParent();
		if (row != null)
			return data.get(row.getIndex()).getImageFile();
		return null;
	}

	public void elevatorViewClosing(ElevatorModel m) {
		data.remove(m);
	}

}
