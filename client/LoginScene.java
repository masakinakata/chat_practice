import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.util.*;
import javafx.scene.input.KeyEvent;

public class LoginScene {
    private Scene scene = null;
    private Parent root = null;
    public static LoginController logincontroller;

    public LoginScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginscene.fxml"));
            root = (Parent) fxmlLoader.load();
            logincontroller = (LoginController) fxmlLoader.getController();
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
            if (Mainchat.firstlogin && !Mainchat.loginsuccess) {
                try {
                    if (!logincontroller.username.getText().equals("")
                            && !logincontroller.password.getText().equals("")) {
                        Mainchat.myName = logincontroller.username.getText();
                        Mainchat.myPassword = logincontroller.password.getText();
                        Mainchat.changeScene(Mainchat.sceneType.Choice);
                    } else
                        logincontroller.exception.setText("入力してください");
                } catch (Exception ex) {
                    System.err.println("error");
                }
            } else {
                try {
                    if (!logincontroller.username.getText().equals("")
                            && !logincontroller.password.getText().equals("")) {
                        String user = logincontroller.username.getText();
                        String pass = logincontroller.password.getText();
                        Mainchat.myName = user;
                        System.out.println("login name");
                        Mainchat.out.writeUTF("LOGIN" + " " + user + " " + pass);
                        System.out.println("send");
                        Mainchat.changeScene(Mainchat.sceneType.Choice);
                    } else {
                        logincontroller.exception.setText("入力してください");
                    }
                } catch (Exception ex) {
                    System.out.println("error");
                }
            }
            break;
        default:
            break;
        }
    }
}
