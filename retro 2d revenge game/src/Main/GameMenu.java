package Main;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JPanel {
    public GameMenu(JFrame window) {
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLayout(new GridBagLayout());

        JButton startButton = new JButton("Démarrer le jeu");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));

        startButton.addActionListener(e -> {
            window.getContentPane().removeAll();
            MainMenu mainMenu = new MainMenu(window);
            window.add(mainMenu);
            window.revalidate();
            mainMenu.requestFocusInWindow();
        });

        this.add(startButton);
    }
}
