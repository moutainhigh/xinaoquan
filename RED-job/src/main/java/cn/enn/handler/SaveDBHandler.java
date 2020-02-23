package cn.enn.handler;

import cn.enn.service.SaveDBService;
import org.eternal.task.handler.FlowHandler;
import org.eternal.task.handler.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: tiantao
 * @Date: 2019/11/28 10:47 AM
 * @Version 1.0
 */
@Component
public class SaveDBHandler extends FlowHandler {

    @Autowired
    SaveDBService saveDBService;

    @Override
    public String getFlowHandlerName() {
        return "SaveDBHandler";
    }

    @Override
    protected <V, S extends TaskService> S getTaskService(Optional<V> optional) {
        return (S) saveDBService;
    }
}
