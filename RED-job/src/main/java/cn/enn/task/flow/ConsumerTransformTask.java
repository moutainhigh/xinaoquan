package cn.enn.task.flow;

import cn.enn.config.TasksConfig;
import cn.enn.handler.InitConfigHandler;
import cn.enn.handler.PreparDataHandler;
import cn.enn.handler.SaveDBHandler;
import cn.enn.handler.TransformHandler;
import org.eternal.task.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: tiantao
 * @Date: 2019/11/28 10:45 AM
 * @Version 1.0
 */
@Component
public class ConsumerTransformTask {

    static String taskcode = "consumer-transform-task";

    static String flowname = "consumer-transform-flow";

    static Tasks tasks;

    @Autowired
    InitConfigHandler initConfigHandler;
    @Autowired
    PreparDataHandler preparDataHandler;
    @Autowired
    TransformHandler transformHandler;
    @Autowired
    SaveDBHandler saveDBHandler;

    @PostConstruct
    public void init() {
        TasksConfig cfg = new TasksConfig(taskcode);
        cfg.addFlow(flowname, flow -> {
            flow
                    .next(initConfigHandler)
                    .next(preparDataHandler)
                    .next(transformHandler)
                    .next(saveDBHandler);
        });
        /**
         * ？？？？？ 这里的task 是否会有多线程问题
         * 如果在accept中没错创建 task 是否有有问题，task会不会回收徐步需要关闭
         */
        tasks = cfg.getTasks();
        System.out.println("task init !");
    }

    public void accept(Long ruleId, String data) {
        try {
            tasks.accept(flowname)
                    // -- content set
                    .withParam("ruleId", ruleId)
                    .withParam("data", data)
                    .then(result -> {
                        System.out.println("--" + result.toString());
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
