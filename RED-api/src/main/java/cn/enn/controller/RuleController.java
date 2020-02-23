package cn.enn.controller;

import cn.enn.base.dto.RuleDTO;
import cn.enn.base.vo.RuleVo;
import cn.enn.common.utils.Constant;
import cn.enn.common.utils.HttpUtil;
import cn.enn.common.utils.Result;
import cn.enn.entity.*;
import cn.enn.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 标签规则 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */

@RequestMapping("/rule")
@RestController
@Api(tags = "标签规则")
@Slf4j
public class RuleController {

    /**
     * 规则业务实现
     */
    private final RuleService ruleService;
    /**
     * 规则详情
     */
    private final RuleExpressionService ruleExpressionService;
    /**
     * 标签条件
     */
    private final LabelConditionService labelConditionService;
    /**
     * 规则数据源
     */
    private final RuleDatasourceService ruleDatasourceService;
    /**
     * 标签对象
     */
    private final LabelObjectService labelObjectService;
    /**
     * 协同业务实现
     */
    private final CollectiveService collectiveService;
    /**
     * 收藏
     */
    private final CollectService collectService;

    @Autowired
    public RuleController(RuleService ruleService,RuleExpressionService ruleExpressionService,CollectiveService collectiveService,CollectService collectService,LabelObjectService labelObjectService,LabelConditionService labelConditionService,RuleDatasourceService ruleDatasourceService){
        this.ruleService=ruleService;
        this.ruleExpressionService=ruleExpressionService;
        this.collectiveService=collectiveService;
        this.collectService=collectService;
        this.labelObjectService=labelObjectService;
        this.labelConditionService=labelConditionService;
        this.ruleDatasourceService=ruleDatasourceService;
    }


