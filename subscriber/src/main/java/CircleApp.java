import javax.swing.*;
import java.awt.*;

public class CircleApp extends JPanel {
    private int x = 750, y = 500, diameter = 100;
    private Color circleColor = Color.RED;

    public CircleApp() {
        JFrame frame = new JFrame("Circle Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1800, 900);
        frame.add(this);
        frame.setVisible(true);
    }

    public void changePosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        repaint();
    }

    public void changeSize(int newDiameter) {
        this.diameter = newDiameter;
        repaint();
    }

    public void changeColor(Color newColor) {
        this.circleColor = newColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(circleColor);
        g.fillOval(x, y, diameter, diameter);
    }



    public static void main(String[] args) {
        CircleApp app = new CircleApp();

        try {
            Thread.sleep(1000);
            app.changePosition(100, 150);
            Thread.sleep(1000);
            app.changeSize(150);
            Thread.sleep(1000);
            app.changeColor(Color.BLUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}