import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class DigitGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private JLabel numberLabel;
    private JTextField inputField;
    private JButton nextButton;
    private int[] numbers;
    private int currentIndex = 0;
    private int currentDigitLength = 4;
    private Timer timer;

    public DigitGUI() {
        setTitle("Digit Game");
        setSize(500, 300);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        numberLabel = new JLabel("Number: ");
        inputField = new JTextField(20);
        nextButton = new JButton("Submit");


        Font largeFont = new Font("Arial", Font.PLAIN, 30);
        numberLabel.setFont(largeFont);

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleNextButton();
            }
        });

        add(numberLabel);
        add(inputField);
        add(nextButton);

        generateNewNumbers();
    }

    private void handleNextButton() {

        String userInput = inputField.getText().trim();


        StringBuilder numberString = new StringBuilder();
        for (int number : numbers) {
            numberString.append(number).append(" ");
        }


        if (userInput.equals(numberString.toString().trim())) {
            System.out.println("Correct! User Input: " + userInput);
            currentIndex = 0;
            inputField.setText("");


            if (currentDigitLength < 8) {
                currentDigitLength++;
                generateNewNumbers();
            } else {
                JOptionPane.showMessageDialog(this, "Game Over!");
                System.exit(0);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect input. Try again!");
        }
    }

    private void generateNewNumbers() {
        Random random = new Random();
        numbers = new int[currentDigitLength];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(10);
        }

        StringBuilder numberString = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            numberString.append(numbers[i]).append(" ");
        }

        numberLabel.setText("Enter the digits: " + numberString.toString());

        timer = new Timer(10000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numberLabel.setText("Enter the digits: ");
                inputField.setEnabled(true);
            }
        });
        timer.setRepeats(false);
        timer.start();

        inputField.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DigitGUI().setVisible(true);
            }
        });
    }
}
