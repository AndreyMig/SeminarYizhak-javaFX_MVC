package views;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

public class ComboBoxCell<S, T> extends TableCell<S, T> {

	private final ComboBox<T> comboBox;
	private T currentValue;
	private static int numOf = 0;
	private SummaryView parentView;
	@SuppressWarnings("unchecked")
	public ComboBoxCell(ArrayList<T> items, SummaryView parentView) {

		this.comboBox = new ComboBox<T>();
		this.comboBox.setId("comb"+ ++numOf);
		this.comboBox.getItems().addAll(items);
		currentValue = items.get(0);
		this.parentView = parentView;
		this.comboBox.setValue(items.get(0));
		this.comboBox.valueProperty().addListener((ChangeListener<? super T>) new ChangeListener<T>() {
	       
			@Override public void changed(ObservableValue ov, T oldVal, T newVal) {
				currentValue = newVal;
	            parentView.fireChangeElevatorImageEvent(newVal.toString(), ComboBoxCell.this);
	          }    
	      });
		
		setAlignment(Pos.CENTER);

//		setGraphic(comboBox);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateItem(T item, boolean empty) {
		
		super.updateItem(item, empty);

		if (empty) {

			setText(null);

			setGraphic(null);

		} else {
			
			setGraphic(this.comboBox);
			if((T)this.parentView.getCurrentImageFileName(ComboBoxCell.this) != null)
				this.comboBox.setValue((T)this.parentView.getCurrentImageFileName(ComboBoxCell.this));

		}

	}

}
