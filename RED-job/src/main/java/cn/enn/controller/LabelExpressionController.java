package cn.enn.controller;

import cn.enn.ExpressConfig;
import cn.enn.common.utils.Result;
import cn.enn.dao.RuleMapper;
import cn.enn.service.InitConfig;
import cn.enn.service.PreparDataService;
import cn.enn.service.TransformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("tag")
@Api(tags = "标签表达式处理类")
public class LabelExpressionController {
    @Autowired
    private InitConfig initConfig;
    @Autowired
    private PreparDataService preparDataService;
    @Autowired
    private TransformService transformService;
    @Autowired
    private RuleMapper ruleMapper;

    @PostMapping("queryTagHandler")
    @ApiOperation("表达式计算")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleId",value = "规则ID",dataType = "long",paramType = "query",required = true),
            @ApiImplicitParam(name = "data",value = "json字符串",dataType = "string",paramType = "body",required = true),
    })
    public Result<Map<String,Object>> queryTagHandler(@RequestParam long ruleId,@RequestBody String data){
        Map<String,Object> mapRes;
        Map<String,Object> errMap;

        ExpressConfig expressConfig = new ExpressConfig();
        expressConfig.setRuleId(ruleId);
        //String str="{\"empNo\":\"10000764\",\"costNo\":\"pc12343\",\"costName\":\"人才激发标签项目\",\"startDate\":\"2019-07-23\",\"endDate\":\"2019-12-12\",\"workingHours\":\"10000\"}";
        if ( null == data || 0 == ruleId){return Result.fail(500,"输入项不能为空");}
        expressConfig.setData(data);
        try {
            expressConfig = initConfig.initConfig(expressConfig);
            expressConfig = preparDataService.prepar(expressConfig);
            expressConfig = transformService.transform(expressConfig);
            mapRes = expressConfig.getResMap();
            errMap = expressConfig.getErrData();

            if ( null != errMap){
                Object err = errMap.get("error");
                return Result.fail(500,err.toString());
            }else {
                return Result.rstSuccess(mapRes);
            }

       }catch (Exception e){
           e.printStackTrace();
          return Result.fail(500,"计算失败");
       }

    }

    @GetMapping("queryTopicCorrespondence")
    @ApiOperation("根据topic和appId查询规则ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "topic",value = "主题",dataType = "string",paramType = "query",defaultValue="ennuser_sync_gcost",required = true),
            @ApiImplicitParam(name = "appId",value = "应用id",dataType = "string",paramType = "query"),
    })
    public Result<String> topicCorrespondence(String topic, String appId){
        String ruleId = this.ruleMapper.queryRuleIdByTopic(topic,appId);
        if (null == ruleId){return Result.rstSuccess("没有此"+topic+"主题对应的规则");}
        return Result.rstSuccess(ruleId);
    }
}
