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
    private int remainingTime = 10;

    private ActionLogger actionLogger;
    private long questionStartTime;
    private Timer questionTimer;
    public DigitGUI() {
        setTitle("Digit Game");
        setSize(1000, 600);
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
        actionLogger = new ActionLogger();
    }


    private void startQuestionTimer() {
        questionTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        questionTimer.start();
    }

    private void handleNextButton() {
        String userInput = inputField.getText().trim();

        StringBuilder numberString = new StringBuilder();
        for (int number : numbers) {
            numberString.append(number).append(" ");
        }

        actionLogger.logAction("User entered: " + userInput);
        long timeTaken = System.currentTimeMillis() - questionStartTime;
        actionLogger.logAction("Time taken to answer: " + timeTaken  / 1000.0 + " seconds");

        if (questionTimer != null && questionTimer.isRunning()) {
            questionTimer.stop();
        }

        if (userInput.equals(numberString.toString().trim())) {
            System.out.println("Correct! User Input: " + userInput);
            currentIndex = 0;  // Reset index for the new set
            inputField.setText("");

            if (currentDigitLength < 10) {
                currentDigitLength++;
                generateNewNumbers();
            } else {
                JOptionPane.showMessageDialog(this, "You won!");
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

        numberLabel.setText("Remmeber the digits: " + numberString.toString() + " (Time remaining: 15)");

        questionStartTime = System.currentTimeMillis();
        remainingTime = 10;
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (remainingTime > 0) {
                    remainingTime--;
                    numberLabel.setText("Enter the digits: " + numberString.toString() + " (Time remaining: " + remainingTime + ")");
                } else {
                    numberLabel.setText("Enter the digits: ");
                    inputField.setEnabled(true);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
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
