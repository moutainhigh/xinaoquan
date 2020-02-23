package cn.enn.handler;

import cn.enn.service.PreparDataService;
import org.eternal.task.handler.FlowHandler;
import org.eternal.task.handler.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: tiantao
 * @Date: 2019/11/28 10:39 AM
 * @Version 1.0
 */
@Component
public class PreparDataHandler extends FlowHandler {


    @Autowired
    PreparDataService preparDataService;

    @Override
    public String getFlowHandlerName() {
        return "PreparDataHandler";
    }

    @Override
    protected <V, S extends TaskService> S getTaskService(Optional<V> optional) {
        return (S) preparDataService;
    }
}
