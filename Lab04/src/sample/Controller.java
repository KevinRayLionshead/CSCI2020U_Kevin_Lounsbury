package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {

    @FXML
    private TextField nameField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker datePicker;

    private DateTimeFormatter dateTimeFormatter;

    @FXML
    public void initialize() {
        //date formatter
        final String datePattern = "M/dd/yyyy";
        dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                String finalDate = null;
                if(date != null) {
                    finalDate = dateTimeFormatter.format(date);
                }
                return finalDate;
            }

            @Override
            public LocalDate fromString(String string) {
                LocalDate date = null;
                if(string != null){
                    date = LocalDate.parse(string, dateTimeFormatter);
                }
                return date;
            }
        });
    }


    @FXML
    public void phoneCheck() {

        if(!phoneField.isFocused()) {
            if (phoneField.getText().length() == 10 && !phoneField.getText().contains("-")) {
                String tempText = phoneField.getText(0, 3);
                tempText += "-";
                tempText += phoneField.getText(3, 6);
                tempText += "-";
                tempText += phoneField.getText(6, 10);

                phoneField.setText(tempText);
            } else if (phoneField.getText().length() == 12 && phoneField.getText(3, 4) == "-" &&
                    phoneField.getText(7, 8) == "-") {
            } else if ((phoneField.getText().length() != 10 && phoneField.getText().length() != 12) ||
                    (phoneField.getText().contains("-") && phoneField.getText().length() == 10)) {
                phoneField.setText("000-000-0000");
            }
        }

    }

    @FXML
    public void buttonPress(ActionEvent event) {
        if (nameField.getText().length() > 0 && fullNameField.getText().length() > 0 &&
        emailField.getText().length() > 0 && phoneField.getText().length() > 0) {
            System.out.println(nameField.getText());
            System.out.println(fullNameField.getText());
            System.out.println(emailField.getText());
            System.out.println(phoneField.getText());
            System.out.println(datePicker.getValue().format(dateTimeFormatter));
        }
        else {
            System.out.println("Enter all required fields");
        }
    }
}
