package TabuSearch_TSP.display;

import TabuSearch_TSP.objects.City;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

/**
 * Draws the cities to the screen, as well as a path.
 */
public class WindowTabu extends JFrame {

    private final int WIDTH = 1000;
    private final int HEIGHT = WIDTH / 13*9;
    private final int OFFSET = 40;
    private final int CITY_SIZE = 5;

    private final MainPanel panel;
    private final City[] cities;
    private City[] route;
    private int maxX, maxY;
    private double scaleX, scaleY;
    private String nameFrame;

    /**
     * Construct the WindowTSP and draw the cities to the screen.
     * @param cities    the cities to draw to the screen
     */
    public WindowTabu(City[] cities, String name) {
        nameFrame = name;
        this.cities = cities;
        setScale();
        panel = createPanel();
        add(panel);
        setWindowProperties();
    }

    public void draw (City[] route) {
        this.route = route;
        panel.repaint();
    }

    private MainPanel createPanel () {
        MainPanel panel = new MainPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return panel;
    }

    private void setWindowProperties () {
        setLocation(20, 15);
        setResizable(false);
        pack();
        setTitle("Traveling Salesman Problem " + nameFrame);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Sets the scale for the drawing so that all the cities
     * are drawn inside the window.
     */
    private void setScale () {
        for (City c : cities) {
            if (c.getX() > maxX) {
                maxX = c.getX();
            }
            if (c.getY() > maxY) {
                maxY = c.getY();
            }
        }
        scaleX = ((double)maxX) / ((double)WIDTH- OFFSET);
        scaleY = ((double)maxY) / ((double)HEIGHT- OFFSET);
    }

    /**
     * All the drawing is done here.
     */
    private class MainPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            paintTravelingSalesman((Graphics2D)graphics);
        }

        private void paintTravelingSalesman (Graphics2D graphics) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            paintCityNames(graphics);
            if (route != null) {
                paintSolution(graphics);
            }
            paintCities(graphics);
        }


        private void paintSolution(Graphics2D graphics) {
            graphics.setColor(Color.darkGray);

            for (int i = 1; i < route.length; i++) {
                int x1 = (int) (route[i - 1].getX() / scaleX + OFFSET / 2);
                int y1 = (int) (route[i - 1].getY() / scaleY + OFFSET / 2);
                int x2 = (int) (route[i].getX() / scaleX + OFFSET / 2);
                int y2 = (int) (route[i].getY() / scaleY + OFFSET / 2);

                graphics.drawLine(x1, y1, x2, y2);
            }

            int x1 = (int) (route[0].getX() / scaleX + OFFSET / 2);
            int y1 = (int) (route[0].getY() / scaleY + OFFSET / 2);
            int x2 = (int) (route[route.length - 1].getX() / scaleX + OFFSET / 2);
            int y2 = (int) (route[route.length - 1].getY() / scaleY + OFFSET / 2);

            graphics.drawLine(x1, y1, x2, y2);
        }

        private void paintCities (Graphics2D graphics) {
            graphics.setColor(Color.darkGray);
            for (City c : cities) {
                int x = (int)((c.getX()) / scaleX - CITY_SIZE/2 + OFFSET / 2);
                int y = (int)((c.getY()) / scaleY - CITY_SIZE/2 + OFFSET / 2);
                graphics.fillOval(x, y, CITY_SIZE, CITY_SIZE);
            }
        }

        private void paintCityNames (Graphics2D graphics) {
            graphics.setColor(new Color(185, 69, 69));
            for (City c : cities) {
                int x = (int)((c.getX()) / scaleX - CITY_SIZE/2 + OFFSET/2);
                int y = (int)((c.getY()) / scaleY - CITY_SIZE/2 + OFFSET/2);
                graphics.fillOval(x, y, CITY_SIZE, CITY_SIZE);
                // Set the font size
                Font originalFont = graphics.getFont();
                Font newFont = originalFont.deriveFont(10.0f); // Adjust the font size (10.0f is just an example)
                graphics.setFont(newFont);
                int fontOffset = getFontMetrics(graphics.getFont()).stringWidth(c.getName())/2-2;
                graphics.drawString(c.getName(), x-fontOffset, y-2);
            }
        }
    }
}
