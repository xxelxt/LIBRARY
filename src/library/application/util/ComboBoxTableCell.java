package library.application.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.StringConverter;

public class ComboBoxTableCell<T> extends TableCell<T, Date> {
    
//    private final DateTimeFormatter formatter ;
    private final DatePicker datePicker;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public ComboBoxTableCell(TableColumn<T, Date> column) {
		this.datePicker = new DatePicker();
		this.datePicker.setConverter(
			new StringConverter<>() {
	            @Override
	            public String toString(LocalDate date) {
	              return (date != null) ? dateFormatter.format(date) : "";
	            }

	            @Override
	            public LocalDate fromString(String string) {
	              return (string != null && !string.isEmpty())
	                  ? LocalDate.parse(string, dateFormatter)
	                  : null;
	            }
			});
//		this.datePicker.
//		this.datePicker.editableProperty().bind(column.editableProperty());
//		this.datePicker.disableProperty().bind(column.editableProperty().not());
//		this.datePicker.setOnShowing(event -> {
//		    final TableView<T> tableView = getTableView();
//		    tableView.getSelectionModel().select(getTableRow().getIndex());
//		    tableView.edit(tableView.getSelectionModel().getSelectedIndex(), column);	    
//		});
		this.datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
		    if(isEditing()) {
		    	commitEdit(Date.valueOf(newValue));
		    }
		});
		
		setGraphic(datePicker);
		this.setContentDisplay(ContentDisplay.TEXT_ONLY);
    }
    
    @Override
    public void startEdit() {
        super.startEdit();
        // setGraphic(datePicker);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        System.out.println("Editing started");
    }
    
    @Override public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
        System.out.println("Cancelled");
    }
 
    @Override
    protected void updateItem(Date item, boolean empty) {
		super.updateItem(item, empty);	

		if(empty || item == null) {
			setText(null);
		    setGraphic(null);
		    if (item == null) {
		    	System.out.println("Something happened item is null");
		    } else {
		    	System.out.println("FUCK EMTPU");
		    }
		} else {
		    datePicker.setValue(item.toLocalDate());
		    setText(item.toString());
			setContentDisplay(ContentDisplay.TEXT_ONLY);
			System.out.println("IS NOOOOOOOOOOT");
		} 
    }

    @Override
    public void commitEdit(Date newValue) {
    	System.out.println("WTF WHEN DID YOU COMMIT");
        super.commitEdit(newValue);    
    }
}

