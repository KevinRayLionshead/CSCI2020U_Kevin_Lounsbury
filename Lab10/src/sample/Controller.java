package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Controller {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField textField;

    @FXML
    public void Exit(){System.exit(0);}

    @FXML
    public void SendMessage(){
        String username = usernameField.getText();
        String message = textField.getText();
        Socket clientSocket = null;
        PrintWriter out = null;
        try{
            clientSocket = new Socket("localhost", 8080);
            out = new PrintWriter(new BufferedOutputStream(clientSocket.getOutputStream()));
            out.println(username + ": " + message);
            out.flush();
            out.close();
            clientSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(message);
    }

}
