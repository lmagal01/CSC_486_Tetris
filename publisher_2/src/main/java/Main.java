import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main extends JFrame {

    private Engine engine;
    private String selectedFilePath; // Stores the selected file path
    private JTextArea statusArea; // Status log for messages

    private JMenuBar createMenuBar() {
        // Controller with a reference to Main
        Controller controller = new Controller(this);

        JMenuBar menuBar = new JMenuBar();
        JMenu connectMenu = new JMenu("Connection");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem fileMenuItem = new JMenuItem("Select File");
        JMenuItem startMenuItem = new JMenuItem("Start");
        JMenuItem stopMenuItem = new JMenuItem("Stop");
        JMenuItem aboutMenuItem = new JMenuItem("About");

        // Keyboard shortcuts
        startMenuItem.setAccelerator(KeyStroke.getKeyStroke('F',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        stopMenuItem.setAccelerator(KeyStroke.getKeyStroke('D',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        // Add menu items
        connectMenu.add(fileMenuItem);
        connectMenu.add(startMenuItem);
        connectMenu.add(stopMenuItem);
        helpMenu.add(aboutMenuItem);

        // Add action listeners
        fileMenuItem.addActionListener(controller);
        startMenuItem.addActionListener(controller);
        stopMenuItem.addActionListener(controller);
        aboutMenuItem.addActionListener(controller);

        menuBar.add(connectMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }

    public Main() {
        // Construct GUI
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

    // Select file function
    public void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedFilePath = selectedFile.getAbsolutePath();
            System.out.println("File selected: " + selectedFilePath);
        }
    }

    // Start/Stop publishing
    public void pauseThread(boolean startPublishing) {
        if (!startPublishing) { // Start publishing
            if (selectedFilePath == null) {
                JOptionPane.showMessageDialog(this, "Please select a file first.");
                return;
            }
            if (engine == null) {
                engine = new Engine(selectedFilePath);
                Thread engineThread = new Thread(engine);
                engineThread.start();
                engine.startPublishing();
                logMessage("Publishing started.");
            } else {
                logMessage("Publisher is already running.");
            }
        } else { // Stop publishing
            if (engine != null) {
                engine.stopPublishing();
                engine = null; // Reset engine after stopping
                logMessage("Publishing stopped.");
            } else {
                logMessage("Publisher is not running.");
            }
        }
    }

    // Log messages to status area
    public void logMessage(String message) {
        statusArea.append(message + "\n");
    }

    public void about() {
        JOptionPane.showMessageDialog(this,
                "Connect: Establishes a connection to the broker\n" +
                        "Stop: Ends connection to broker");
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setSize(800, 600);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
        main.setTitle("MQTT Publisher");
    }
}
