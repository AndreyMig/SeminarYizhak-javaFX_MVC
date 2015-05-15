package views;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import application.Main;
import controllers.ViewListener;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainView2 {

	
	
	public static Scene scene;
	private ArrayList<ViewListener> listeners;
	
	private final String BUILDING_IMAGE = "building.png";
	private final String ELEVATOR_INTERIOR_IMAGE = "elevator-bg.png";
	
	private static final Image ICON_48 = new Image(Main.class.getResourceAsStream("elevator-bg.png"));
	//private static final String buildingImage = MainView.class.getResource("images//building.png").toString();
	
	public MainView2(Stage stage)
	{
		
		listeners = new ArrayList<ViewListener>();
		createScene( stage);
		
		
		
		
		
	}
	
	private Rectangle lastOne;
	private void createScene(Stage stage)
	{
		 Pane root = new Pane();

		    int grid_x = 7; //number of rows
		    int grid_y = 7; //number of columns

		    // this binding will find out which parameter is smaller: height or width
		    NumberBinding rectsAreaSize = Bindings.min(root.heightProperty(), root.widthProperty());

		    for (int x = 0; x < grid_x; x++) {
		        for (int y = 0; y < grid_y; y++) {
		            Rectangle rectangle = new Rectangle();
		            rectangle.setStroke(Color.WHITE);

		            rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
		                @Override
		                public void handle(MouseEvent t) {
		                    if (lastOne != null) {
		                        lastOne.setFill(Color.BLACK);
		                    }
		                    // remembering clicks
		                    lastOne = (Rectangle) t.getSource();
		                    // updating fill
		                    lastOne.setFill(Color.RED);
//		                    lastOne.setLayoutX(rectangle.getX()+10);
//		                    System.out.println(rectangle.getX());
		                }
		            });

		            // here we position rects (this depends on pane size as well)
		            rectangle.xProperty().bind(rectsAreaSize.multiply(x).divide(grid_x));
		            rectangle.yProperty().bind(rectsAreaSize.multiply(y).divide(grid_y));

		            // here we bind rectangle size to pane size 
		            rectangle.heightProperty().bind(rectsAreaSize.divide(grid_x));
		            rectangle.widthProperty().bind(rectangle.heightProperty());

		            root.getChildren().add(rectangle);
		        }
		    }

		    stage.setScene(new Scene(root, 500, 500));
		    stage.show();
	}
	
	
	
//	
//	
//	private void createScene(Stage stage){
//		try {
//			// BorderPane root = new BorderPane();
//
////			FXMLLoader fxmlRoot = new FXMLLoader();
////			Group root = new Group();
////			StackPane stackPane = new StackPane();
////			scene = new Scene(root, 500, 700);
////
////			 SplitPane splitPane = new SplitPane();
////		     splitPane.prefWidthProperty().bind(scene.widthProperty());
////		     splitPane.prefHeightProperty().bind(scene.heightProperty());
////		     splitPane.setOrientation(Orientation.HORIZONTAL);
////	        Rectangle rectangle = new Rectangle(280, 70, Color.BISQUE);
////
////	        rectangle.setStroke(Color.BLACK);
////
//		     ImageView imageView = new ImageView(ELEVATOR_INTERIOR_IMAGE);
////
////	        Label label = new Label("Your name could be here.", imageView);
////
////	        label.setContentDisplay(ContentDisplay.RIGHT);
////
////	 
////
////	        stackPane.getChildren().addAll(rectangle, label);
////
////	 
////
////	        root.getChildren().add(stackPane);
//			
//			
//		//	ImageView buildingImageView = loadImageView(BUILDING_IMAGE, stage);
//
//	//		System.out.println(buildingImageView.getX());
//	//		
//			//Load building image
////			Image buildingImage = new Image(BUILDING_IMAGE);
////			ImageView buildingImageView = new ImageView(buildingImage);
//	//		buildingImageView.setLayoutX(150);
//			
//			//Load elevator image
//		//	Image img = new Image(ELEVATOR_INTERIOR_IMAGE);
//	//		ImageView elevatorImageView = new ImageView(img);
//	//		elevatorImageView.setLayoutX(150);
//////			
////			elevatorImageView.fitWidthProperty().bind(); 
////			elevatorImageView.fitHeightProperty().bind(buildingImageView.getImage().heightProperty());
////			
//////			elevatorImageView.layoutXProperty().bind(buildingImageView.layoutXProperty());
//////			elevatorImageView.layoutYProperty().bind(buildingImageView.layoutYProperty());
////
////			//elevatorImageView.fitWidthProperty().bind(stage.widthProperty()); 
////			//elevatorImageView.fitHeightProperty().bind(stage.heightProperty());
////			
////			//ImageView elevatorImageView = loadImageView(ELEVATOR_INTERIOR_IMAGE, stage);
////					
////					
////					
////					
//	//		stackPane.getChildren().add(buildingImageView);
//	//		stackPane.getChildren().add(elevatorImageView);
//	//		root.getChildren().add(stackPane);
////			root.getChildren().add(elevatorImageView);
////			
////			System.out.println(buildingImageView.getX());
////			
//			
//			stage.setScene(scene);
//			stage.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	public void registerListener(ViewListener listener)
	{
		listeners.add(listener);
	}
	
	
	public void changeElevatorFloor(int floorNum)
	{
		
	}
	
	
	
	private void fireUpChangeFloorEvent(int floorNum)
	{
		
		for (ViewListener l : listeners) {
			l.changeFloor(floorNum);
		}
		
	}
	
	private ImageView loadImageView(String imageName, Stage stage)
	{
		
		Image img = new Image(imageName);

		ImageView iv = new ImageView(img);
		iv.setLayoutX(stage.getWidth());
		iv.fitWidthProperty().bind(stage.widthProperty()); 
		iv.fitHeightProperty().bind(stage.heightProperty());
		
		
		
		return iv;
	}
	
//	private  ObservableValue<? extends Number> test(Stage stage)
//	{
//		ObservableValue<? extends Number> o = new ObservableValue<Number>() {
//
//			@Override
//			public void addListener(InvalidationListener arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void removeListener(InvalidationListener arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void addListener(ChangeListener<? super Number> listener) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public Number getValue() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public void removeListener(ChangeListener<? super Number> listener) {
//				// TODO Auto-generated method stub
//				
//			}
//		};
//		
//		stage.widthProperty() ;
//	}
	
}
