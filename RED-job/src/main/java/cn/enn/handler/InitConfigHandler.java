package cn.enn.handler;

import cn.enn.service.impl.InitEnnTask;
import org.eternal.task.handler.FlowHandler;
import org.eternal.task.handler.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: tiantao
 * @Date: 2019/11/27 10:50 AM
 * @Version 1.0
 */
@Component
public class InitConfigHandler extends FlowHandler {


    @Autowired
    InitEnnTask initEnnTask;

    @Override
    public String getFlowHandlerName() {
        return "InitConfigHandler";
    }

    @Override
    protected <V, S extends TaskService> S getTaskService(Optional<V> args) {
        args.ifPresent(s -> {
            System.out.println("-- " + getFlowHandlerName() + "  " + s.toString());
        });

        return (S) initEnnTask;
    }
}
