import org.eclipse.paho.client.mqttv3.*;
import java.awt.Color;


class Engine implements Runnable, MqttCallback {

    private static final String BROKER = "tcp://test.mosquitto.org:1883";
    private static final String TOPIC = "jgs/unity/test";
    private static final String CLIENT_ID = "tetris-subscriber";

    private boolean stop = false;
    private MqttClient client;
    private CircleApp circleApp;

    public Engine(CircleApp circleApp) {
        this.circleApp = circleApp;
        try {
            client = new MqttClient(BROKER, CLIENT_ID);
            client.setCallback(this);
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

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String receivedMsg = new String(message.getPayload());
        System.out.println("Received message: " + receivedMsg);

        String[] parts = receivedMsg.split(",");
        if (parts.length >= 3) {
            try {
                int newX = Integer.parseInt(parts[0].trim());
                int newY = Integer.parseInt(parts[1].trim());
                int newSize = Integer.parseInt(parts[2].trim());
                circleApp.changePosition(newX, newY);
                circleApp.changeSize(newSize);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing message values: " + e.getMessage());
            }
        } else if (parts.length >= 4) {
            try {
                int red = Integer.parseInt(parts[3].trim());
                int green = Integer.parseInt(parts[4].trim());
                int blue = Integer.parseInt(parts[5].trim());
                Color newColor = new Color(red, green, blue);
                circleApp.changeColor(newColor);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing color values: " + e.getMessage());
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
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
}
