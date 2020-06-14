import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.util.*;

public class ChoiceroomScene {
    private Scene scene = null;
    private Parent root = null;
    public static ChoiceroomController choiceroomcontroller;

    public ChoiceroomScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("choiceroom.fxml"));
            root = (Parent) fxmlLoader.load();
            choiceroomcontroller = (ChoiceroomController) fxmlLoader.getController();
            scene = new Scene(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return scene;
    }
}