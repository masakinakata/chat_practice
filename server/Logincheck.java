import java.io.*;
import java.util.*;

public class Logincheck {
    public static boolean check(String username, String password) {
        boolean checked = false;
        try {
            File file = new File("username/username.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String data;
            while ((data = br.readLine()) != null) {
                String[] name = data.split(" ");
                if (username.equals(name[0]) && password.equals(name[1])) {
                    checked = true;
                    break;
                }
            }
            br.close();
            return checked;
        } catch (Exception e) {
            return checked;
        }
    }

    public static boolean make(String username, String password) {
        boolean checked = true;
        try {
            File file = new File("username/username.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String data;
            while ((data = br.readLine()) != null) {
                String[] name = data.split(" ");
                if (username.equals(name[0]) || password.equals(name[1])) {
                    checked = false;
                    break;
                }
            }
            br.close();
            return checked;
        } catch (Exception e) {
            return false;
        }
    }
}