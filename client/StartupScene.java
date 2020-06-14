import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class StartupScene {
    private Scene scene = null;
    private Parent root = null;
    public static StartupController startupcontroller;

    public StartupScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("startup.fxml"));
            root = (Parent) fxmlLoader.load();
            startupcontroller = (StartupController) fxmlLoader.getController();
            scene = new Scene(root);
            scene.setOnKeyPressed(e -> keyPressed(e));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return scene;
    }

    private void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
        case ENTER:
            Mainchat.changeScene(Mainchat.sceneType.Login);
            break;
        default:
            break;
        }
    }
}