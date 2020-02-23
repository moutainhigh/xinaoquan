package cn.enn.service.impl;

import cn.enn.ExpressConfig;
import cn.enn.config.JointOperator;
import cn.enn.service.TransformService;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eternal.eventbus.EventResult;
import org.eternal.task.TaskEvent;
import org.eternal.task.handler.TaskService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: tiantao
 * @Date: 2019/11/28 1:48 PM
 * @Version 1.0
 */
@Component
@Slf4j
public class TransformServiceImpl extends TaskService.Impl implements TransformService {
    private final static Log logger = LogFactory.getLog(TransformServiceImpl.class);

    @Override
    protected EventResult execute(TaskEvent event, EventResult result) {

        System.out.println("TransformServiceImpl:---------------3");

        ExpressConfig config = event.getContent();
        transform(config);
        return result;
    }

    @Override
    public ExpressConfig transform(ExpressConfig expressConfig){

        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        Map<String,Object> mapRes = new HashMap<>(16);
        Map errorMap = new HashMap();
        try {
            runner.addOperatorWithAlias("如果", "if", null);
            runner.addOperatorWithAlias("那么", "then", null);
            runner.addOperatorWithAlias("或", "or", null);
            runner.addOperatorWithAlias("且", "and", null);
            runner.addOperatorWithAlias("否则","else",null);
            runner.addOperator("join",new JointOperator());
            List contextValue = expressConfig.getContextValue();
            for(int i=0;i<contextValue.size();i++){
                Map<String,Object> map =(Map) contextValue.get(i);
                for(Map.Entry<String,Object> entry:map.entrySet()){
                    context.put(entry.getKey(),entry.getValue());
                    log.debug("context: "+entry.getKey()+":"+entry.getValue());
                }
            }
            List exp = expressConfig.getExpressList();
            List labelName = expressConfig.getLabelName();
            for (int j=0;j<exp.size();j++){
                String exps = exp.get(j).toString();
                String labelName2 = labelName.get(j).toString();
                log.debug(labelName2+"表达式："+exps);
                Object r = runner.execute(exps, context, null, true, false,logger);
                if(null == r){log.info("无此表达式："+exps+"的变量计算值");
                    errorMap.put("error","context无此表达式：["+exps+"]的计算值");
                    expressConfig.setErrData(errorMap);
                }
                log.debug("计算结果："+r);
                mapRes.put(labelName2,r);
            }
            log.debug("最终MAP："+mapRes.toString());
            expressConfig.setResMap(mapRes);

        }catch (Exception e){
            log.error("计算错误",e);
        }
        return expressConfig;
    }
}
