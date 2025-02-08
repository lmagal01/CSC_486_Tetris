import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    private Main viewMain;

    public Controller(Main viewMain) {
        this.viewMain = viewMain;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getActionCommand().equals("About")) {
                viewMain.about();
            }
            else if (e.getActionCommand().equals("Select File")) {
                System.out.println("Selecting file...");
                viewMain.selectFile();
            }
            else if (e.getActionCommand().equals("Start")) {
                viewMain.logMessage("Attempting to connect...");
                System.out.println("Start");
                viewMain.pauseThread(false);
            } else if (e.getActionCommand().equals("Stop")) {
                System.out.println("Stop");
                viewMain.logMessage("Disconnecting...");
                viewMain.pauseThread(true);
            }
        } catch (Exception ex){
            viewMain.logMessage("Error: " + getErrorMessage(ex));
        }
        }
    private String getErrorMessage(Exception e) {
        if (e.getMessage().contains("timeout")) {
            return "Connection timed out. Please try again.";
        } else {
            return "An unexpected error occurred. Check status or restart application.";
        }}
}