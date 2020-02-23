package cn.enn.config;

import cn.enn.mp.kafkaconsumer.config.MPKafkaConsumerConfig;
import cn.enn.mp.kafkaconsumer.worker.ConsumerWorkerRegistry;
import cn.enn.worker.TestWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerConfig implements MPKafkaConsumerConfig {

    @Autowired
    TestWorker testWorker;

    @Override
    public void addConsumerWorker(ConsumerWorkerRegistry consumerWorkerRegistry) {
        consumerWorkerRegistry.addConsumerWorker(testWorker).addTopic("work_record_test_topic");
    }
}
