package views;

import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.BoldAction;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

public class ComboBoxCell<S, T> extends TableCell<S, T> {

	private final ComboBox<T> comboBox;
	private ObservableValue<T> ov;

	public ComboBoxCell(ArrayList<T> items, SummaryView parentView) {

		this.comboBox = new ComboBox<T>();
		this.comboBox.getItems().addAll(items);
		
		this.comboBox.setValue(items.get(0));
		this.comboBox.valueProperty().addListener((ChangeListener<? super T>) new ChangeListener<String>() {
	       
			@Override public void changed(ObservableValue ov, String oldVal, String newVal) {

	            System.out.println(ov);
	              System.out.println(oldVal);
	              System.out.println(newVal);
	              parentView.fireChangeElevatorImageEvent(newVal);
	          }    
	      });
		
		setAlignment(Pos.CENTER);

		setGraphic(comboBox);

	}

	@Override
	public void updateItem(T item, boolean empty) {

		super.updateItem(item, empty);

		if (empty) {

			setText(null);

			setGraphic(null);

		} else {

			setGraphic(comboBox);


		}

	}

}