    /**
     * 此接口为个人查询和所有查询
     * @param pageNum 当前页
     * @param pageSize 每页大小
     * @param createId 当前用户
     * @param status 草稿
     * @return Result<Page<RuleEntity>>
     */
    @GetMapping("getAllOfRuleAndExpression")
    @ApiOperation("我的查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createId",value = "当前用户ID",dataType = "int",paramType = "query",defaultValue="10043017"),
            @ApiImplicitParam(name = "pageNum",value = "当前页",dataType = "int",paramType = "query",defaultValue="1",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "int",paramType = "query",defaultValue="10",required = true),
            @ApiImplicitParam(name = "searchKeyWord",value = "多个关键字以,隔开例如测,试",dataType="String",paramType = "query",defaultValue="智,测,试"),
            @ApiImplicitParam(name = "status",value = "草稿状态位默认值为1",dataType = "int",paramType = "query")
    })
    @Deprecated
    public Result<Page<RuleEntity>> getAllOfRuleAndExpression(@RequestParam("pageNum") Long pageNum,@RequestParam("pageSize")Long pageSize,Long createId,Integer status,String searchKeyWord){
        Page page = new Page(pageNum,pageSize);
        Page<RuleEntity> entityPage = this.ruleService.getAllOfRuleAndExpression(page,createId,status,searchKeyWord);
        return Result.rstSuccess(entityPage);
    }

    @GetMapping("searchRuleView")
    @ApiOperation("查询搜索方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "当前用户ID",dataType = "String",paramType = "query",defaultValue=""),
            @ApiImplicitParam(name = "pageNum",value = "当前页",dataType = "int",paramType = "query",defaultValue="1",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "int",paramType = "query",defaultValue="10",required = true),
            @ApiImplicitParam(name = "searchKeyWord",value = "多个关键字以,隔开例如测,试",dataType="String",paramType = "query",defaultValue=""),
            @ApiImplicitParam(name = "ruleType",value = "规则类型,1标签规则2数据转换规则",dataType = "int",paramType = "query",defaultValue="1",required = true),
            @ApiImplicitParam(name = "status",value = "草稿状态值为1",dataType = "int",paramType = "query",defaultValue="0",required = true)
    })
    public Result<Page<RuleEntity>> searchRuleView(int pageNum,int pageSize,String searchKeyWord,Integer ruleType,Integer status,String userId){
        Page page = new Page(pageNum,pageSize);
        Page<RuleEntity> viewList = this.ruleService.queryRuleView(page,searchKeyWord,ruleType,status,userId);
        return Result.rstSuccess(viewList);
    }

    /**
     * 收藏或者引用
     * @param ruleId 规则id
     * @return Result<String>
     */
    @GetMapping("collection")
    @ApiOperation("收藏或引用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前用户id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "realName", value = "当前用户真实姓名", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "ruleId", value = "规则id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "status", value = "收藏1,引用2", dataType = "int", paramType = "query", required = true)
    })
    public Result<String> collection(String userId,int status,Long ruleId,String realName){
        if(userId==null||"".equals(userId)){
            return Result.fail(500,"用户未登陆");
        }
        //收藏方法，一不能重复收藏，二，不能收藏自己创建的规则
        if(1 == status){
            try {
                //应该从ruleId和用户id两个条件判断
                QueryWrapper<CollectEntity> queryWrapper = new QueryWrapper();
                queryWrapper.eq("user_name",userId);
                queryWrapper.eq("rule_id",ruleId);
                List<CollectEntity> l = this.collectService.list(queryWrapper);
                RuleEntity ruleEntity = this.ruleService.specificExpression(ruleId);
                if(null!=l&&l.size()>0){
                    return Result.fail(500,"已被收藏");
                }else if(null == ruleEntity){
                    return Result.fail(500,"规则对象不存");
                }else if(userId.equals(ruleEntity.getCreatorUserName())){
                    return Result.fail(500,"不能收藏自己的规则");
                }else{
                    CollectEntity collectEntity =new CollectEntity();
                    collectEntity.setCollectType("COLLECT");
                    collectEntity.setUserName(userId);
                    collectEntity.setRuleId(ruleId);
                    this.collectService.save(collectEntity);

                }
            } catch (Exception e) {
                log.error(Constant.ERROR,e);
                return Result.fail(500,"收藏失败");
            }
        }else{
            //status ==2
             try{
                 //引用几项验证，1、创建人不能是自己，2、不能引用草稿
                 // 1、创建人不能是自己
                 //RuleEntity ruleEntity = this.ruleService.specificExpression(ruleId);
                 RuleEntity ruleEntity = this.ruleService.queryRuleById(ruleId);
                 if(null==ruleEntity){
                     return Result.fail(500,"规则对象不存在");
                 }else {
                     String creatorUserName = ruleEntity.getCreatorUserName();
                     if(creatorUserName!=null&&creatorUserName.equals(userId)){
                         return Result.fail(500,"不能引用自己创建");
                     }else {
                         //定义返回结果,如果是草稿，也不能引用
                         Result result = this.ruleService.quoteRule(ruleId,userId,realName);
                         return result;
                     }
                 }

            } catch (Exception e) {
                log.error(Constant.ERROR,e);
                return Result.fail(500,"引用错误");
            }
        }
        return Result.rstSuccess(Constant.SUCCESS);
    }
    /**
     * 复制：将一条数据生成新的id
     */
    @ApiOperation("复制规则")
    @PostMapping("copyData")
    public  Result<String> copyData(Long ruleId){
        //根据id查询数据，保存一份，再返回给页面，编辑再次保存
        try {
            if(null != ruleId){
                RuleEntity ruleEntity1 = this.ruleService.queryRuleById(ruleId);
                RuleEntity ruleEntity = setRuleEntity(ruleEntity1);
                //保存规则
                this.ruleService.save(ruleEntity);
                //保存协同
                List<CollectiveEntity> collectiveEntity = ruleEntity1.getCollectiveEntityList();
                this.collectiveService.saveBatch(setCollectiveList(collectiveEntity,ruleEntity.getRuleId()));
                //保存标签对象
                List<LabelObjectEntity> labelObjectEntityList = ruleEntity1.getLabelObjectEntities();
                this.labelObjectService.saveBatch(setLabelObjectList(labelObjectEntityList,ruleEntity.getRuleId()));
                //保存规则详情
                List<RuleExpressionEntity> ruleExpressionEntities = ruleEntity1.getRuleExpressionEntity();
                if(null != ruleExpressionEntities) {
                    for (RuleExpressionEntity ruleExpression:ruleExpressionEntities) {
                        RuleExpressionEntity ruleExpressionEntity = setRuleExpressionEntity(ruleExpression, ruleEntity.getRuleId());
                        this.ruleExpressionService.save(ruleExpressionEntity);
                        List<LabelConditionEntity> labelConditionEntityList = ruleExpression.getLabelConditionEntities();
                        if(null != labelConditionEntityList) {
                            //保存标签条件
                            for (LabelConditionEntity labelConditionEntity1:labelConditionEntityList) {
                                LabelConditionEntity labelConditionEntity = new LabelConditionEntity();
                                BeanUtils.copyProperties(labelConditionEntity1, labelConditionEntity);
                                labelConditionEntity.setRuleExpressionId(ruleExpressionEntity.getRuleExpressionId());
                                this.labelConditionService.save(labelConditionEntity);
                            }
                        }

                    }
                }

            }else {
                return Result.fail(400,"ruleId不能为空");
            }
        } catch (Exception e) {
            log.error(Constant.ERROR,e);
            return Result.fail(500,"复制错误");
        }

        return Result.rstSuccess(Constant.SUCCESS);
    }

    /**
     * 根据id删除
     * @param ruleIds 规则id
     * @return Result<String>
     */
    @GetMapping("deleteByIds")
    @ApiOperation("删除规则")
    public Result<String> deleteByIds(@RequestParam(value = "ruleIds") List<Long> ruleIds){
        try {
            if(null == ruleIds){
                return Result.fail(400,"id不能为空");
            }
            List<RuleEntity> ruleEntityList=new ArrayList<>();
            for(int i = 0;i<ruleIds.size();i++){
                RuleEntity ruleEntity = new RuleEntity();
                Long id =ruleIds.get(i);
                ruleEntity.setRuleId(id);
                ruleEntity.setStatus(2);
                ruleEntityList.add(ruleEntity);
            }
            this.ruleService.updateBatchById(ruleEntityList);
            this.collectService.removeByRuleIds(ruleIds);
        } catch (Exception e) {
            log.error(Constant.ERROR,e);
            return Result.fail(500,"删除出错");
        }
        return Result.rstSuccess(Constant.SUCCESS);
    }

    /**
     * 关键字搜索
     * @param searchKeyWord 关键字
     * @return Result<Page<RuleDTO>>
     */
    public Result<Page<RuleDTO>> searchKeyWord(String searchKeyWord,int pageNum,int pageSize,String userId){
        Page page = new Page(pageNum,pageSize);
        Page<RuleDTO> pageList;
        if( null == searchKeyWord){
            return Result.fail(400,"关键字不能为空");
        }else {
            pageList = this.ruleService.searchKeyWord(page,searchKeyWord,userId);
        }

        return Result.rstSuccess(pageList);
    }
    @GetMapping("queryCollectByPage")
    @ApiOperation("我的收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createId",value = "当前用户ID",dataType = "int",paramType = "query",defaultValue="10043017",required = true),
            @ApiImplicitParam(name = "pageNum",value = "当前页",dataType = "int",paramType = "query",defaultValue="1",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "int",paramType = "query",defaultValue="10",required = true)
    })
    @Deprecated
    public Result<Page<RuleDTO>> queryCollectByPage(@RequestParam("createId")Long createId,@RequestParam("pageNum") Long pageNum,@RequestParam("pageSize")Long pageSize){
        Page page = new Page(pageNum,pageSize);

        Page<RuleDTO> ruleDtoList =this.ruleService.getQuestionByCollectPage(page,createId);
        return Result.rstSuccess(ruleDtoList);
    }
    /**
     * 查询具体规则
     * @param ruleId 规则id
     * @return Result<RuleEntity>
     */
    @GetMapping("queryRuleById")
    @ApiOperation("查询具体规则")
    @ApiImplicitParam(name = "ruleId",value = "规则id",dataType = "int",paramType = "query",defaultValue = "33",required = true)
    public Result<RuleEntity> queryRuleById(Long ruleId){
        if(ruleId == null){
            return Result.fail(400,"ruleId不能为空");
        }
        try{
            RuleEntity ruleEntity = this.ruleService.queryRuleById(ruleId);
            return Result.rstSuccess(ruleEntity);
        }catch (Exception e){
            return Result.fail(500,e.getMessage());
        }

    }

    /**
     * 验证赋予的标签名称是否重复
     * @param labelName
     * @return
     */
    @PostMapping("/checkLabelName")
    @ApiOperation(value = "验证赋予的标签名称是否重复")
    public Result<String> checkLabelName(String labelName){
        //参数验证，labelName不能是Null,且不能是空字符串
        if(null==labelName||labelName.replaceAll("\\\\s*","").equals("")||labelName.equals("null")){
            return Result.fail(500,"标签名称不能为空");
        }
        //查询表达式表，
        List<RuleExpressionEntity> list = ruleExpressionService.checkLabelName(labelName);
        if(list.size()>0){
            return Result.fail(500,"已被使用");
        }
        return Result.rstSuccess("可以使用");
    }

    /**
     * 保存规则方法
     * @param ruleVo 规则封装类
     * @return Result<String>
     */
    @PostMapping(value = "saveRule")
    @ApiOperation(value = "保存规则")
    public Result<String> saveRule(@RequestBody RuleVo ruleVo){
        //必要验证：ruleType不能为空
        if(null==ruleVo.getRuleType()){
            return Result.fail(500,"ruleType不能为空");
        }
        if(null==ruleVo.getRuleName()||"".equals(ruleVo.getRuleName().replaceAll("\\s*",""))){
            return Result.fail(500,"规则名称不能为空");
        }

            try {
                ruleService.saveRule(ruleVo);
            } catch (Exception e) {
                log.error("保存错误：", e);
                
                return Result.fail(500, e.getMessage());
            }

        return Result.rstSuccess(Constant.SUCCESS);
    }

    /**
     * 修改规则方法
     * @param ruleVo 规则封装类
     * @return Result<String>
     */
    @PostMapping("updateRuleDetail")
    @ApiOperation(value = "修改规则")
    public Result<String> updateRuleDetail(@RequestBody RuleVo ruleVo){
        Result<String> result = this.updateRuleDetailNew(ruleVo);
        return result;
    }

    /**
     * 修改规则方法new
     * @param ruleVo 规则封装类
     * @return Result<String>
     */
    @PostMapping("updateRuleDetailNew")
    @ApiOperation(value = "修改规则")
    public Result<String> updateRuleDetailNew(@RequestBody RuleVo ruleVo){
        //1、进行一些必要的验证
        //1、协同人的用户名不能为空,2、不能传递相同的协同人，3、已经协同的不能重复填加，
        List<CollectiveEntity> collectiveEntityList = ruleVo.getCollectiveEntityList();
        if(null!=collectiveEntityList&&collectiveEntityList.size()>0){
            //如果传了协同人，则对协同人进行验证，1、协同人的用户名不能为空,2、不能传递相同的协同人，3、已经协同的不能重复填加，
            for (int i = 0; i < collectiveEntityList.size(); i++) {
                collectiveEntityList.get(i).setRuleId(ruleVo.getRuleId());
                if(null==collectiveEntityList.get(i).getUserName()||"".equals(collectiveEntityList.get(i).getUserName())){
                    return Result.fail(500,"参数错误,协同用户id不能为空");
                }
            }
        }
        //2验证ruleType不能为空
        if(ruleVo.getRuleType()==null){
            return Result.fail(500,"ruleType不能为空");
        }
        //3验证规则名称不能为空
        if(null==ruleVo.getRuleName()||"".equals(ruleVo.getRuleName().replaceAll("\\s*",""))){
            return Result.fail(500,"规则名称不能为空");
        }
        try{
            ruleService.updateRuleDetail(ruleVo);
        }catch (Exception e){
            log.error("保存错误：",e);
            
            return Result.fail(500,e.getMessage());
        }
        return Result.rstSuccess(Constant.SUCCESS);
    }

    /**
     * 查询当前组织下的员工
     * @param orgCode 组织编码
     * @return 组织json格式字符串
     */
    @GetMapping("employee")
    @ApiOperation(value = "查询当前组织下的员工")
    @ApiImplicitParam(name = "orgCode",value = "组织代码",dataType = "String",paramType = "query",defaultValue = "10000001",required = true)
    public String employee(@RequestParam("orgCode") String orgCode){

        String jsonStr = HttpUtil.doGet(Constant.EMPTREE + orgCode+Constant.PAGESTR,Constant.APPSECRET);
        String str1 = jsonStr.replace("records","subOrg");
        String str2 = str1.replace("orgName","orgNames");
        return str2.replace("realName","orgName");
    }

    /**
     * 获取根组织树
     * @return String
     */
    @PostMapping("SearchOrgTree")
    @ApiOperation("获取根组织树")
    public String searchOrgTree(){
        //一级组织
        return HttpUtil.doGet(Constant.ORGTREE+Constant.ORGCODE+"",Constant.APPSECRET);
    }

    /**
     * 查询角色
     * @param orgCode 组织编码
     * @param keyWord 关键字
     * @return json字符串
     */
    @GetMapping("getRole")
    @ApiOperation("搜索某组织下的职位/角色-包含子组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页",dataType = "int",paramType = "query",defaultValue="1",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "int",paramType = "query",defaultValue="10",required = true),
            @ApiImplicitParam(name = "orgCode",value = "组织编码",defaultValue = "10000000",paramType = "query",dataType = "string",required = true),
            @ApiImplicitParam(name = "keyWord",value = "关键字",defaultValue = "IT",paramType = "query",dataType = "string")
    })
    public String getRole(Integer pageNum,Integer pageSize,String orgCode,String keyWord){

        String jsonStr = null;
        //第一次点击角色时
        if (null == orgCode && null == keyWord){
            jsonStr = HttpUtil.doGet(Constant.ROLEURL+Constant.ORGCODE+"?page="+pageNum+"&size="+pageSize+"",Constant.APPSECRET);
        }
        if(null == orgCode && null!=keyWord){
            jsonStr = HttpUtil.doGet(Constant.ROLEURL+Constant.ORGCODE +"?keyword="+keyWord +"&page="+pageNum+"&size="+pageSize+"",Constant.APPSECRET);
        }
        if(null != orgCode && null == keyWord){
            jsonStr = HttpUtil.doGet(Constant.ROLEURL+orgCode+"?page="+pageNum+"&size="+pageSize+"",Constant.APPSECRET);
        }
        if(null != orgCode && null != keyWord){
            jsonStr = HttpUtil.doGet(Constant.ROLEURL + orgCode + "?keyword="+keyWord+"&page="+pageNum+"&size="+pageSize+"", Constant.APPSECRET);
        }
        return jsonStr;
    }
    /**
     *
     *
     * 查询子机构
     * @param orgCode 组织编码
     * @return json格式字符串
     */
    @GetMapping("SearchSubOrgTree")
    @ApiOperation("根组织-子组织")
    @ApiImplicitParam(name="orgCode",value = "组织代码",dataType = "int",paramType = "query",required = true)
    public String searchSubOrgTree(Long orgCode){
        String jsonStr;
        if(null != orgCode){
            jsonStr = HttpUtil.doGet(Constant.ORGTREE +orgCode,Constant.APPSECRET);
        }else {
            return "OrgCode不能为空";
        }
        return jsonStr;
    }

    @GetMapping("SearchEmpOrgTre")
    @ApiOperation("查询当前组织人员和机构")
    @ApiImplicitParam(name="orgCode",value = "组织代码",dataType = "int",defaultValue = "10000001",paramType = "query",required = true)
    public String searchEmpOrgTre(String orgCode){
        if(null == orgCode){
            return "OrgCode不能为空";
        }

        //当前组织包含的机构
        String dynamictreeJson = HttpUtil.doGet(Constant.ORGTREE +orgCode+"",Constant.APPSECRET);
        //当前组织的人员
        String employeeJson = HttpUtil.doGet(Constant.EMPTREE+orgCode+Constant.PAGESTR,Constant.APPSECRET);

        return parsJson(dynamictreeJson,employeeJson);
    }
    /**
     * 解析json
     * @param jsonStr 传入json字符串
     */
    private String parsJson(String jsonStr,String emStrJson){
        StringBuilder json = new StringBuilder();
        //把字符串转成jsonObject
        JsonObject jsonObject1 = new JsonParser().parse(jsonStr).getAsJsonObject();
        //把字符串转成jsonObject
        JsonObject jsonObject2 = new JsonParser().parse(emStrJson).getAsJsonObject();

        //这里得到上级机构
        Long orgCode = jsonObject1.get("content").getAsJsonObject().get("orgCode").getAsLong();
        String orgName = jsonObject1.get("content").getAsJsonObject().get("orgName").getAsString();

        //拼装json格式数据
        json.append("{");
        json.append("\"content\": {");
        json.append("\"orgCode\": "+orgCode+",");
        json.append("\"orgName\": \""+orgName+"\",");

        //JsonObject对象转成jsonArray,子机构
        JsonArray jsonArray = jsonObject1.get("content").getAsJsonObject().get("subOrg").getAsJsonArray();
        JsonArray jsonArray2 = jsonObject2.get("content").getAsJsonObject().get("records").getAsJsonArray();

        if(jsonArray2.size()>0){
            json.append("\"records\":[");
            for(int j=0;j<jsonArray2.size();j++){
                JsonElement userId = jsonArray2.get(j).getAsJsonObject().get("userId");
                JsonElement realName = jsonArray2.get(j).getAsJsonObject().get("realName");
                json.append("{");
                json.append("\"userid\":"+userId+",");
                json.append("\"realName\":"+realName+"");
                int a = j+1;
                if(a == jsonArray2.size()){
                    json.append("}");

                }else{
                    json.append("},");

                }
            }
            json.append("],");
        }

        if(jsonArray.size()>0){
            json.append("\"subOrg\":[");
            for(int i=0;i<jsonArray.size();i++){
                //子机构code
                JsonElement orgCodeSub = jsonArray.get(i).getAsJsonObject().get("orgCode");
                JsonElement orgNameSub = jsonArray.get(i).getAsJsonObject().get("orgName");
                json.append("{");
                json.append("\"orgCode\": "+orgCodeSub+",");
                json.append("\"orgName\": "+orgNameSub+"");

                if(i+1 == jsonArray.size()){
                    json.append("}");

                }else{
                    json.append("},");

                }

            }
            json.append("]}}");
        }else {
            json.append("\"subOrg\": null");
        }
        return json.toString();
    }


    /**
     * 设置规则详情
     * @param ruleDto 规则对象
     * @return 规则详情
     */
    private RuleExpressionEntity setRuleExpressionEntity(RuleExpressionEntity ruleDto,Long ruleId){
        RuleExpressionEntity ruleExpressionEntity = new RuleExpressionEntity();
        ruleExpressionEntity.setRuleExpressionId(ruleDto.getRuleExpressionId());
        ruleExpressionEntity.setRuleId(ruleId);
        ruleExpressionEntity.setLabelName(ruleDto.getLabelName());
        ruleExpressionEntity.setLabelShowName(ruleDto.getLabelShowName());
        ruleExpressionEntity.setLabelType(ruleDto.getLabelType());
        ruleExpressionEntity.setRuleDescription(ruleDto.getRuleDescription());
        if(ruleDto.getRuleExpression()!=null){
            ruleExpressionEntity.setRuleExpression(ruleDto.getRuleExpression().replace("×","*").replace("÷","/"));
        }
        ruleExpressionEntity.setRuleExpressionComponent(ruleDto.getRuleExpressionComponent());
        ruleExpressionEntity.setRuleExpressionComponentType(ruleDto.getRuleExpressionComponentType());
        ruleExpressionEntity.setCreatorUserName(ruleDto.getCreatorUserName());
        return ruleExpressionEntity;
    }
    /**
     * 设置规则
     * @param ruleDTO 规则实体
     * @return 规则
     */
    private RuleEntity setRuleEntity(RuleEntity ruleDTO){
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setRuleName(ruleDTO.getRuleName());
        ruleEntity.setRuleBody(ruleDTO.getRuleBody());
        ruleEntity.setStatus(ruleDTO.getStatus());
        ruleEntity.setCreatorUserName(ruleDTO.getCreatorUserName());
        ruleEntity.setUpdateUserName(ruleDTO.getCreatorUserName());
        ruleEntity.setCreatorRealName(ruleDTO.getCreatorRealName());
        //1.新建 2.复制 3.引用
        ruleEntity.setCreateType("2");
        return ruleEntity;
    }

    /**
     * 设置协同
     * @param collectiveEntity 协同
     * @param ruleId 规则ID
     * @return 协同
     */
    private List<CollectiveEntity> setCollectiveList(List<CollectiveEntity> collectiveEntity, Long ruleId) {

        List<CollectiveEntity> list = new ArrayList<>();
        if(null != collectiveEntity) {
            for (CollectiveEntity collectiveEntity2:collectiveEntity) {
                CollectiveEntity collectiveEntity1 = new CollectiveEntity();
                collectiveEntity1.setRuleId(ruleId);
                collectiveEntity1.setUserName(collectiveEntity2.getUserName());
                collectiveEntity1.setRealName(collectiveEntity2.getRealName());
                list.add(collectiveEntity1);
            }
        }
        return list;
    }

    /**
     * 设置标签对象
     * @param labelObjectEntityList 标签对象
     * @param ruleId 规则id
     * @return 标签对象
     */
    private List<LabelObjectEntity> setLabelObjectList(List<LabelObjectEntity> labelObjectEntityList, Long ruleId) {
        List<LabelObjectEntity> list = new ArrayList<>();
        if (null != labelObjectEntityList) {
            for (LabelObjectEntity labelObjectEntity1:labelObjectEntityList ) {
                LabelObjectEntity labelObjectEntity = new LabelObjectEntity();
                labelObjectEntity.setRuleId(ruleId);
                labelObjectEntity.setLabelObjectCreation(labelObjectEntity1.getLabelObjectCreation());
                labelObjectEntity.setLabelObjectLabel(labelObjectEntity1.getLabelObjectLabel());
                labelObjectEntity.setLabelObjectOrg(labelObjectEntity1.getLabelObjectOrg());
                labelObjectEntity.setLabelObjectRole(labelObjectEntity1.getLabelObjectRole());
                list.add(labelObjectEntity);
            }
        }
        return list;
    }


}
