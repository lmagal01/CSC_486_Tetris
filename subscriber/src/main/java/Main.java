import javax.swing.*;
import java.awt.*;


public class Main extends JFrame {

    private Engine engine; // Subscriber engine

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
        setJMenuBar(createMenuBar());
        ViewPanel centralPanel = new ViewPanel();
        StatusBar viewStatusBar = new StatusBar();
        setLayout(new BorderLayout());
        add(centralPanel, BorderLayout.CENTER);
        add(viewStatusBar, BorderLayout.SOUTH);
        Blackboard.getInstance().addPropertyChangeListener(centralPanel);
        Blackboard.getInstance().addPropertyChangeListener(viewStatusBar);
    }
//pauseThread function to start new Engine and Thread
    public void pauseThread(boolean startSubscriber) {
        if (!startSubscriber) {
            if (engine == null) {
                engine = new Engine();
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
        Main main = new Main();
        main.setSize(800, 600);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
        main.setTitle("MQTT Subscriber");
    }
}
