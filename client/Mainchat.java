import java.io.*;
import java.util.*;
import java.net.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

public class Mainchat extends Application {
    enum sceneType {
        Startup, Login, Choice, Chatroom, Makeaccount, Makechat, Friend, Under, Wait;
    }

    private static StartupScene startupscene = null;
    private static LoginScene loginscene = null;
    private static UnderScene underscene = null;
    private static ChatroomScene chatroomscene = null;
    private static ChoiceroomScene choiceroomscene = null;
    private static MakeaccountScene makeaccountscene = null;
    private static FriendScene friendscene = null;
    public static Stage stage = null;
    public static String myName = "user";
    public static String myPassword = "pass";
    public static String ipadress = "localhost";
    public static DataOutputStream out;
    private static Socket socket = null;
    public static boolean firstlogin = true;
    public static boolean loginsuccess = false;
    public static boolean logincheck = false;
    public static boolean waitflag = true;
    public static List<String> roomlist = new ArrayList<String>();
    public static List<String> friendlist = new ArrayList<String>();

    private boolean startupchangeflag = false;

    @Override
    public void start(Stage primaryStage) {
        try {
            startupscene = new StartupScene();
            loginscene = new LoginScene();
            underscene = new UnderScene();
            chatroomscene = new ChatroomScene();
            choiceroomscene = new ChoiceroomScene();
            makeaccountscene = new MakeaccountScene();
            friendscene = new FriendScene();
            stage = primaryStage;
            stage.setScene(startupscene.getScene());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeScene(sceneType scene) {
        switch (scene) {
        case Startup:
            stage.setScene(startupscene.getScene());
            break;
        case Login:
            stage.setScene(loginscene.getScene());
            break;
        case Choice:
            if (firstlogin) {
                firstlogin = false;
                try {
                    socket = new Socket(ipadress, 10000);
                    Clientconnect mrt = new Clientconnect(socket, myName); // 受信用のスレッドを作成する
                    mrt.start(); // スレッドを動かす（Runが動く）
                    System.out.println(myName + " " + myPassword);
                    try {
                        Object lock = new Object();
                        synchronized (lock) {
                            while (!Clientconnect.outflag) {
                                lock.wait(10);
                            }
                            lock.notify();
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Connect error" + e);
                        System.exit(0);
                    }
                    out.writeUTF("LOGIN" + " " + myName + " " + myPassword);
                } catch (UnknownHostException e) {
                    System.err.println("ホストの IP アドレスが判定できません: " + e);
                    System.exit(0);
                } catch (IOException e) {
                    System.err.println("エラーが発生しました: " + e);
                    System.exit(0);
                } catch (Exception e) {
                    logincheck = true;
                }

            }
            try {
                Object lock = new Object();
                synchronized (lock) {
                    while (!logincheck) {
                        lock.wait(10);
                    }
                    lock.notify();
                }
            } catch (InterruptedException e) {
                System.out.println("Connect error" + e);
                System.exit(0);
            }
            logincheck = false;
            if (loginsuccess) {
                stage.setScene(choiceroomscene.getScene());
                ChoiceroomScene.choiceroomcontroller.setlist();
            } else {
                LoginScene.logincontroller.exception.setText("usernameまたはpasswordが違います");
            }
            break;
        case Chatroom:
            stage.setScene(chatroomscene.getScene());
            break;
        case Makeaccount:
            stage.setScene(makeaccountscene.getScene());
            if (firstlogin) {
                firstlogin = false;
                try {
                    socket = new Socket(ipadress, 10000);
                    Clientconnect mrt = new Clientconnect(socket, myName); // 受信用のスレッドを作成する
                    mrt.start(); // スレッドを動かす（Runが動く）
                } catch (UnknownHostException e) {
                    System.err.println("ホストの IP アドレスが判定できません: " + e);
                } catch (IOException e) {
                    System.err.println("エラーが発生しました: " + e);
                    System.exit(0);
                }
            }
            break;
        case Makechat:
            stage.setScene(choiceroomscene.getScene());
            break;
        case Friend:
            FriendScene.friendcontroller.friendlist.getSelectionModel().select(0);
            stage.setScene(friendscene.getScene());
            break;
        case Under:
            stage.setScene(underscene.getScene());
            break;
        case Wait:
            try {
                Object lock = new Object();
                synchronized (lock) {
                    while (waitflag) {
                        lock.wait(10);
                    }
                    lock.notify();
                }
            } catch (InterruptedException e) {
                System.out.println("Connect error" + e);
                System.exit(0);
            }
            stage.setScene(chatroomscene.getScene());
            break;
        default:
            stage.setScene(underscene.getScene());
            break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}