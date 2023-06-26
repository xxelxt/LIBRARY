package library.application.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.StringConverter;

public class ComboBoxTableCell<T> extends TableCell<T, String> {
    
//    private final DateTimeFormatter formatter ;
    private final ComboBox<String> comboBox;
    
    
    public ComboBoxTableCell(TableColumn<T, String> column, ObservableList<String> comboBoxItems) {
    	this.comboBox = new ComboBox<>();
    	this.comboBox.setItems(comboBoxItems);
    	this.comboBox.setValue(comboBoxItems.get(0));
//		this.datePicker.
//		this.datePicker.editableProperty().bind(column.editableProperty());
//		this.datePicker.disableProperty().bind(column.editableProperty().not());
//		this.datePicker.setOnShowing(event -> {
//		    final TableView<T> tableView = getTableView();
//		    tableView.getSelectionModel().select(getTableRow().getIndex());
//		    tableView.edit(tableView.getSelectionModel().getSelectedIndex(), column);	    
//		});
		this.comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
		    if(isEditing()) {
		    	commitEdit(newValue);
		    }
		});
		
		setGraphic(comboBox);
		this.setContentDisplay(ContentDisplay.TEXT_ONLY);
    }
    
    @Override
    public void startEdit() {
        super.startEdit();
        // setGraphic(datePicker);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        System.out.println("ComboBox started");
    }
    
    @Override public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
        System.out.println("ComboBox cancelled");
    }
 
    @Override
    protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);	

		if(empty || item == null) {
			setText(null);
		    setGraphic(null);
		    if (item == null) {
		    	System.out.println("Something happened combobox is null????");
		    } else {
		    	System.out.println("empty but item != null??");
		    }
		} else {
			comboBox.setValue(item);
		    setText(item);
			setContentDisplay(ContentDisplay.TEXT_ONLY);
			System.out.println("Updated comboBox " + this);
		} 
    }

    @Override
    public void commitEdit(String newValue) {
    	System.out.println("ComboBox commited");
        super.commitEdit(newValue);    
    }
}

