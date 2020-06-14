import java.util.*;
import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class Clientconnect extends Thread {
    Socket socket;
    String myName;
    public static boolean outflag = false;

    public Clientconnect(Socket s, String n) {
        socket = s;
        myName = n;
    }

    // 通信状況を監視し，受信データによって動作する
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            Mainchat.out = new DataOutputStream(socket.getOutputStream());
            outflag = true;
            String name = "false", pass = "false";
            String inputLine;
            String roomname;
            while (true) {
                inputLine = in.readUTF(); // データを一行分だけ読み込んでみる
                if (inputLine != null) { // 読み込んだときにデータが読み込まれたかどうかをチェックする
                    String[] inputTokens = inputLine.split(" "); // 入力データを解析するために、スペースで切り分ける
                    String cmd = inputTokens[0]; // コマンドの取り出し．１つ目の要素を取り出す

                    switch (cmd) {
                    case "LOGIN":
                        System.out.println("received");
                        if (inputTokens[1].equals("SUCCESS"))
                            Mainchat.loginsuccess = true;
                        else {
                            Mainchat.loginsuccess = false;
                            Mainchat.logincheck = true;
                        }
                        break;
                    case "END":
                        Mainchat.logincheck = true;
                        break;
                    case "ROOM":
                        roomname = inputTokens[1] + " " + inputTokens[2];
                        Mainchat.roomlist.add(roomname);
                        break;
                    case "MESSAGE":
                        try {
                            if (ChatroomScene.chatroomcontroller.roomnumber == Integer.parseInt(inputTokens[1])) {
                                ChatroomScene.chatroomcontroller.textarea.appendText(inputTokens[2] + ":");
                                for (int i = 3; i < inputTokens.length; i++) {
                                    ChatroomScene.chatroomcontroller.textarea.appendText(inputTokens[i] + " ");
                                }
                                ChatroomScene.chatroomcontroller.textarea.appendText("\n");
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case "MAKE":
                        if (inputTokens[1].equals("SUCCESS")) {
                            AnimationTimer timer = new AnimationTimer() {
                                @Override
                                public void handle(long now) {
                                    Mainchat.changeScene(Mainchat.sceneType.Makechat);
                                }
                            };
                            timer.start();
                        } else {
                            MakeaccountScene.makeaccountcontroller.errortext
                                    .setText("usernameまたはpasswordがすでに使用しされています");
                        }

                        break;
                    case "LIST":
                        Mainchat.waitflag = false;
                        break;
                    case "FRIEND":
                        Mainchat.friendlist.add(inputTokens[1]);
                        FriendScene.friendcontroller.friendlist.getItems().add(inputTokens[1]);
                        break;
                    default:
                        break;
                    }
                }
            }
            /*
             * try { socket.close(); } catch (IOException e) {
             * System.err.println("scene is ....." + e); }
             */
        } catch (IOException e) {
            System.err.println("エラーが発生しました: " + e);
        }
    }
}