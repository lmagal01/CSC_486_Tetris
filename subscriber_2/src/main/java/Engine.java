import org.eclipse.paho.client.mqttv3.*;
import java.io.FileWriter;
import java.io.IOException;

class Engine implements Runnable, MqttCallback {

    private static final String BROKER = "tcp://test.mosquitto.org:1883";
    private static final String TOPIC = "javiergs/tobii/gazedata";
    private static final String CLIENT_ID = "tetris-subscriber";

    private boolean stop = false;
    private boolean wait = true;
    private MqttClient client;
    private String[] mqttBuffer = new String[10000];
    private int counter = 0;

    public Engine() {
        try {
            client = new MqttClient(BROKER, CLIENT_ID);
            client.setCallback(this); // Set this class as the callback handler
            client.connect();
            client.subscribe(TOPIC);
            System.out.println("Connected and subscribed to: " + TOPIC);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!stop) {
                Thread.sleep(1000); // Keep thread going
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost: " + cause.getMessage());
    }

    @Override //prints received messages
    public void messageArrived(String topic, MqttMessage mqttMessage) {

        mqttBuffer[counter] = new String(mqttMessage.getPayload());
        System.out.println("Message stored in mqttBuffer: " + mqttBuffer[counter]);
        String filePath = "C:\\Users\\luism\\CSC486\\CSC_486_Tetris\\subscriber\\src\\main\\java\\EyeTracker.csv";

        try (FileWriter writer = new FileWriter(filePath)) {
            for (String data : mqttBuffer) {
                writer.append(data);
                writer.append(","); // Use a comma as the delimiter
                writer.append("\n"); // Add a newline character to move to the next row

            }

            System.out.println("Array data has been written to " + filePath);

        } catch (
                IOException e) {
            System.err.println("An error occurred while writing to the CSV file: " + e.getMessage());
        }

        counter++;

        System.out.println("Message arrived. Topic: " + topic +
                " Message: " + new String(mqttMessage.getPayload()));

//        if(counter == 1000)
//        {
//            System.out.println("Reached 1000\n");
//            mqttBuffer = new String[1000];
//            counter = 0;
//        }
        String receivedMsg = new String(mqttMessage.getPayload());
        System.out.println("Received message: " + receivedMsg);
        Blackboard.getInstance().addValue("mqttMessage", receivedMsg);
    }








    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        //abstract method not needed
    }

    public void stop(boolean stop) {
        this.stop = stop;
        try {
            if (client.isConnected()) {
                client.disconnect();
                System.out.println("Disconnected from broker.");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void pause(boolean b) {
        this.wait = b;
    }
}