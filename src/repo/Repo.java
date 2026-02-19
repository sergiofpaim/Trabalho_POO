package repo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Repo {

     public static void serialize(File arquivo, Object o) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo));
            oos.writeObject(o);
            oos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
 
    public static <T> HashMap<String, T> desserialize(File arquivo) {
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new HashMap<>();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo));
            return (HashMap<String, T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    
}
