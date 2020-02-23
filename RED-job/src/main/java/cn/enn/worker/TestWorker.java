package cn.enn.worker;

import cn.enn.mp.kafkaconsumer.worker.MPKafkaWorker;
import cn.enn.task.flow.ConsumerTransformTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: tiantao
 * @Date: 2019/11/26 11:23 AM
 * @Version 1.0
 */
@Component
public class TestWorker implements MPKafkaWorker {


    @Autowired
    ConsumerTransformTask consumerTransformTask;

    /**
     * 当收到kafka消息后的处理逻辑
     *
     * @param message
     */
    @Override
    public void doReceive(String message) {

        System.out.println("work test: " + message);

        consumerTransformTask.accept(742L, message);
    }
}
