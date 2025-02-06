import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewPanel extends JPanel implements PropertyChangeListener {

    private JTextArea textArea;
    private JLabel label;

    private JPanel[] zonePanels = new JPanel[8];


    public ViewPanel() {
        setLayout(new GridLayout(3, 4));


        for (int i = 0; i < 8; i++) {
            zonePanels[i] = new JPanel();
            add(zonePanels[i]);
        }
        textArea = new JTextArea();
        label = new JLabel();
        add(textArea);
        add(label);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        textArea.append(evt.getNewValue().toString() + "\n");
        label.setOpaque(true);

        double[] testArray = {0.5, -0.5, -0.5};
        double[] eyeTestLeft = {0.913, 0.354};
        double[] eyeTestRight = {0.885, 0.388};
//        int zone = zoneFinder(testArray);
        int zoneLeft = eyeFinder(eyeTestLeft);
        int zoneRight = eyeFinder(eyeTestRight);
//        zonePanels[zone - 1].setBackground(zoneColor(zone));
        zonePanels[zoneLeft - 1].setBackground(zoneColor(zoneLeft));
        zonePanels[zoneRight - 1].setBackground(zoneColor(zoneRight));
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

    private int eyeFinder(double[] eyePos) {
        double x = eyePos[0];
        double y = eyePos[1];
        if (y >= 0.5) {
            if (x <= 0.25)
                return 1;
            else if (x <= 0.5)
                return 2;
            else if (x <= 0.75)
                return 3;
            else
                return 4;
        }
        else {
            if (x <= 0.25)
                return 5;
            else if (x <= 0.5)
                return 6;
            else if (x <= 0.75)
                return 7;
            else
                return 8;
        }

    }
}