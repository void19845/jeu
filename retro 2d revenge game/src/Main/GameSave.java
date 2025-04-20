package Main;

import java.io.*;

public class GameSave {
    public static void save(GameData data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame.sav"))) {
            oos.writeObject(data);
        } catch (IOException e) {}
    }

    public static GameData load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.sav"))) {
            return (GameData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
