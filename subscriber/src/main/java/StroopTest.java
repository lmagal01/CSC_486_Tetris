import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class StroopTest extends JFrame implements ActionListener {

    private final String[] colors = {"RED", "GREEN", "BLUE", "YELLOW"};
    private final Color[] colorValues = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private JLabel wordLabel;
    private JButton[] buttons = new JButton[4];
    private String correctColor; // for answer by user

    public StroopTest() {
        setTitle("Stroop Test");
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 48));
        add(wordLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(colors[i]);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        generateNewChallenge();
        setVisible(true);
    }

    private void generateNewChallenge() {
        Random rand = new Random();
        int textIndex = rand.nextInt(colors.length);
        int colorIndex = rand.nextInt(colors.length);

        wordLabel.setText(colors[textIndex]);
        wordLabel.setForeground(colorValues[colorIndex]);
        correctColor = colors[colorIndex];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        String chosenColor = clicked.getText();

        if (chosenColor.equals(correctColor)) {
            JOptionPane.showMessageDialog(this, "Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect!", "Result", JOptionPane.ERROR_MESSAGE);
        }
        generateNewChallenge();
    }
}
