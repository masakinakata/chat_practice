import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class UnderScene {
    private Scene scene = null;
    private Parent root = null;

    public UnderScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("under.fxml"));
            root = (Parent) fxmlLoader.load();
            scene = new Scene(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return scene;
    }
}