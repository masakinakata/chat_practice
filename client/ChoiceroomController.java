import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ChoiceroomController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public ListView<String> talklist;

    @FXML
    private Button friendbutton;

    @FXML
    private Button choiceroom;

    @FXML
    private Text nametext;

    @FXML
    private Text errortext;

    private boolean setflag = true;

    @FXML
    void choicetalk() {
        errortext.setText("");
        if (talklist.getSelectionModel().getSelectedItem().equals(choiceroom.getText()) && setflag) {
            setflag = false;
            String chatname = choiceroom.getText();
            String[] roomname;
            for (int i = 0; i < Mainchat.roomlist.size(); i++) {
                roomname = Mainchat.roomlist.get(i).split(" ");
                if (chatname.equals(roomname[0])) {
                    ChatroomScene.chatroomcontroller.roomname = roomname[0];
                    ChatroomScene.chatroomcontroller.roomnumber = Integer.parseInt(roomname[1]);
                    break;
                }
            }
            try {
                Mainchat.out.writeUTF("LIST" + " " + ChatroomScene.chatroomcontroller.roomnumber);
                Mainchat.changeScene(Mainchat.sceneType.Wait);
            } catch (IOException e) {
            }
        } else {
            setflag = true;
            choiceroom.setText(talklist.getSelectionModel().getSelectedItem());
        }

    }

    @FXML
    void enterroom() {
        errortext.setText("");
        String chatname = choiceroom.getText();
        if (chatname.equals("部屋名を選んでください") || chatname.equals("")) {
            System.out.println("部屋を選択しましょう.");
            errortext.setText("部屋名を選んでください");
        } else {
            String[] roomname;
            for (int i = 0; i < Mainchat.roomlist.size(); i++) {
                roomname = Mainchat.roomlist.get(i).split(" ");
                if (chatname.equals(roomname[0])) {
                    ChatroomScene.chatroomcontroller.roomname = roomname[0];
                    ChatroomScene.chatroomcontroller.roomnumber = Integer.parseInt(roomname[1]);
                    break;
                }
            }
            try {
                Mainchat.out.writeUTF("LIST" + " " + ChatroomScene.chatroomcontroller.roomnumber);
                Mainchat.changeScene(Mainchat.sceneType.Wait);
            } catch (IOException e) {
            }
        }
    }

    @FXML
    void friend() {
        errortext.setText("");
        Mainchat.changeScene(Mainchat.sceneType.Friend);
    }

    @FXML
    void initialize() {
    }

    public void setlist() {
        nametext.setText(Mainchat.myName);
        String[] roomname;
        if (Mainchat.roomlist.size() == 0)
            System.out.println("room is empty");
        for (int i = 0; i < Mainchat.roomlist.size(); i++) {
            roomname = Mainchat.roomlist.get(i).split(" ");
            talklist.getItems().add(roomname[0]);
        }
        talklist.getSelectionModel().select(0);
    }
}
