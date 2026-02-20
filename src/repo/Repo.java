package repo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Repo {

    public static void serialize(File arquivo, Object o) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo));
            oos.writeObject(o);
            oos.close();
        } catch (IOException e) {
            System.out.println("Erro ao serializar " + arquivo.getName() + ": " + e.getMessage());
        }
    }

    public static <T> ArrayList<T> desserialize(File arquivo) {
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (ArrayList<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar " + arquivo.getName() + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
