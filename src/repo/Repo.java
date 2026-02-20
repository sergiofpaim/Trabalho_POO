package repo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import model.Usuario;

public class Repo {

    public static void serialize(File arquivo, Object o) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo));
            oos.writeObject(o);
            oos.close();
        } catch (IOException e) {
        }
    }

    public static <T> HashMap<String, T> desserialize(File arquivo) {
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (HashMap<String, T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    public static String validar(String user, String pass, HashMap<String, Usuario> usuarios) {
        for (Map.Entry<String, Usuario> e : usuarios.entrySet()) {
            if (e.getValue().getNome().equals(user) && e.getValue().getSenha().equals(pass)) {
                return e.getKey();
            }
        }

        return "";
    }
}
