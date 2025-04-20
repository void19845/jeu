package Main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Thai-nam & mathis");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH); //  Plein écran
        window.setUndecorated(false); // ou true a toi de voir thai nam

        GameMenu menu = new GameMenu(window);
        window.add(menu);
        window.setVisible(true);
    }
}
