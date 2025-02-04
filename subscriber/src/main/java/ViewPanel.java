import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewPanel extends JPanel implements PropertyChangeListener {

    private JTextArea textArea;
    private JLabel label;

    public ViewPanel() {
        setLayout(new GridLayout(1, 2));
        textArea = new JTextArea();
        label = new JLabel();
        add(textArea);
        add(label);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        textArea.append(evt.getNewValue().toString() + "\n");
        label.setOpaque(true);
//        label.setBackground(new Color(
//                (int) (Math.random() * 256),
//                (int) (Math.random() * 256),
//                (int) (Math.random() * 256)));
        double[] testArray = {-0.5, -0.5, -0.5};
        label.setBackground(zoneColor(zoneFinder(testArray)));
    }

    private int zoneFinder(double[] padArray) {
        int zone = 0;
        double p = padArray[0];
        double a = padArray[1];
        double d = padArray[2];
        if (p > 0 && a > 0 && d > 0)
            zone = 1;
        else if (p > 0 && a > 0 && d < 0)
            zone = 2;
        else if (p > 0 && a < 0 && d > 0)
            zone = 3;
        else if (p > 0 && a < 0 && d < 0)
            zone = 4;
        else if (p < 0 && a > 0 && d > 0)
            zone = 5;
        else if (p < 0 && a > 0 && d < 0)
            zone = 6;
        else if (p < 0 && a < 0 && d > 0)
            zone = 7;
        else if (p < 0 && a < 0 && d < 0)
            zone = 8;
        return zone;
    }

    private Color zoneColor(int zoneNumber) {
        if (zoneNumber == 1)
            return Color.RED;
        else if (zoneNumber == 2)
            return Color.ORANGE;
        else if (zoneNumber == 3)
            return Color.YELLOW;
        else if (zoneNumber == 4)
            return Color.GREEN;
        else if (zoneNumber == 5)
            return Color.BLUE;
        else if (zoneNumber == 6)
            return Color.MAGENTA;
        else if (zoneNumber == 7)
            return Color.PINK;
        else if (zoneNumber == 8)
            return Color.CYAN;
        else
            return Color.BLACK;
    }
}