package views;

import java.util.ArrayList;
import java.util.Arrays;

import model.ElevatorModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

	
	
	public SummaryView(Stage stage){
		
		
		createScene(stage);
		
		
	}
	
	
	
	
	private void createScene(Stage stage){


		Group root = new Group();
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		stage.setScene(scene);
		
		//set button layout
		Button createButton = new Button("Create");
		createButton.prefWidthProperty().bind(stage.widthProperty().divide(10));		
		
		
		
        final ObservableList<ElevatorModel> data = FXCollections.observableArrayList();

        
        TableColumn currentFloorCol = new TableColumn();
        currentFloorCol.setText("Current floor");
        currentFloorCol.setCellValueFactory(new PropertyValueFactory("currentFloor"));
        
        TableColumn destFloor = new TableColumn();
        destFloor.setText("Destinaition floor");
        destFloor.setCellValueFactory(new PropertyValueFactory("currentFloor"));
        
        TableColumn<ElevatorModel, String> comboBoxCol = new TableColumn<ElevatorModel, String>();

        comboBoxCol.setText("curFloor");

        comboBoxCol.setMinWidth(50);

        ArrayList<String> fileNames = new ArrayList<String>(Arrays.asList(ElevatorModel.imageFileNames));

        comboBoxCol.setCellValueFactory(new PropertyValueFactory<ElevatorModel,String>("curFloor"));

        comboBoxCol.setCellFactory(new Callback<TableColumn<ElevatorModel, String>, TableCell<ElevatorModel, String>>() {

			@Override
			public TableCell<ElevatorModel, String> call(
					TableColumn<ElevatorModel, String> arg0) {


				return new ComboBoxCell<>(fileNames);
				
				
			}

		
 

        });
	
		
        TableView<ElevatorModel> tableView = new TableView<ElevatorModel>();
        tableView.prefWidthProperty().bind(stage.widthProperty().divide(1.2));
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
        hb.getChildren().add(createButton);
        root.getChildren().add(hb); 
//        root.getChildren().add(createButton); 
		
		stage.show();

	}

	
	
	
	
	
	
}
