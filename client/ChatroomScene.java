import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ChatroomScene {
    private Scene scene = null;
    private Parent root = null;
    public static ChatroomController chatroomcontroller;

    public ChatroomScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chatroom.fxml"));
            root = (Parent) fxmlLoader.load();
            chatroomcontroller = (ChatroomController) fxmlLoader.getController();
            scene = new Scene(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return scene;
    }
}