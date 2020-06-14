import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class FriendController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backbutton;

    @FXML
    public ListView<String> friendlist;

    @FXML
    private Button addbutton;

    @FXML
    void back() {
        Mainchat.changeScene(Mainchat.sceneType.Makechat);
    }

    @FXML
    void add() {
        Mainchat.changeScene(Mainchat.sceneType.Under);
    }

    @FXML
    void initialize() {

    }
}
