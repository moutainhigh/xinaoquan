package cn.enn.service.impl;

import cn.enn.ExpressConfig;
import cn.enn.service.TaggingService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * 阿里规则引擎标签实现类
 * @author Administrator
 */
@Service("TaggingHandler")
public class TaggingHandlerQlExpressImpl  implements TaggingService {


    /**
     * 解析Context
     *
     */
    public  Map parseContext(){

        final String jsonStrDemo="[{\"price\":5,\"man-hour\":16}]";

        JsonArray jsonArray = new JsonParser().parse(jsonStrDemo).getAsJsonArray();
        Map map = new HashMap();
        if(jsonArray.size()>0){
            for(JsonElement element:jsonArray){
                JsonElement price = element.getAsJsonObject().get("price");
                JsonElement hour = element.getAsJsonObject().get("man-hour");
                map.put("price",price.getAsInt());
                map.put("hour",hour.getAsInt());
            }
        }
       return map;
    }



    @Override
    public  ExpressConfig execute(ExpressConfig expressConfig) throws Exception {
        ExpressRunner runner = new ExpressRunner();
//        runner.addOperatorWithAlias("如果", "if",null);
//        runner.addOperatorWithAlias("那么", "then",null);
//        runner.addOperatorWithAlias("或","or",null);
//        runner.addOperatorWithAlias("且","and",null);
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        Map map = parseContext();

        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            context.put(entry.getKey(),entry.getValue());

        }

        //获取规则数据
        //规则表达式
        String exp1 = "price*hour>50";


        Object r = runner.execute(exp1, context, null, true, false);
        System.out.println(r);
        if(r.equals(true)){
            //员工
            String empNo = "tiantaod";
            //标签名称，就是ruleId
            String tagName = "639";
            //标签值，就是labelType
            String tagValue = "修炼标签-学习类";
            //规则id
            Long ruleId = 123L;
            expressConfig.setJobNumber(UUID.randomUUID().toString());
            expressConfig.setEmpNo(empNo);
            expressConfig.setTagName(tagName);
            expressConfig.setTagValue(tagValue);
            expressConfig.setRuleId(ruleId);

            return expressConfig;
        }else {
            return null;
        }

    }


    public static void main(String[] args) throws Exception{

    }
}
