package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Application {

    static TextArea text = new TextArea();

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Lab10: Server");
        //Creating the main menu grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Button exitButton = new Button();
        exitButton.setText("Exit");
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(1);
            }
        });



        //text.setText("Text...");
        text.setPrefWidth(700);
        text.setPrefHeight(400);
        //text.setAlignment(Pos.TOP_LEFT);

        grid.add(text, 0,0);
        grid.add(exitButton, 0,1);

        Scene mainScene = new Scene(grid, 720, 480);

        primaryStage.setScene(mainScene);
        primaryStage.show();

        NewConnection(text);

    }
    public void NewConnection(TextArea text){
        ConnectionListener handler = new ConnectionListener(text);
        Thread handlerThread = new Thread(handler);
        handlerThread.start();
    }

    public static void main(String[] args){
        launch(args);
        System.out.println("Launch!");

    }
}
class ConnectionListener implements Runnable {
    private ServerSocket serverSocket;
    TextArea text;

    public ConnectionListener(TextArea text){
        this.text = text;
        try {
            serverSocket = new ServerSocket(8080);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            while(true){
                Socket acceptedSocket = serverSocket.accept();
                Scanner in = new Scanner(acceptedSocket.getInputStream());
                String line = "";
                String words = text.getText() + "\n";
                while (in.hasNextLine()) {
                    words+=in.nextLine();
                    System.out.println(words);
                }
                this.text.setText(words);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
