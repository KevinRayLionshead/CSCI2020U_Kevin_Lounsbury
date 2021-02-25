package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn SSIDCol;
    @FXML
    private TableColumn assignmentsCol;
    @FXML
    private TableColumn midtermCol;
    @FXML
    private TableColumn finalExamCol;
    @FXML
    private TableColumn finalMarkCol;
    @FXML
    private TableColumn letterGradeCol;


    private TableView<StudentRecord> students;

    @FXML
    public void initialize(){
        SSIDCol.setCellValueFactory(new PropertyValueFactory<>("SSID"));
        assignmentsCol.setCellValueFactory(new PropertyValueFactory<>("assignments"));
        midtermCol.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        finalExamCol.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        finalMarkCol.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterGradeCol.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        tableView.setItems(DataSource.getAllMarks());

    }

}
