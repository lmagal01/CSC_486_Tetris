import java.util.ArrayList;
import java.util.List;

public class ActionLogger {
    private List<String> actions = new ArrayList<>();

    public void logAction(String action) {
        String log_action =  "Action: " + action;
        actions.add(log_action);
        System.out.println(log_action);
    }}