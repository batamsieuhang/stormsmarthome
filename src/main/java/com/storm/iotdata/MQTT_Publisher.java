package com.storm.iotdata;

import java.util.Stack;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTT_Publisher {
    public static void notificationsPublish(Stack<DeviceNotification> data_list) {
        new NotificationPublisher(data_list).start();
    }
}

class NotificationPublisher extends Thread {
    String brokerUrl = "tcp://mqtt-broker:1883";
    String global_topic = "iot-notification";
    Stack<DeviceNotification> data_list;

    public NotificationPublisher(Stack<DeviceNotification> data_list) {
        this.data_list = data_list;
    }

    @Override
    public void run() {
        Long start = System.currentTimeMillis();
        String publisherId = UUID.randomUUID().toString();
        try {
            IMqttClient publisher = new MqttClient(brokerUrl, publisherId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            publisher.connect(options);
            if(publisher.isConnected())
            for(DeviceNotification deviceNotification : data_list){
                byte[] payload = deviceNotification.toString().getBytes();
                MqttMessage msg = new MqttMessage(payload);
                msg.setQos(0);
                msg.setRetained(true);
                publisher.publish(global_topic, msg);
            }
            publisher.close();
            System.out.printf("\n[Notification Publisher] MQTT Publisher took %.2f s\n", (float) (System.currentTimeMillis() - start) / 1000);
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
