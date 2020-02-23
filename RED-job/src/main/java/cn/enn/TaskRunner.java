package cn.enn;

import cn.enn.config.TasksConfig;
import cn.enn.handler.InitConfigHandler;
import org.eternal.task.Tasks;

/**
 * @Author: tiantao
 * @Date: 2019/11/27 10:39 AM
 * @Version 1.0
 */
public class TaskRunner {

    static String taskcode = "test-task-01";

    static Tasks tasks;

    static String flowname = "sample-flow-handler-test";

    public void init() {
        TasksConfig cfg = new TasksConfig(taskcode);
        cfg.addFlow(flowname, flow -> {
            flow.next(new InitConfigHandler());
        });
        tasks = cfg.getTasks();
    }

    public void accept() {
        try {
            tasks.accept(flowname)
                    // -- content set
                    .withContent("this is content test ")
                    // -- args set
                    .withArgs("{properity: s1,s2,s3,s4}")
                    // -- param set
                    .withParam("action1", "la la 1 ....").withParam("action2", "la la 2....")
                    .withParam("action3", "la la 3....")
                    // then set
                    .then(result -> {
                        System.out.println("--" + result.toString());
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        TaskRunner runner = new TaskRunner();
        runner.init();
        runner.accept();

    }

}
