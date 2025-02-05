import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main extends JFrame {

    private Engine engine;
    private String selectedFilePath; // String for selected file path

    private JMenuBar createMenuBar() {
        //Controller with a reference to Main
        Controller controller = new Controller(this);

        JMenuBar menuBar = new JMenuBar();
        JMenu connectMenu = new JMenu("Connection");
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        JMenuItem startMenuItem = new JMenuItem("Start");
        JMenuItem stopMenuItem = new JMenuItem("Stop");
        JMenuItem fileMenuItem = new JMenuItem("Select File");

        //added file option
        connectMenu.add(fileMenuItem);
        connectMenu.add(startMenuItem);
        connectMenu.add(stopMenuItem);
        helpMenu.add(aboutMenuItem);

        //added listener to fileMenu item
        fileMenuItem.addActionListener(controller);
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

    //selectFile function that allows user to choose file
    public void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedFilePath = selectedFile.getAbsolutePath();
            System.out.println("File selected: " + selectedFilePath);
        }
    }

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
                System.out.println("Publishing started.");
            } else {
                System.out.println("Publisher is already running.");
            }
        } else { //Stop publishing
            if (engine != null) {
                engine.stopPublishing();
                engine = null; //Resets engine after stopping
                System.out.println("Publishing stopped.");
            } else {
                System.out.println("Publisher is not running.");
            }
        }
    }

    public void about() {
        JOptionPane.showMessageDialog(this, "MQTT Publisher");
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
