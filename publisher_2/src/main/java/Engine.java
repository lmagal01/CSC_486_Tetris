import org.eclipse.paho.client.mqttv3.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Engine class for MQTT Publisher.
 * Sends messages from a file or generates test messages.
 */
public class Engine implements Runnable {

    private static final String BROKER = "tcp://test.mosquitto.org:1883";
    private static final String TOPIC = "tetris/486";
    private static final String CLIENT_ID = "tetris-publisher";

    private MqttClient client;
    private boolean stop = false;
    private boolean publishing = false;
    private String filePath; // File to read messages from
    private int counter = 0;

    public Engine(String filePath) {
        this.filePath = filePath;
        try {
            client = new MqttClient(BROKER, CLIENT_ID);
            client.connect();
            System.out.println("Connected to broker: " + BROKER);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
            if (filePath != null) {
                reader = new BufferedReader(new FileReader(filePath));
            }

            while (!stop) {
                Thread.sleep(1000);
                if (publishing && client.isConnected()) {
                    String messageContent;

                    if (reader != null) { //Read from file if provided
                        messageContent = reader.readLine();
                        if (messageContent == null) { //EOF
                            System.out.println("EOF reached.");
                            break;
                        }
                    } else { //send test messages if no file
                        messageContent = "Tetris test #" + counter;
                        counter++;
                    }

                    MqttMessage message = new MqttMessage(messageContent.getBytes());
                    message.setQos(2);
                    client.publish(TOPIC, message);
                    System.out.println("Message published: " + messageContent);
                }
            }
        } catch (IOException | MqttException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    System.out.println("File closed.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        stop = true;
        try {
            if (client.isConnected()) {
                client.disconnect();
                System.out.println("MQTT Publisher Disconnected.");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void startPublishing() {
        publishing = true;
    }

    public void stopPublishing() {
        publishing = false;
    }

    public void pause(boolean b) {
    }
}