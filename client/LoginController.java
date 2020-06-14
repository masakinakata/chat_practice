import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public TextField username;

    @FXML
    public TextField password;

    @FXML
    private Button newaccount;

    @FXML
    public Button loginbutton;

    @FXML
    public Text exception;

    @FXML
    void makenewaccount() {
        exception.setText("");
        Mainchat.changeScene(Mainchat.sceneType.Makeaccount);
    }

    @FXML
    void Login() {
        if (Mainchat.firstlogin && !Mainchat.loginsuccess) {
            try {
                if (!username.getText().equals("") && !password.getText().equals("")) {
                    Mainchat.myName = username.getText();
                    Mainchat.myPassword = password.getText();
                    Mainchat.changeScene(Mainchat.sceneType.Choice);
                } else
                    exception.setText("入力してください");
            } catch (Exception e) {
                System.err.println("error");
            }
        } else {
            try {
                if (!username.getText().equals("") && !password.getText().equals("")) {
                    String user = username.getText();
                    String pass = password.getText();
                    Mainchat.myName = user;
                    System.out.println("login name");
                    Mainchat.out.writeUTF("LOGIN" + " " + user + " " + pass);
                    System.out.println("send");
                    Mainchat.changeScene(Mainchat.sceneType.Choice);
                } else {
                    exception.setText("入力してください");
                }
            } catch (Exception e) {
                System.out.println("error");
            }
        }
    }

    @FXML
    void initialize() {
    }
}
