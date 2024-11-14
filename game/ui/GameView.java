package game.ui;

import game.callbacks.TextEntered;
import game.sound.Sound;
import game.sound.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class GameView {
    private JPanel scrollablePanel;
    private GridBagConstraints gbc;
    private final List<TextEntered> listeners = new ArrayList<>();
    private final List<TextEntered> listenersToRemove = new ArrayList<>();
    private final List<TextEntered> listenersToAdd = new ArrayList<>();

    public void addUIListener(TextEntered listener) {
        listenersToAdd.add(listener);
    }

    public void removeUIListener(TextEntered listener) {
        listenersToRemove.add(listener);
    }

    public void show() {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(720, 480);
        frame.setLayout(new BorderLayout());

        // Title
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Kazakh Mountains");
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Scrollable Region
        ImageIcon imageIcon = new ImageIcon("data/images/home.png");
        BackgroundPanel scrollablePanel = new BackgroundPanel(imageIcon.getImage());
        this.scrollablePanel = scrollablePanel;
        scrollablePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);
        JScrollPane scrollPane = new JScrollPane(scrollablePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        JTextField inputField = new JTextField(20);
        frame.add(inputField, BorderLayout.SOUTH);
        inputField.addActionListener(e -> {
            listeners.removeAll(listenersToRemove);
            listeners.addAll(listenersToAdd);
            listenersToAdd.clear();
            listenersToRemove.clear();

            String input = inputField.getText();
            inputField.setText("");
            listeners.forEach(listener -> listener.onEnter(input));
        });

        frame.setVisible(true);


        SoundPlayer<Sound> soundPlayer = new SoundPlayer<Sound>();
        soundPlayer.loadSounds(Sound.class);
        soundPlayer.playSound(Sound.MusicHome, false);
    }

    public void addText(String text) {
        if (scrollablePanel != null) {
            scrollablePanel.add(new JLabel(text), gbc);
            scrollablePanel.repaint();
            scrollablePanel.revalidate();
        }
    }

    static class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, this);
            }
        }
    }
}