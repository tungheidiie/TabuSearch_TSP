package TabuSearch_TSP.display;

import javax.swing.*;
import java.awt.*;

/**
 * Draws results of TSP, as well as a path.
 */
public class WindowResults extends JFrame {
    private final int WIDTH = 500;
    private final int HEIGHT = 680;
    private final TextPanel textPanel;
    private final StringBuilder results;

    public WindowResults(StringBuilder results) {
        this.results = results;
        textPanel = createTextPanel();
        add(textPanel);
        setWindowProperties();
    }

    private TextPanel createTextPanel() {
        TextPanel panel = new TextPanel(results);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return panel;
    }

    private void setWindowProperties() {
        setLocation(1020, 15);
        setResizable(false);
        pack();
        setTitle("Tabu search Results");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private static class TextPanel extends JPanel {
        private final JTextArea textArea;

        public TextPanel(StringBuilder text) {
            textArea = new JTextArea();
            textArea.setText(text.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 16));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(480, 640));
            add(scrollPane);
        }
    }
}
