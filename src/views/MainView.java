package views;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.swing.Timer;

import application.Main;
import controllers.ViewListener;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainView {

	
	
	public static Scene scene;
	private ArrayList<ViewListener> listeners;
	
	private final String BUILDING_IMAGE = "building2.png";
//	private final String ELEVATOR_INTERIOR_IMAGE = "elev2.png";
	private final String ELEVATOR_INTERIOR_IMAGE = "elevator-bg.png";
	
	private static final Image ICON_48 = new Image(Main.class.getResourceAsStream("elevator-bg.png"));
	//private static final String buildingImage = MainView.class.getResource("images//building.png").toString();
	
	public MainView(Stage stage)
	{
		
		listeners = new ArrayList<ViewListener>();
		createScene( stage);
		
		
		
		
		
	}

	private Rectangle lastOne;
	private void createScenxxx(Stage stage)
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
		                    
		                    
		                  
		               
		                    System.out.println(rectangle.getX());
		                }
		            });

		           
		            
		           
		            
		            
		            // here we position rects (this depends on pane size as well)
		            rectangle.xProperty().bind(rectsAreaSize.multiply(x).divide(grid_x));
		            rectangle.yProperty().bind(rectsAreaSize.multiply(y).divide(grid_y));

		            // here we bind rectangle size to pane size 
		            rectangle.heightProperty().bind(rectsAreaSize.divide(grid_x));
		            rectangle.widthProperty().bind(rectangle.heightProperty());

		            root.getChildren().add(rectangle);
		            
		            if(x==grid_x-1 && y==grid_y-1)
		            	lastOne = rectangle;
		            
		        }
		        
		        
		        
		        
		        
		    }

		    lastOne.setFill(Color.RED);
		    EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {

	            public void handle(ActionEvent t) {

	            	lastOne.setLayoutX(lastOne.getX()+1);
	            }

	        };
	        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(200), onFinished));
            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();
		    
		    
		    stage.setScene(new Scene(root, 500, 500));
		    stage.show();
	}
	
	double x = 0;
	double divis = 2.4; 
	private void createScene(Stage stage){
		try {
			// BorderPane root = new BorderPane();

			FXMLLoader fxmlRoot = new FXMLLoader();

			Parent root = (Parent) fxmlRoot.load(new FileInputStream(new File(
					"another.fxml")));

		
			Scene scene = new Scene(root, 600, 400);
			

//			AnchorPane p =(AnchorPane) scene.lookup("#anchorPane");
//			
//			Pane pp = (Pane) scene.lookup("#wrapperPain");
//			pp.h
//			p.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//			pp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

			
			
			GridPane pane = (GridPane) scene.lookup("#mainGrid");
			Pane p = (Pane) scene.lookup("#elevatorPane");
//			VBox v = (VBox) scene.lookup("#aVbox");
//			HBox h = (HBox) scene.lookup("#aHbox");
			
			
			for (int i = 0; i < 8; i++) {
				
//				Rectangle r= new Rectangle();
//				r.widthProperty().bind(p.widthProperty());
//				r.heightProperty().bind(p.heightProperty());
//				Color c = i%2==0 ? Color.BLACK : Color.WHITESMOKE;   
//				r.setFill(c);
//				pane.add(r, 1, i);
				
				ToggleButton t = new ToggleButton("F"+i);
				t.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				ToggleButton t2 = new ToggleButton("F"+i);
				t2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				pane.add(t, 0, i);
				pane.add(t2, 2, i);
			}
			Image elevatorImage = new Image(ELEVATOR_INTERIOR_IMAGE);
			ImageView elevatorImageView = new ImageView(elevatorImage);
			GridPane.setHalignment(elevatorImageView, HPos.CENTER);
			elevatorImageView.fitWidthProperty().bind(p.widthProperty().divide(10));
			elevatorImageView.fitHeightProperty().bind(p.heightProperty().divide(5));
			
			System.out.println(p.widthProperty().doubleValue());
			
			stage.setScene(scene);
			stage.show();
			

			elevatorImageView.xProperty().bind(p.widthProperty().divide(5));
			elevatorImageView.setLayoutY(p.getHeight() - 100);
//			elevatorImageView.yProperty().bind(p.heightProperty().divide(5));
			
//			pane.add(elevatorImageView, 1, 7);
			p.getChildren().add(elevatorImageView);
			
//			
//			Timer t= new Timer(10, new ActionListener() {
//				
//				@Override
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					elevatorImageView.yProperty()
//					x += 0.5;
////					elevatorImageView.yProperty().bind(p.heightProperty().divide(5).add(x));
////					elevatorImageView.setLayoutX(x);
//					
//				}
//			});
//			t.start();
//			 
			
//			final Timeline leftDoorTimeline = new Timeline();
//			leftDoorTimeline.setCycleCount(1);
//			leftDoorTimeline.setAutoReverse(true);
//			final KeyValue leftDoorKv = new KeyValue(elevatorImageView.yProperty(), -200);
//			final KeyFrame leftDoorKf = new KeyFrame(Duration.millis(5000), leftDoorKv);
//			leftDoorTimeline.getKeyFrames().add(leftDoorKf);
//			
//			leftDoorTimeline.play();
//		elevatorImageView.fitHeightProperty().bind(buildingImageView.fitHeightProperty().divide(14));
			

			
//			Group root = new Group();
//			Pane root = new Pane();
//			GridPane gridPane = new GridPane();
//			scene = new Scene(gridPane, 700, 1000);
			
//
//			 SplitPane splitPane = new SplitPane();
//		     splitPane.prefWidthProperty().bind(scene.widthProperty());
//		     splitPane.prefHeightProperty().bind(scene.heightProperty());
//		     splitPane.setOrientation(Orientation.HORIZONTAL);
//	        Rectangle rectangle = new Rectangle(280, 70, Color.BISQUE);
//
//	        rectangle.setStroke(Color.BLACK);
//
		     ImageView imageView = new ImageView(ELEVATOR_INTERIOR_IMAGE);
		     
		     
//		     
//		     
//		     for (int i = 0; i < 8; i++) {
//		    	 
//		    	 ToggleButton toggle = new ToggleButton();
//		    	 leftGrid.add(toggle, 0, i);
//		    	 toggle.setSelected(true);
//				
//			}
//		     
//		     
//		     Text playerTitle = new Text("Player"); playerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
//		     leftGrid.add(playerTitle, 0, 0);
////		     leftGrid.setVgap(10);
//		     
//		     root.getChildren().add(leftGrid);
		     
		     
		     
		     
		     
//
//	        Label label = new Label("Your name could be here.", imageView);
//
//	        label.setContentDisplay(ContentDisplay.RIGHT);
//
//	 
//
//	        stackPane.getChildren().addAll(rectangle, label);
//
//	 
//
//	        root.getChildren().add(stackPane);
			
			
		//	ImageView buildingImageView = loadImageView(BUILDING_IMAGE, stage);

	//		System.out.println(buildingImageView.getX());
	//		
			//Load building image
//			Image buildingImage = new Image(BUILDING_IMAGE);
//			ImageView buildingImageView = new ImageView(buildingImage);
//			buildingImageView.setLayoutX(10);
//			buildingImageView.fitWidthProperty().bind(stage.widthProperty().divide(3)); 
//			buildingImageView.fitHeightProperty().bind(stage.heightProperty().divide(1));
//			System.out.println(buildingImage.heightProperty());
//			//Load elevator image
//		     
//			Image elevatorImage = new Image(ELEVATOR_INTERIOR_IMAGE);
//			ImageView elevatorImageView = new ImageView(elevatorImage);
//			NumberBinding rectsAreaSize = Bindings.min(root.heightProperty(), root.widthProperty());
//
//			System.out.println(scene.getWidth()/2);
//			System.out.println(buildingImage.widthProperty());
////			elevatorImageView.setLayoutX((buildingImage.widthProperty().getValue()/23));
////			elevatorImageView.setLayoutY((buildingImage.heightProperty().getValue()/1.32));
////			elevatorImageView.fitWidthProperty().bind(buildingImageView.fitWidthProperty().multiply(2).divide(3)); 
////			elevatorImageView.fitHeightProperty().bind(buildingImageView.fitHeightProperty().divide(14));
//			
//			
//			Rectangle r = new Rectangle();
//			r.setWidth(100);
//			r.setHeight(100);
//			
//			
//			r.widthProperty().bind(buildingImageView.fitWidthProperty().divide(5)); 
//			r.heightProperty().bind(buildingImageView.fitHeightProperty().divide(1));
//			stage.show();
//			elevatorImageView.setLayoutX(buildingImageView.fitWidthProperty().divide(6.5).getValue());
//			elevatorImageView.setLayoutY(buildingImageView.fitHeightProperty().divide(1.32).getValue());
//			
//			System.err.println(buildingImageView.fitHeightProperty().divide(14).getValue());
//			elevatorImageView.setLayoutX(0);
//			elevatorImageView.setLayoutY(buildingImageView.fitHeightProperty().divide(1.15).getValue());
//			elevatorImageView.setX(-100);
			
			
			
//			r.xProperty().bind(buildingImageView.fitWidthProperty().multiply(0.1));
//			r.yProperty().bind(buildingImageView.fitHeightProperty().divide(1.15));
//			elevatorImageView.xProperty().bind(buildingImageView.fitWidthProperty());
//			elevatorImageView.yProperty().bind(buildingImageView.fitHeightProperty().divide(1.15));
//			
//			buildingImageView.fitWidthProperty().addListener(new ChangeListener<Number>() {
//			    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
////			        System.out.println("Width: " + newSceneWidth);
////			        elevatorImageView.xProperty().unbind();
//			    	System.out.println("rect x: " + r.widthProperty().getValue());
//			    	System.out.println("building Width: " + buildingImageView.fitWidthProperty().getValue());
//			    	
////					
////			        elevatorImageView.setLayoutX(buildingImageView.fitWidthProperty().divide(6.5).getValue());
////					elevatorImageView.setLayoutY(buildingImageView.fitHeightProperty().divide(1.32).getValue());
////			        
////			        double diff = Math.abs(newSceneWidth.doubleValue() - oldSceneWidth.doubleValue());
////			        diff *= 0.1;
////			        System.err.println();
////			        if(newSceneWidth.doubleValue() > oldSceneWidth.doubleValue())
////			        {
////			        	
////			        	 elevatorImageView.xProperty().bind(buildingImageView.fitWidthProperty().divide(divis).add(diff));
////			        }
////			        else{
////			        	
////						elevatorImageView.xProperty().bind(buildingImageView.fitWidthProperty().divide(divis).subtract(diff));
////
////			        }
//			        
//			        
//			        
//			       
//			    }
//			});
			
			
			
//			
////			elevatorImageView.layoutXProperty().bind(buildingImageView.layoutXProperty());
////			elevatorImageView.layoutYProperty().bind(buildingImageView.layoutYProperty());
//
//			//elevatorImageView.fitWidthProperty().bind(stage.widthProperty()); 
//			//elevatorImageView.fitHeightProperty().bind(stage.heightProperty());
//			
//			//ImageView elevatorImageView = loadImageView(ELEVATOR_INTERIOR_IMAGE, stage);
//					
//					
//					
//					
	//		stackPane.getChildren().add(buildingImageView);
	//		stackPane.getChildren().add(elevatorImageView);
//			//root.getChildren().add(stackPane);
//			root.getChildren().add(r);
//			root.getChildren().add(buildingImageView);
//			
//			
//			System.out.println(buildingImageView.getX());
//			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
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
