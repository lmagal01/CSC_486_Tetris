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


    // CircleApp
    private int x = 750, y = 500, diameter = 100;
    private Color circleColor = Color.RED;

    public void CircleApp() {
        JFrame frame = new JFrame("Circle Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1800, 900);
        frame.add(this);
        frame.setVisible(true);
    }

    public void changePosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        repaint();
    }

    public void changeSize(int newDiameter) {
        this.diameter = newDiameter;
        repaint();
    }

    public void changeColor(Color newColor) {
        this.circleColor = newColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(circleColor);
        g.fillOval(x, y, diameter, diameter);
    }

    public static void circleMain(String[] args) {
        CircleApp app = new CircleApp();

        // Example usage
        try {
            Thread.sleep(1000);
            app.changePosition(100, 150);
            Thread.sleep(1000);
            app.changeSize(150);
            Thread.sleep(1000);
            app.changeColor(Color.BLUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
