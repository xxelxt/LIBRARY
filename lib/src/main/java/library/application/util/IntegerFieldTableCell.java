package library.application.util;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class IntegerFieldTableCell<T> extends TextFieldTableCell<T, Integer> {

    public IntegerFieldTableCell() {
        super(new IntegerStringConverter());
        setTextFormatterForTextField();
    }

    private void setTextFormatterForTextField() {
    	if (getGraphic() != null) {
    		((TextField) getGraphic()).setTextFormatter(textFormatter);
    	}
    }
    
    private final TextFormatter<Integer> textFormatter = new TextFormatter<>(
    	change -> {
	        String newText = change.getControlNewText();
	        if (newText.matches("\\d*")) {
	            return change;
	        }
	        return null;
    	}
    );

    @Override
    public void startEdit() {
        super.startEdit();
        setTextFormatterForTextField();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setTextFormatterForTextField();
    }
}