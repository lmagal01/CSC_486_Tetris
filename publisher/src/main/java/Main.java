import javax.swing.*;
import java.awt.*;


public class Main extends JFrame {

    private Engine engine;

    private JTextArea statusArea;

    private JMenuBar createMenuBar() {
        // controller
        Controller controller = new Controller(this);
        // construct
        JMenuBar menuBar = new JMenuBar();
        JMenu connectMenu = new JMenu("Connection");
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        JMenuItem startMenuItem = new JMenuItem("Start");
        JMenuItem stopMenuItem = new JMenuItem("Stop");

        startMenuItem.setAccelerator(KeyStroke.getKeyStroke
                ('F', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        stopMenuItem.setAccelerator(KeyStroke.getKeyStroke
                ('D', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));


        connectMenu.add(startMenuItem);
        connectMenu.add(stopMenuItem);
        helpMenu.add(aboutMenuItem);
        startMenuItem.addActionListener(controller);
        stopMenuItem.addActionListener(controller);
        aboutMenuItem.addActionListener(controller);
        menuBar.add(connectMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }

    public Main() {
        // model
        engine = new Engine();
        Thread modelThread = new Thread(engine);
        modelThread.start();
        // construct
        setJMenuBar(createMenuBar());
        ViewPanel centralPanel = new ViewPanel();
        StatusBar viewStatusBar = new StatusBar();
        statusArea = new JTextArea(5, 30);
        statusArea.setPreferredSize(new Dimension(800, 100));

        statusArea.setEditable(false);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(viewStatusBar, BorderLayout.NORTH);
        statusPanel.add(statusArea, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(centralPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        Blackboard.getInstance().addPropertyChangeListener(centralPanel);
        Blackboard.getInstance().addPropertyChangeListener(viewStatusBar);

    }

    public void logMessage(String message) {
        statusArea.append(message + "\n");
    }

    public void about() {
        // view
        JOptionPane.showMessageDialog(this,
                "Connect: Establishes a connection to the broker "+"\n" +
                "Stop: End connection to broker");
    }

    public void pauseThread(boolean b) {
        try {
            // controller
            engine.pause(b);
            if (b) {
                logMessage("Disconnected from broker.");
            } else {
                logMessage("Connected to broker.");
            }
        } catch(Exception e) {
            logMessage("Error: Unable to connect. Please try again or check connections.");

        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setSize(800, 600);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
        main.setTitle("Publisher");
    }
}