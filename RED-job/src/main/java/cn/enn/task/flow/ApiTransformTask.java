package cn.enn.task.flow;

import cn.enn.ExpressConfig;
import cn.enn.config.TasksConfig;
import cn.enn.handler.InitConfigHandler;
import cn.enn.handler.PreparDataHandler;
import cn.enn.handler.TransformHandler;
import org.eternal.task.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: tiantao
 * @Date: 2019/11/28 10:26 AM
 * @Version 1.0
 */
@Component
public class ApiTransformTask {

    static String taskcode = "api-transform-task";

    static String flowname = "api-transform-flow";

    static Tasks tasks;

    @Autowired
    InitConfigHandler initConfigHandler;
    @Autowired
    PreparDataHandler preparDataHandler;
    @Autowired
    TransformHandler transformHandler;

    @PostConstruct
    public void init() {
        TasksConfig cfg = new TasksConfig(taskcode);
        cfg.addFlow(flowname, flow -> {
            flow
                    .next(initConfigHandler)
                    .next(preparDataHandler)
                    .next(transformHandler);
        });
        tasks = cfg.getTasks();
    }


    public ExpressConfig accept(ExpressConfig expressConfig) {
        try {
            tasks.accept(flowname)
                    // -- content set
                    .withContent(expressConfig)
                    .then(result -> {
                        System.out.println("--" + result.toString());

                    });
            return expressConfig;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
