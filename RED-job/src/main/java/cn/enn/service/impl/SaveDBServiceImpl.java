package cn.enn.service.impl;

import cn.enn.ExpressConfig;
import cn.enn.dao.EmpLabelMapper;
import cn.enn.entity.EmpLabelEntity;
import cn.enn.service.SaveDBService;
import org.eternal.eventbus.EventResult;
import org.eternal.task.TaskEvent;
import org.eternal.task.handler.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveDBServiceImpl extends TaskService.Impl implements SaveDBService {

    @Autowired
    private EmpLabelMapper empLabelMapper;



    @Override
    protected EventResult execute(TaskEvent event, EventResult result) {
        System.out.println("SaveDBServiceImpl:---------------4");
        ExpressConfig config = event.getContent();
        execute(config);
        return result;
    }

    @Override
    public void execute(ExpressConfig expressConfig) {
        if(null != expressConfig){
            EmpLabelEntity empLabelEntity = new EmpLabelEntity();
            empLabelEntity.setEmpNo(expressConfig.getEmpNo());
            empLabelEntity.setRuleId(expressConfig.getRuleId());
            empLabelEntity.setLabelName(expressConfig.getTagValue());
            empLabelMapper.insert(empLabelEntity);
        }
    }
}