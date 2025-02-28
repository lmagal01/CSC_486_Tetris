import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private Engine engine; // Subscriber engine
    private CircleApp circleApp;  // Reference to CircleApp

    private JMenuBar createMenuBar() {
        // Controller
        Controller controller = new Controller(this);

        // Construct menu
        JMenuBar menuBar = new JMenuBar();
        JMenu connectMenu = new JMenu("Connection");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutMenuItem = new JMenuItem("About");
        JMenuItem startMenuItem = new JMenuItem("Start");
        JMenuItem stopMenuItem = new JMenuItem("Stop");

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
        // Initialize CircleApp
        circleApp = new CircleApp();

        // Initialize Engine with CircleApp
        engine = new Engine(circleApp);

        setJMenuBar(createMenuBar());
        setLayout(new BorderLayout());
        add(circleApp, BorderLayout.CENTER);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void pauseThread(boolean startSubscriber) {
        if (!startSubscriber) {
            if (engine == null) {
                engine = new Engine(circleApp);
                Thread engineThread = new Thread(engine);
                engineThread.start();
                System.out.println("Subscriber started.");
            } else {
                System.out.println("Subscriber is already running.");
            }
        } else {
            if (engine != null) {
                engine.stop(true); // Properly stop the subscriber
                engine = null; // Reset engine after stopping
                System.out.println("Subscriber stopped.");
            } else {
                System.out.println("Subscriber is not running.");
            }
        }
    }

    public void about() {
        JOptionPane.showMessageDialog(this, "MQTT Subscriber About");
    }

    public static void main(String[] args) {
        new Main();  // Run the application
    }
}