import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import java.util.*;

public class MakeaccountScene {
    private Scene scene = null;
    private Parent root = null;
    public static MakeaccountController makeaccountcontroller;

    public MakeaccountScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("makeaccount.fxml"));
            root = (Parent) fxmlLoader.load();
            makeaccountcontroller = (MakeaccountController) fxmlLoader.getController();
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
            try {
                Mainchat.myName = makeaccountcontroller.username.getText();
                if (!makeaccountcontroller.username.getText().equals("")
                        && !makeaccountcontroller.password.getText().equals("")
                        && makeaccountcontroller.password.getText().length() > 5)
                    Mainchat.out.writeUTF("MAKE" + " " + makeaccountcontroller.username.getText() + " "
                            + makeaccountcontroller.password.getText());
                else
                    makeaccountcontroller.errortext.setText("passwordの長さは６文字以上");
            } catch (Exception ie) {
                System.out.println("sucess");
            }
            break;
        default:
            break;
        }
    }
}