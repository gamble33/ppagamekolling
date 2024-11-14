package game.ui;

import game.callbacks.TextEntered;
import game.sound.Sound;
import game.sound.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameView {
    private static final int MAXIMUM_SCROLLABLE_COMPONENTS = 20;

    private JPanel scrollablePanel;
    private GridBagConstraints gbc;
    private JTextField inputField;

    private final Queue<JComponent> scrollableComponents = new LinkedList<>();
    private final Map<String, List<JComponent>> taggedComponents = new HashMap<>();
    private final List<JComponent> componentsToRemove = new ArrayList<>();

    private final List<TextEntered> listeners = new ArrayList<>();
    private final List<TextEntered> listenersToRemove = new ArrayList<>();
    private final List<TextEntered> listenersToAdd = new ArrayList<>();

    public void addUIListener(TextEntered listener) {
        listenersToAdd.add(listener);
    }

    public void removeUIListener(TextEntered listener) {
        listenersToRemove.add(listener);
    }

    public void disableInputField() {
        inputField.setEnabled(false);
        inputField.setVisible(false);
    }

    public void enableInputField() {
        inputField.setEnabled(true);
        inputField.setVisible(true);
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

        inputField = new JTextField(20);
        frame.add(inputField, BorderLayout.SOUTH);
        inputField.addActionListener(e -> {
            checkTooManyComponents();
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

    public void addButton(JButton button, String tag) {
        scrollablePanel.add(button, gbc);
        scrollablePanel.repaint();
        scrollablePanel.revalidate();
        taggedComponents.computeIfAbsent(tag, k -> new ArrayList<>()).add(button);
        button.addActionListener(e -> {
            componentsToRemove.addAll(taggedComponents.get(tag));
            removeComponents();
            taggedComponents.remove(tag);
        });
    }

    public void addText(String text) {
        if (text.length() > 80) {
            splitText(text);
            return;
        }
        addTextLabel(text);
    }

    public void addTextLabel(String text) {
        JComponent component = new JLabel(text);
        scrollablePanel.add(component, gbc);
        scrollablePanel.repaint();
        scrollablePanel.revalidate();
        scrollableComponents.add(component);
    }

    private void splitText(String text) {
        int n = (int) Math.ceil(text.length() / 60f);
        String[] words = text.split(" ");
        String[] groups = new String[n];
        Arrays.fill(groups, "");

        int currentLength = 0;
        int groupIndex = 0;
        for (String word : words) {
            groups[groupIndex] += word + " ";
            currentLength += word.length() + 1;
            if (currentLength > 60 && groupIndex < n - 1) {
                groupIndex++;
                currentLength = 0;
            }
        }

        for (String group : groups) {
            addTextLabel(group);
        }
    }

    private void checkTooManyComponents() {
        if (scrollableComponents.size() > MAXIMUM_SCROLLABLE_COMPONENTS) {
            for (int i = 0; i < scrollableComponents.size() - MAXIMUM_SCROLLABLE_COMPONENTS; i++) {
                scrollablePanel.remove(scrollableComponents.remove());
            }
        }
    }

    private void removeComponents() {
        componentsToRemove.forEach(scrollablePanel::remove);
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