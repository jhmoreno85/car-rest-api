package com.example.jhuerta.service.ks;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

@Slf4j
public class KafkaStreamService implements StreamService {

    private static final String TOPIC_NAME = "cars-topic";
    private static final String KAFKA_CLUSTER_ENV_VAR_NAME = "KAFKA_CLUSTER";
    private final KafkaProducer<String, String> kafkaProducer;

    public KafkaStreamService() {
        log.info("Kafka Producer running in thread {}", Thread.currentThread().getName());
        Properties kafkaProps = new Properties();

        String defaultClusterValue = "localhost:9092";
        String kafkaCluster = System.getProperty(KAFKA_CLUSTER_ENV_VAR_NAME, defaultClusterValue);
        log.info("Kafka cluster {}", kafkaCluster);

        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaCluster);
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put(ProducerConfig.ACKS_CONFIG, "0");

        this.kafkaProducer = new KafkaProducer<>(kafkaProps);
    }

    @Override
    public void publish(String message) {
        kafkaProducer.send(new ProducerRecord<>(TOPIC_NAME, message), (rm, ex) -> {
            if (null != ex) {
                log.error("Error sending message with key", ex);
            } else {
                log.error("Message has been published successful");
            }
        });
    }
}
