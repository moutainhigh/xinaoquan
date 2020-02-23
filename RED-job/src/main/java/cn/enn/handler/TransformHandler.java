package cn.enn.handler;

import cn.enn.service.TransformService;
import org.eternal.task.handler.FlowHandler;
import org.eternal.task.handler.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: tiantao
 * @Date: 2019/11/28 10:43 AM
 * @Version 1.0
 */
@Component
public class TransformHandler extends FlowHandler {


    @Autowired
    TransformService transformService;

    @Override
    public String getFlowHandlerName() {
        return "TransformHandler";
    }

    @Override
    protected <V, S extends TaskService> S getTaskService(Optional<V> optional) {
        return (S) transformService;
    }
}
