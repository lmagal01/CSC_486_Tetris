import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private Engine engine; // Subscriber engine
    private JTextArea statusArea; // Status log for messages

    private JMenuBar createMenuBar() {
        // Controller
        Controller controller = new Controller(this);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        // Menus
        JMenu connectionMenu = new JMenu("Connection");
        JMenu testsMenu = new JMenu("Tests");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem startMenuItem = new JMenuItem("Start");
        JMenuItem stopMenuItem = new JMenuItem("Stop");
        JMenuItem stroopTestMenuItem = new JMenuItem("Start Stroop Test");
        JMenuItem aboutMenuItem = new JMenuItem("About");

        // Keyboard shortcuts
        startMenuItem.setAccelerator(KeyStroke.getKeyStroke('F', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));  // Ctrl/Cmd + F
        stopMenuItem.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));   // Ctrl/Cmd + D

        // Action listeners
        startMenuItem.addActionListener(controller);
        stopMenuItem.addActionListener(controller);
        aboutMenuItem.addActionListener(controller);
        stroopTestMenuItem.addActionListener(controller);

        connectionMenu.add(startMenuItem);
        connectionMenu.add(stopMenuItem);

        testsMenu.add(stroopTestMenuItem);

        helpMenu.add(aboutMenuItem);

        menuBar.add(connectionMenu);
        menuBar.add(testsMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }


    public Main() {
        // GUI Construction
        setJMenuBar(createMenuBar());
        ViewPanel centralPanel = new ViewPanel();
        StatusBar viewStatusBar = new StatusBar();

        // Status log setup
        statusArea = new JTextArea(5, 30);
        statusArea.setPreferredSize(new Dimension(800, 100));
        statusArea.setEditable(false);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(viewStatusBar, BorderLayout.NORTH);
        statusPanel.add(new JScrollPane(statusArea), BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(centralPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        Blackboard.getInstance().addPropertyChangeListener(centralPanel);
        Blackboard.getInstance().addPropertyChangeListener(viewStatusBar);
    }

    // Start/Stop subscriber engine
    public void pauseThread(boolean startSubscriber) {
        if (!startSubscriber) { // Start subscriber
            if (engine == null) {
                engine = new Engine();
                Thread engineThread = new Thread(engine);
                engineThread.start();
                logMessage("Subscriber started.");
            } else {
                logMessage("Subscriber is already running.");
            }
        } else { // Stop subscriber
            if (engine != null) {
                engine.stop(true); // Properly stop the subscriber
                engine = null; // Reset engine after stopping
                logMessage("Subscriber stopped.");
            } else {
                logMessage("Subscriber is not running.");
            }
        }
    }

    // Log messages to status area
    public void logMessage(String message) {
        statusArea.append(message + "\n");
    }

    public void about() {
        JOptionPane.showMessageDialog(this,
                "MQTT Subscriber\n\n" +
                        "Connect: Starts the subscriber\n" +
                        "Stop: Stops the subscriber");
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setSize(800, 600);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
        main.setTitle("MQTT Subscriber");
    }
}
