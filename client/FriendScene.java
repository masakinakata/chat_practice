import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class FriendScene {
    private Scene scene = null;
    private Parent root = null;
    public static FriendController friendcontroller;

    public FriendScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("friend.fxml"));
            root = (Parent) fxmlLoader.load();
            friendcontroller = (FriendController) fxmlLoader.getController();
            scene = new Scene(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return scene;
    }
}