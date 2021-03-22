package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.*;

public class Controller {

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuItem newItem;
    @FXML
    private MenuItem openItem;
    @FXML
    private MenuItem saveItem;
    @FXML
    private MenuItem saveAsItem;
    @FXML
    private MenuItem exitItem;


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

    private String currentFileName = new String("");

    private void Load(){
        File inFile = new File(currentFileName);
        if (inFile.exists()){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(inFile));
                String line;
                ObservableList<StudentRecord> marks = FXCollections.observableArrayList();
                while ((line = reader.readLine()) != null){
                    String[] array = line.split(",");
                    marks.add(new StudentRecord(array[0], Float.parseFloat(array[1]), Float.parseFloat(array[2]),Float.parseFloat(array[3])));

                }
                tableView.setItems(marks);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void New(){


        ObservableList<StudentRecord> empty = FXCollections.observableArrayList();

        tableView.setItems(empty);

    }
    private void Open(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(tableView.getScene().getWindow());

        currentFileName = file.getName();
        Load();
    }
    private void Save() throws FileNotFoundException {
        ObservableList<StudentRecord> records = tableView.getItems();

        try {
            File outputFile = new File(currentFileName);
            if(outputFile.exists()) {
                PrintWriter output = new PrintWriter(outputFile);
                for (StudentRecord record : records) {
                    System.out.println(record.getSSID());
                    System.out.println(record.getAssignments());
                    System.out.println(record.getMidterm());
                    System.out.println(record.getFinalExam());

                    String line = record.getSSID();
                    line += ",";
                    line += record.getAssignments();
                    line += ",";
                    line += record.getMidterm();
                    line += ",";
                    line += record.getFinalExam();
                    output.println(line);
                }
                output.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }



    }
    private void SaveAs(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(tableView.getScene().getWindow());

        currentFileName = file.getName();
        //System.out.println(currentFileName);
        try {
            Save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void HandleNewAction(ActionEvent event){
        New();
    }
    @FXML
    public void HandleOpenAction(ActionEvent event){
        Open();
    }
    @FXML
    public void HandleSaveAction(ActionEvent event){
        try {
            Save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void HandleSaveAsAction(ActionEvent event){
        SaveAs();
    }
    @FXML
    public void HandleExitAction(ActionEvent event){
        System.exit(1);
    }

    @FXML
    public void initialize(){

        currentFileName = "studentRecords.csv";

        SSIDCol.setCellValueFactory(new PropertyValueFactory<>("SSID"));
        assignmentsCol.setCellValueFactory(new PropertyValueFactory<>("assignments"));
        midtermCol.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        finalExamCol.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        finalMarkCol.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterGradeCol.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        tableView.setItems(DataSource.getAllMarks());

    }

}
