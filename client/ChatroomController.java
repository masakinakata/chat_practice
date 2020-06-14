import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ChatroomController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    public TextArea textarea;

    @FXML
    public TextArea message;

    @FXML
    private Button sendmessagebutton;

    @FXML
    private Button closebutton;

    public int roomnumber = 0;
    public String roomname;

    @FXML
    void backscene() {
        Mainchat.changeScene(Mainchat.sceneType.Makechat);
        textarea.setText("");
    }

    @FXML
    void sendmessage() {
        try {
            Mainchat.out.writeUTF("MESSAGE" + " " + roomnumber + " " + Mainchat.myName + " " + message.getText());
        } catch (Exception e) {
        }
        message.setText("");
    }

    @FXML
    void close() {
        System.exit(0);
    }

    @FXML
    void initialize() {
    }
}
