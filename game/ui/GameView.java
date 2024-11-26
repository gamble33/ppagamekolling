package game.ui;

import game.BackgroundImage;
import game.callbacks.TextEntered;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class GameView {
    private static final int MAXIMUM_SCROLLABLE_COMPONENTS = 20;
    private static final int WINDOW_WIDTH = 896;
    private static final int WINDOW_HEIGHT = 596;

    private JPanel scrollablePanel;
    private BackgroundPanel backgroundPanel;
    private JScrollPane scrollPane;
    private GridBagConstraints gbc;
    private JTextField inputField;
    private JLabel titleLabel;
    JFrame frame;

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

    public void end() {
        disableInputField();
    }

    public void close() {
        frame.dispose();
        System.exit(0);
    }

    public void show() {
        // Main window in which the game occurs
        frame = new JFrame("Game");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon("data/images/bg.png");
        backgroundPanel = new BackgroundPanel(imageIcon.getImage());
        backgroundPanel.setLayout(new BorderLayout());

        frame.setContentPane(backgroundPanel);

        // Title
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Kazakh Mountains");
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Scrollable Region
        scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);
        scrollPane = new JScrollPane(scrollablePanel);
        scrollablePanel.setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Text input field for commands
        inputField = new JTextField(20);
        inputField.addActionListener(e -> {
            // When a command is entered, all listeners are called. This is the observer design pattern.
            checkTooManyComponents();
            listeners.removeAll(listenersToRemove);
            listeners.addAll(listenersToAdd);
            listenersToAdd.clear();
            listenersToRemove.clear();

            String input = inputField.getText();
            inputField.setText("");
            listeners.forEach(listener -> listener.onEnter(input));
        });
        inputField.setOpaque(false);
        frame.add(inputField, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setBackgroundImage(BackgroundImage image) {
        backgroundPanel.setBackgroundImage(new ImageIcon(image.getFilePath()).getImage());
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
        scrollTo(button);
    }

    public void addText(String text) {
        if (text.length() > 80) {
            splitText(text);
            return;
        }
        addTextLabel(text);
    }

    public void addTextLabel(String text) {
        FadeInLabel component = new FadeInLabel(text);
        scrollablePanel.add(component, gbc);
        scrollablePanel.repaint();
        scrollablePanel.revalidate();
        scrollableComponents.add(component);
        scrollTo(component);
        component.startFadeIn();
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    private void scrollTo(JComponent component) {
        SwingUtilities.invokeLater(() -> {
            scrollablePanel.scrollRectToVisible(component.getBounds());
        });
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
        private Image backgroundImage;

        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, null);
        }

        public void setBackgroundImage(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
        }
    }

    /**
     * A custom JLabel that provides a fade-in effect for text.
     * The fade-in effect is controlled by a Timer that gradually increases the alpha value until the label becomes
     * fully opaque. The fading effect can be started by invoking the <code>startFadeIn()</code> method.
     */
    static class FadeInLabel extends JLabel {
        private float alpha = 0.0f; // initial transparency

        public FadeInLabel(String text) {
            super(text);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            super.paintComponent(g2d);
            g2d.dispose();
        }

        public void startFadeIn() {
            Timer timer = new Timer(40, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    alpha += 0.05f;
                    if (alpha >= 1.0f) {
                        alpha = 1.0f;
                        ((Timer) e.getSource()).stop();
                    }
                    repaint();
                }
            });
            timer.start();
        }
    }
}