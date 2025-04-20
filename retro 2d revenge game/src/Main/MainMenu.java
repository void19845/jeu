package Main;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    public MainMenu(JFrame window) {
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;

        JButton continuer = new JButton("Continuer");
        JButton nouvellePartie = new JButton("Nouvelle Partie");
        JButton parametres = new JButton("Paramètres");
        JButton quitter = new JButton("Quitter");

        Font font = new Font("Arial", Font.BOLD, 22);
        continuer.setFont(font);
        nouvellePartie.setFont(font);
        parametres.setFont(font);
        quitter.setFont(font);

        continuer.addActionListener(e -> {
            GameData data = GameSave.load();
            if (data != null) {
                window.getContentPane().removeAll();
                GamePanel gamePanel = new GamePanel(data);
                window.add(gamePanel);
                window.revalidate();
                gamePanel.requestFocusInWindow();
                gamePanel.startGameThread();
            } else {
                JOptionPane.showMessageDialog(this, "Aucune sauvegarde trouvée.");
            }
        });

        nouvellePartie.addActionListener(e -> {
            window.getContentPane().removeAll();
            GamePanel gamePanel = new GamePanel();
            window.add(gamePanel);
            window.revalidate();
            gamePanel.requestFocusInWindow();
            gamePanel.startGameThread();
        });

        quitter.addActionListener(e -> System.exit(0));

        gbc.gridy = 0; this.add(continuer, gbc);
        gbc.gridy = 1; this.add(nouvellePartie, gbc);
        gbc.gridy = 2; this.add(parametres, gbc);
        gbc.gridy = 3; this.add(quitter, gbc);
    }
}
