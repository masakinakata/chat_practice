import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MakeaccountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public TextField username;

    @FXML
    public TextField password;

    @FXML
    private Button backbutton;

    @FXML
    private Button makeaccount;

    @FXML
    public Text errortext;

    @FXML
    void back() {
        Mainchat.changeScene(Mainchat.sceneType.Login);
        errortext.setText("");
    }

    @FXML
    void make() {
        try {
            Mainchat.myName = username.getText();
            if (!username.getText().equals("") && !password.getText().equals("") && password.getText().length() > 5)
                Mainchat.out.writeUTF("MAKE" + " " + username.getText() + " " + password.getText());
            else
                errortext.setText("passwordの長さは６文字以上");
        } catch (Exception e) {
            System.out.println("sucess");
        }
    }

    @FXML
    void initialize() {

    }
}
