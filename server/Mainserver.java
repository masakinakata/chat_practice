import java.util.*;
import java.io.*;
import java.net.*;

// スレッド部（各クライアントに応じて）
class ClientProcThread extends Thread {
    private int number; // 自分の番号
    // private Socket incoming;
    private DataInputStream myIn;
    private DataOutputStream myOut;
    private String myName = "NONAME"; // 接続者の名前

    public ClientProcThread(int n, Socket i, DataInputStream in, DataOutputStream out) {
        number = n;
        // incoming = i;
        myIn = in;
        myOut = out;
    }

    public void run() {
        try {

            while (true) { // 無限ループで，ソケットへの入力を監視する

                String str = null;
                try {
                    str = myIn.readUTF();
                } catch (Exception e) {
                    System.err.println("will  be closed.....");
                }
                if (str.equals("null"))
                    System.exit(0);
                System.out.println("Received from client No." + number + "(" + myName + "), Messages: " + str);
                if (str != null) { // このソケット（バッファ）に入力があるかをチェック
                    if (str.toUpperCase().equals("BYE")) {
                        myOut.writeUTF("Good bye!");
                        break;
                    }
                    String[] message = str.split(" ");
                    switch (message[0]) {
                    case "LOGIN":
                        myName = message[1];
                        if (Logincheck.check(message[1], message[2])) {
                            myOut.writeUTF("LOGIN" + " " + "SUCCESS");
                            String[] roomdata;
                            /* グループリストを返す */
                            try {
                                File file = new File("username/" + message[1] + ".txt");
                                FileReader fr = new FileReader(file);
                                BufferedReader br = new BufferedReader(fr);
                                String data;
                                while ((data = br.readLine()) != null) {
                                    System.out.println(data);
                                    myOut.writeUTF(data);
                                    roomdata = data.split(" ");
                                    Mainserver.roomlist.get(Integer.parseInt(roomdata[2])).member.add(myOut);
                                }
                                br.close();
                                myOut.writeUTF("END");
                            } catch (Exception e) {
                                System.err.println("error" + e);
                            }
                            /* フレンドリストを返す */
                            try {
                                File file = new File("friendlist/" + message[1] + ".txt");
                                FileReader fr = new FileReader(file);
                                BufferedReader br = new BufferedReader(fr);
                                String data;
                                while ((data = br.readLine()) != null) {
                                    myOut.writeUTF(data);
                                }
                                br.close();
                                myOut.writeUTF("END");
                            } catch (Exception e) {
                                System.err.println("friend error" + e);
                            }
                        } else
                            myOut.writeUTF("LOGIN" + " " + "FAIL");
                        break;
                    case "MESSAGE":
                        Mainserver.SendAll(str, Mainserver.roomlist.get(Integer.parseInt(message[1])));
                        try {
                            FileWriter file = new FileWriter(
                                    "list/" + Mainserver.roomlist.get(Integer.parseInt(message[1])).roomnumber + ".txt",
                                    true);
                            PrintWriter pw = new PrintWriter(new BufferedWriter(file));
                            pw.println(str);
                            pw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "MAKE":
                        if (!Logincheck.make(message[1], message[2])) {
                            myOut.writeUTF("MAKE" + " " + "FAIL");
                        } else {
                            myOut.writeUTF("MAKE" + " " + "SUCCESS");
                            System.out.println("MAKE SUCCESS");
                            try {
                                FileWriter file = new FileWriter("username/username.txt", true);
                                PrintWriter pw = new PrintWriter(new BufferedWriter(file));
                                pw.println(message[1] + " " + message[2]);
                                pw.close();
                                File makefile = new File("username/" + message[1] + ".txt");
                                makefile.createNewFile();
                            } catch (Exception e) {
                                System.err.println("新規ユーザのファイル書き込みに失敗しました");
                                e.printStackTrace();
                            }
                        }
                    case "LIST":
                        try {
                            File file = new File("list/" + message[1] + ".txt");
                            FileReader fr = new FileReader(file);
                            BufferedReader br = new BufferedReader(fr);
                            String data;
                            while ((data = br.readLine()) != null) {
                                System.out.println(data);
                                myOut.writeUTF(data);
                            }
                            br.close();
                            myOut.writeUTF("LIST");
                        } catch (Exception e) {
                        }
                        break;
                    default:
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // ここにプログラムが到達するときは，接続が切れたとき
            System.out.println("Disconnect from client No." + number + "(" + myName + ")");
            for (int i = 0; i < Mainserver.roomlist.size(); i++) {
                if (Mainserver.roomlist.get(i).member.contains(myOut))
                    Mainserver.roomlist.get(i).member.remove(Mainserver.roomlist.get(i).member.indexOf(myOut));
            }
            Mainserver.SetFlag(number, false); // 接続が切れたのでフラグを下げる
        }
    }
}

public class Mainserver extends Thread {
    private static int maxConnection = 100; // 最大接続数
    private static Socket[] incoming; // 受付用のソケット
    private static boolean[] flag; // 接続中かどうかのフラグ
    private static DataInputStream[] in;
    private static DataOutputStream[] out; // 出力ストリーム用の配列
    private static ClientProcThread[] myClientProcThread; // スレッド用の配列
    private static int member; // 接続しているメンバーの数
    public static List<Room> roomlist = new ArrayList<Room>();

    // 全員にメッセージを送る
    public static void SendAll(String str, Room room) {
        // 送られた来たメッセージを接続している全員に配る
        for (int i = 0; i < room.member.size(); i++) {
            try {
                room.member.get(i).writeUTF(str);
                room.member.get(i).flush(); // バッファをはき出す＝＞バッファにある全てのデータをすぐに送信する
                System.out.println("Send messages to client No." + i);
            } catch (IOException e) {
            }
        }
    }

    // フラグの設定を行う
    public static void SetFlag(int n, boolean value) {
        flag[n] = value;
    }

    // mainプログラム
    public static void main(String[] args) {
        // 必要な配列を確保する boolean checked = false;
        incoming = new Socket[maxConnection];
        flag = new boolean[maxConnection];
        in = new DataInputStream[maxConnection];
        out = new DataOutputStream[maxConnection];
        myClientProcThread = new ClientProcThread[maxConnection];
        try {
            File file = new File("list/group.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String data;
            String[] detail;
            Room room;
            while ((data = br.readLine()) != null) {
                try {
                    detail = data.split(" ");
                    room = new Room(Integer.parseInt(detail[1]), detail[0]);
                    roomlist.add(room);
                } catch (Exception e) {
                    System.err.println("file don't exist");
                }
            }
            br.close();
        } catch (Exception e) {
            System.err.println("error" + e);
        }
        int n = 1;
        member = 0; // 誰も接続していないのでメンバー数は０

        try {
            System.out.println("The server has launched!");
            ServerSocket server = new ServerSocket(10000); // 10000番ポートを利用する

            while (n <= maxConnection) {
                incoming[n] = server.accept();
                flag[n] = true;
                System.out.println("Accept client No." + n);
                // 必要な入出力ストリームを作成する
                in[n] = new DataInputStream(incoming[n].getInputStream());
                out[n] = new DataOutputStream(incoming[n].getOutputStream());
                myClientProcThread[n] = new ClientProcThread(n, incoming[n], in[n], out[n]); // 必要なパラメータを渡しスレッドを作成
                member = n; // メンバーの数を更新する
                myClientProcThread[n].start();
                n++;
            }
            /*
             * for(int i=1;i<=n;i++){ myClientProcThread[i].start(); // スレッドを開始する }
             */
            server.close();
        } catch (Exception e) {
            System.err.println("ソケット作成時にエラーが発生しました: " + e);
        }
    }
}

class Room {
    List<DataOutputStream> member = new ArrayList<DataOutputStream>();
    int roomnumber;
    String roomname;

    Room(int number, String name) {
        this.roomnumber = number;
        this.roomname = name;
    }
}