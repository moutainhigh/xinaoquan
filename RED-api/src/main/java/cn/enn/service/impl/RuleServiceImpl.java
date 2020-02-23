package cn.enn.service.impl;

import cn.enn.base.dto.RuleDTO;
import cn.enn.base.utils.RuleUtil;
import cn.enn.base.vo.RuleDatasourceVO;
import cn.enn.base.vo.RuleVo;
import cn.enn.common.utils.Constant;
import cn.enn.common.utils.FullHalfAngleConverter;
import cn.enn.common.utils.Result;
import cn.enn.dao.CollectiveMapper;
import cn.enn.dao.LabelConditionMapper;
import cn.enn.dao.LabelObjectMapper;
import cn.enn.dao.RuleDatasourceMapper;
import cn.enn.dao.RuleExpressionMapper;
import cn.enn.dao.RuleMapper;
import cn.enn.entity.CollectiveEntity;
import cn.enn.entity.LabelConditionEntity;
import cn.enn.entity.LabelObjectEntity;
import cn.enn.entity.RuleDatasourceEntity;
import cn.enn.entity.RuleEntity;
import cn.enn.entity.RuleExpressionEntity;
import cn.enn.service.RuleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 标签规则 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Service("RuleService")
public class RuleServiceImpl extends ServiceImpl<RuleMapper, RuleEntity> implements RuleService {
    @Autowired
    private CollectiveMapper collectiveMapper;

    @Autowired
    private LabelObjectMapper labelObjectMapper;

    @Autowired
    private RuleExpressionMapper ruleExpressionMapper;

    @Autowired
    private LabelConditionMapper labelConditionMapper;

    @Autowired
    private RuleDatasourceMapper ruleDatasourceMapper;


    /**
     * @param newKeyWord 关键词
     * @param status     状态
     * @return queryRuleViewCount
     */
    @Override
    public Long queryRuleViewCount(String newKeyWord, int status, String userId) {

        return this.baseMapper.queryRuleViewCount(halfAngleString(newKeyWord), status, userId);
    }

    /**
     * 搜索方法
     *
     * @param page          分页参数
     * @param searchKeyWord 关键词
     * @return Page<RuleEntity>
     */
    @Override
    public Page<RuleEntity> queryRuleView(Page page, String searchKeyWord, int ruleType, int status, String userId) {

        return this.baseMapper.queryRuleView(page, halfAngleString(searchKeyWord), ruleType, status, userId);
    }

    /**
     * 全角半角转换
     *
     * @param searchKeyWord
     * @return
     */
    private String halfAngleString(String searchKeyWord) {
        String keyWord = null;
        if (searchKeyWord != null) {
            //全角转换为半角
            String halfAngleString = FullHalfAngleConverter.ToDBC(searchKeyWord.trim());
            String[] newStr = halfAngleString.split(",");
            StringBuffer key = new StringBuffer();
            for (int i = 0; i < newStr.length; i++) {
                if (i + 1 == newStr.length) {
                    key.append(newStr[i].trim());
                } else {
                    key.append(newStr[i].trim() + "|");
                }
            }
            keyWord = key.toString().trim();
        }
        return keyWord;
    }

    /**
     * 级联查询规则和规则详情
     *
     * @param page     分页
     * @param createId 当前用户
     * @param status   我的、草稿、
     * @return Page<RuleEntity>
     */
    @Override
    public Page<RuleEntity> getAllOfRuleAndExpression(Page page, Long createId, Integer status, String searchKeyWord) {

        return this.baseMapper.getAllOfRuleAndExpression(page, createId, status, halfAngleString(searchKeyWord));
    }

    /**
     * 查询我的规则/我的草稿
     *
     * @param page
     * @param createId
     * @param status
     * @return
     */
    @Override
    public Page<RuleDTO> getQuestionByPagenByPage(Page page, Long createId, int status) {
        return this.baseMapper.getQuestionByPage(page, createId, status);
    }

    /**
     * 查询我的收藏
     *
     * @param page
     * @param createId
     * @return
     */
    @Override
    public Page<RuleDTO> getQuestionByCollectPage(Page page, Long createId) {
        return this.baseMapper.getCollectByPage(page, createId);
    }

    /**
     * 关键字搜索
     *
     * @param searchKeyWord
     * @param page
     * @return
     */
    @Override
    public Page<RuleDTO> searchKeyWord(Page page, String searchKeyWord, String userId) {

        String[] newStr = searchKeyWord.split(",");
        int n = newStr.length;
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < n; i++) {
            if (i + 1 == newStr.length) {
                key.append(newStr[i]);
            } else {
                key.append(newStr[i] + "|");
            }


        }
        String newKeyWord = key.toString();
        return this.baseMapper.searchKeyWord(page, newKeyWord, userId);
    }


    @Override
    public Page<RuleDTO> queryRuleExpression(Page page, Long createId, int status) {
        return this.baseMapper.queryRuleAndExpression(page, createId, status);
    }

    /**
     * 查询具体规则
     *
     * @param ruleId
     * @return
     */
    @Override
    public RuleEntity specificExpression(Long ruleId) {
        return this.baseMapper.specificExpression(ruleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRule(RuleVo ruleVo) throws Exception{

        //1、保存规则
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setRuleId(ruleVo.getRuleId());
        ruleEntity.setRuleName(ruleVo.getRuleName());
        ruleEntity.setRuleBody(ruleVo.getRuleBody());
        ruleEntity.setRuleType(ruleVo.getRuleType());
        ruleEntity.setCreatorUserName(ruleVo.getUserName());
        ruleEntity.setCreatorRealName(ruleVo.getRealName());
        //状态: 0.正常,1.草稿 ,2.删除
        ruleEntity.setStatus(ruleVo.getStatus());
        Long ruleId = ruleEntity.getRuleId();
        if(null == ruleId ||  0==ruleId){
            ruleEntity.setCreateType("1");
        }else{
            throw new Exception("新增不需要id");
        }
        this.baseMapper.insert(ruleEntity);

        //2、保存协同
        List<CollectiveEntity> collectiveEntityList = ruleVo.getCollectiveEntityList();
        if(null != collectiveEntityList && collectiveEntityList.size()>0) {
            for(CollectiveEntity collectiveEntity:collectiveEntityList){
                collectiveEntity.setRuleId(ruleEntity.getRuleId());
                this.collectiveMapper.insert(collectiveEntity);
            }
        }

        //3、保存标签对象
        List<LabelObjectEntity> labelObjectEntityList = ruleVo.getLabelObjectEntityList();
        if(null != labelObjectEntityList && labelObjectEntityList.size()>0) {
            for(LabelObjectEntity labelObjectEntity : labelObjectEntityList){
                labelObjectEntity.setRuleId(ruleEntity.getRuleId());
                this.labelObjectMapper.insert(labelObjectEntity);
                //往rule_datasource里保存一份，实现兼容新版本，保存创值场景id,缺少表名
                RuleDatasourceEntity ruleDatasourceEntity = new RuleDatasourceEntity();
                ruleDatasourceEntity.setRuleId(ruleEntity.getRuleId());
                ruleDatasourceEntity.setDatasourceId(Long.parseLong(labelObjectEntity.getLabelObjectCreationId()));
                ruleDatasourceEntity.setDatasourceName(labelObjectEntity.getLabelObjectCreationEn());
                ruleDatasourceEntity.setDatasourceShowName(labelObjectEntity.getLabelObjectCreation());
                this.ruleDatasourceMapper.insert(ruleDatasourceEntity);
            }
        }

        //4、处理规则表达式，如果是标签规则，走原程序，如果数据转换规则，改变一下保存方法
        if(1==ruleVo.getRuleType()||0==ruleVo.getRuleType()||null==ruleVo.getRuleType()){
            List<RuleExpressionEntity> ruleExpressionEntities = ruleVo.getRuleExpressionEntities();
            if (null != ruleExpressionEntities && ruleExpressionEntities.size()>0) {
                for (RuleExpressionEntity ruleExpressionEntity:ruleExpressionEntities) {
                    //设置标签名称类型及表达式
                    RuleExpressionEntity ruleExpressionEntity1 = new RuleExpressionEntity();
                    ruleExpressionEntity1.setRuleExpressionId(ruleExpressionEntity.getRuleExpressionId());
                    ruleExpressionEntity1.setRuleId(ruleEntity.getRuleId());
                    ruleExpressionEntity1.setLabelName(ruleExpressionEntity.getLabelName());
                    ruleExpressionEntity1.setLabelShowName(ruleExpressionEntity.getLabelShowName());
                    ruleExpressionEntity1.setLabelType(ruleExpressionEntity.getLabelType());
                    ruleExpressionEntity1.setRuleDescription(ruleExpressionEntity.getRuleDescription());
                    ruleExpressionEntity1.setRuleExpression(ruleExpressionEntity.getRuleExpression());
                    ruleExpressionEntity1.setRuleExpressionComponent(ruleExpressionEntity.getRuleExpressionComponent());
                    ruleExpressionEntity1.setRuleExpressionComponentType(ruleExpressionEntity.getRuleExpressionComponentType());
                    ruleExpressionEntity1.setCreatorUserName(ruleExpressionEntity.getCreatorUserName());
                    this.ruleExpressionMapper.insert(ruleExpressionEntity1);
                    //保存标签条件
                    List<LabelConditionEntity> labelConditionEntities = ruleExpressionEntity.getLabelConditionEntities();
                    if (null != labelConditionEntities && labelConditionEntities.size()>0) {
                        for (LabelConditionEntity labelConditionEntity:labelConditionEntities) {
                            labelConditionEntity.setRuleExpressionId(ruleExpressionEntity1.getRuleExpressionId());
                            labelConditionEntity.setRuleId(ruleEntity.getRuleId());
                            this.labelConditionMapper.insert(labelConditionEntity);
                        }
                    }
                }
            }
        }

        //5、数据转换保存方法，没有标签条件，只有规则数据源
        if(2==ruleVo.getRuleType()) {
            //规则表达式
            List<RuleExpressionEntity> ruleExpressionEntities = ruleVo.getRuleExpressionEntities();
            if (null != ruleExpressionEntities && ruleExpressionEntities.size() > 0) {
                for (RuleExpressionEntity ruleExpressionEntity : ruleExpressionEntities) {
                    //设置标签名称类型及表达式
                    RuleExpressionEntity ruleExpressionEntity1 = new RuleExpressionEntity();
                    //不需要id
                    //ruleExpressionEntity1.setRuleExpressionId(ruleExpressionEntity.getRuleExpressionId());
                    ruleExpressionEntity1.setRuleId(ruleEntity.getRuleId());
                    ruleExpressionEntity1.setLabelName(ruleExpressionEntity.getLabelName());
                    ruleExpressionEntity1.setLabelShowName(ruleExpressionEntity.getLabelShowName());
                    ruleExpressionEntity1.setLabelType(ruleExpressionEntity.getLabelType());
                    ruleExpressionEntity1.setRuleDescription(ruleExpressionEntity.getRuleDescription());
                    ruleExpressionEntity1.setRuleExpressionComponent(ruleExpressionEntity.getRuleExpressionComponent());
                    ruleExpressionEntity1.setRuleExpressionComponentType(ruleExpressionEntity.getRuleExpressionComponentType());
                    ruleExpressionEntity1.setCreatorUserName(ruleExpressionEntity.getCreatorUserName());
                    //处理表达式，
                    String ruleExpressionComponent = ruleExpressionEntity1.getRuleExpressionComponent();
                    if (ruleExpressionComponent != null) {
                        int start = ruleExpressionComponent.indexOf("[");
                        int end = ruleExpressionComponent.indexOf("]");
                        if (start > -1 && end > -1) {
                            //去掉中括号，引号和逗号，得到表达式原型
                            String ss = ruleExpressionComponent.substring(start + 1, end);
                            String newStr = ss.replace("\"", "").replace(",", "");
                            //对表达式进行验证，处理理
                            newStr = RuleUtil.resolveRuleStr(newStr);
                            ruleExpressionEntity1.setRuleExpression(newStr);
                        }
                    }else{
                        throw new Exception("表达式参数不正确");
                    }
                    this.ruleExpressionMapper.insert(ruleExpressionEntity1);
                }
            }
            //保存规则数据源
            List<RuleDatasourceVO> ruleDatasourceVOList = ruleVo.getRuleDatasourceVOList();
            if (null != ruleDatasourceVOList && ruleDatasourceVOList.size() > 0) {
                for (RuleDatasourceVO ruleDatasourceVO : ruleDatasourceVOList) {
                    RuleDatasourceEntity ruleDatasourceEntity = new RuleDatasourceEntity();
                    if ("slave".equals(ruleDatasourceVO.getLogic())) {
                        //验证连接符
                        if (ruleDatasourceVO.getSymbol() == null || "".equals(ruleDatasourceVO.getSymbol())) {
                            throw new Exception("变量(symbol):关键词连接符不能为空");
                        }
                        if (ruleDatasourceVO.getMasterField() != null && ruleDatasourceVO.getSlaveField() != null) {
                            ruleDatasourceVO.setRelationship(ruleDatasourceVO.getMasterField().replace("#", ".") + ruleDatasourceVO.getSymbol() + ruleDatasourceVO.getSlaveField().replace("#", "."));
                        }
                        if (ruleDatasourceVO.getMasterFieldShow() != null && ruleDatasourceVO.getSlaveFieldShow() != null) {
                            ruleDatasourceVO.setRelationshipShow(ruleDatasourceVO.getMasterFieldShow().replace("#", ".") + ruleDatasourceVO.getSymbol() + ruleDatasourceVO.getSlaveFieldShow().replace("#", "."));
                        }
                    }
                    BeanUtils.copyProperties(ruleDatasourceVO, ruleDatasourceEntity);
                    //还有一个字段，再处理一次吧
                    if ("slave".equals(ruleDatasourceVO.getLogic())) {
                        if (ruleDatasourceVO.getSlaveField() != null) {
                            ruleDatasourceEntity.setRelationfield(ruleDatasourceVO.getSlaveField().replace("#", "."));
                        } else {
                            throw new Exception("参数不正确：子表slaveField不能为空");
                        }
                    }
                    ruleDatasourceEntity.setRuleId(ruleEntity.getRuleId());
                    this.ruleDatasourceMapper.insert(ruleDatasourceEntity);
                }
            }
        }
    }

    /**
     * 根据规则内容
     *
     * @param ruleVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRuleDetail(RuleVo ruleVo) throws Exception{

        //规则实体
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setRuleId(ruleVo.getRuleId());
        ruleEntity.setRuleName(ruleVo.getRuleName());
        ruleEntity.setRuleBody(ruleVo.getRuleBody());
        ruleEntity.setRuleType(ruleVo.getRuleType());
        ruleEntity.setStatus(ruleVo.getStatus());
        ruleEntity.setCreateType(ruleVo.getCreateType());
        ruleEntity.setCreatorUserName(ruleVo.getUserName());
        ruleEntity.setCreatorRealName(ruleVo.getRealName());

        //第一步：先根据规则id(ruleId)删除子类，再执行新增接口将信息增加
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rule_id", ruleEntity.getRuleId());
        //标签规则条件删除
        this.labelConditionMapper.deleteByMap(map);
        //标签规则详情删除
        this.ruleExpressionMapper.deleteByMap(map);
        //协同人删除
        this.collectiveMapper.deleteByMap(map);
        //标签对象删除
        this.labelObjectMapper.deleteByMap(map);
        //规则数据源删除
        this.ruleDatasourceMapper.deleteByMap(map);

        //第二步：开始执行保存方法
        //保存协同
        List<CollectiveEntity> collectiveEntityList = ruleVo.getCollectiveEntityList();
        if(null!=collectiveEntityList){
            //需要去掉collectiveEntityList里重复数据
            for(int m=0;m<collectiveEntityList.size()-1;m++){
                for(int l=collectiveEntityList.size()-1;l>m;l--)  {
                    if  (collectiveEntityList.get(l).getUserName().replaceAll("\\\\s*","").equals(collectiveEntityList.get(m).getUserName().replaceAll("\\\\s*","")))  {
                        collectiveEntityList.remove(l);
                    }
                }
            }
            for (int i = 0; i < collectiveEntityList.size(); i++) {
                collectiveEntityList.get(i).setRuleId(ruleVo.getRuleId());
                //协同人不保存自己，是前端传过来的
                //collectiveEntityList.get(i).setUserName(ruleVo.getUserName());
                //collectiveEntityList.get(i).setRealName(ruleVo.getRealName());
                this.collectiveMapper.insert(collectiveEntityList.get(i));
            }
        }
        //保存标签对象
        List<LabelObjectEntity> labelObjectEntityList = ruleVo.getLabelObjectEntityList();
        if(null!=labelObjectEntityList&&labelObjectEntityList.size()>0){
            for (int k = 0; k < labelObjectEntityList.size(); k++) {
                labelObjectEntityList.get(k).setRuleId(ruleVo.getRuleId());
                this.labelObjectMapper.insert(labelObjectEntityList.get(k));

                //往rule_datasource里保存一份，实现兼容新版本
                RuleDatasourceEntity ruleDatasourceEntity = new RuleDatasourceEntity();
                ruleDatasourceEntity.setRuleId(ruleEntity.getRuleId());
                ruleDatasourceEntity.setDatasourceId(Long.parseLong(labelObjectEntityList.get(k).getLabelObjectCreationId()));
                ruleDatasourceEntity.setDatasourceName(labelObjectEntityList.get(k).getLabelObjectCreationEn());
                ruleDatasourceEntity.setDatasourceShowName(labelObjectEntityList.get(k).getLabelObjectCreation());
                this.ruleDatasourceMapper.insert(ruleDatasourceEntity);
            }
        }
        //如果是标签规则，走原程序，如果数据转换规则，改变一下保存方法
        if(1==ruleVo.getRuleType()||0==ruleVo.getRuleType()||null==ruleVo.getRuleType()){
            List<RuleExpressionEntity> ruleExpressionEntities = ruleVo.getRuleExpressionEntities();
            if (ruleExpressionEntities.size() > 0) {
                for (int j = 0; j < ruleExpressionEntities.size(); j++) {
                    //设置标签名称类型及表达式
                    RuleExpressionEntity ruleExpressionEntity1 = new RuleExpressionEntity();
                    ruleExpressionEntity1.setRuleId(ruleVo.getRuleId());
                    ruleExpressionEntity1.setLabelName(ruleExpressionEntities.get(j).getLabelName());
                    ruleExpressionEntity1.setLabelShowName(ruleExpressionEntities.get(j).getLabelShowName());
                    ruleExpressionEntity1.setLabelType(ruleExpressionEntities.get(j).getLabelType());
                    ruleExpressionEntity1.setRuleDescription(ruleExpressionEntities.get(j).getRuleDescription());
                    if(ruleExpressionEntities.get(j).getRuleExpression()!=null){
                        ruleExpressionEntity1.setRuleExpression(ruleExpressionEntities.get(j).getRuleExpression().replace("×","*").replace("÷","/"));
                    }
                    ruleExpressionEntity1.setCreatorUserName(ruleVo.getUserName());
                    this.ruleExpressionMapper.insert(ruleExpressionEntity1);

                    //保存标签表达式条件
                    List<LabelConditionEntity> labelConditionEntities = ruleExpressionEntities.get(j).getLabelConditionEntities();
                    if (labelConditionEntities.size() > 0) {
                        for (int s = 0; s < labelConditionEntities.size(); s++) {
                            LabelConditionEntity labelConditionEntity = labelConditionEntities.get(s);
                            labelConditionEntity.setRuleExpressionId(ruleExpressionEntity1.getRuleExpressionId());
                            labelConditionEntity.setRuleId(ruleVo.getRuleId());
                            this.labelConditionMapper.insert(labelConditionEntity);
                        }
                    }
                }
            }
        }
        //数据转换保存方法，没有标签条件，只有规则数据源
        if(2==ruleVo.getRuleType()){
            List<RuleExpressionEntity> ruleExpressionEntities = ruleVo.getRuleExpressionEntities();
            if (ruleExpressionEntities.size() > 0) {
                for (int j = 0; j < ruleExpressionEntities.size(); j++) {
                    //设置标签名称类型及表达式
                    RuleExpressionEntity ruleExpressionEntity1 = new RuleExpressionEntity();
                    ruleExpressionEntity1.setRuleId(ruleVo.getRuleId());
                    ruleExpressionEntity1.setLabelName(ruleExpressionEntities.get(j).getLabelName());
                    ruleExpressionEntity1.setLabelShowName(ruleExpressionEntities.get(j).getLabelShowName());
                    ruleExpressionEntity1.setLabelType(ruleExpressionEntities.get(j).getLabelType());
                    ruleExpressionEntity1.setRuleDescription(ruleExpressionEntities.get(j).getRuleDescription());
                    ruleExpressionEntity1.setRuleExpression(ruleExpressionEntities.get(j).getRuleExpression());
                    ruleExpressionEntity1.setRuleExpressionComponent(ruleExpressionEntities.get(j).getRuleExpressionComponent());
                    ruleExpressionEntity1.setRuleExpressionComponentType(ruleExpressionEntities.get(j).getRuleExpressionComponentType());
                    ruleExpressionEntity1.setCreatorUserName(ruleVo.getUserName());
                    //处理表达式，
                    String ruleExpressionComponent = ruleExpressionEntity1.getRuleExpressionComponent();
                    if(ruleExpressionComponent!=null){
                        int start = ruleExpressionComponent.indexOf("[");
                        int end = ruleExpressionComponent.indexOf("]");
                        if(start>-1&&end>-1){
                            //去掉中括号和双引号逗号，得到表达式原型
                            String ss = ruleExpressionComponent.substring(start+1,end);
                            String newStr = ss.replace("\"","").replace(",","");
                            //验证表达式正确性
                            newStr= RuleUtil.resolveRuleStr(newStr);
                            ruleExpressionEntity1.setRuleExpression(newStr);
                        }
                    }
                    this.ruleExpressionMapper.insert(ruleExpressionEntity1);
                }
            }
            //保存规则数据源
            List<RuleDatasourceVO> ruleDatasourceVOList = ruleVo.getRuleDatasourceVOList();
            if(null!=ruleDatasourceVOList&&ruleDatasourceVOList.size()>0){
                for (RuleDatasourceVO ruleDatasourceVO:ruleDatasourceVOList) {
                    RuleDatasourceEntity ruleDatasourceEntity = new RuleDatasourceEntity();
                    //在这个地方，调用了一个vo的get方法，这也做不太好，不如写在正项代码里，或者以in做为标志
                    if("slave".equals(ruleDatasourceVO.getLogic())){
                        //需要处理的几个字段
                        /*String masterTable = ruleDatasourceVO.getMasterTable();
                        String currentTable = ruleDatasourceVO.getDatasourceName();
                        String masterStr = "";
                        String slaveStr = "";*/
                        //去掉字段的表信息
                        /*if(ruleDatasourceVO.getMasterField()!=null&&ruleDatasourceVO.getMasterField().indexOf("#")>-1){
                            masterStr = ruleDatasourceVO.getMasterField().split("#")[1];
                        }else{
                            masterStr = ruleDatasourceVO.getMasterField();
                        }
                        if(ruleDatasourceVO.getSlaveField()!=null&&ruleDatasourceVO.getSlaveField().indexOf("#")>-1){
                            slaveStr = ruleDatasourceVO.getSlaveField().split("#")[1];
                        } else {
                            slaveStr = ruleDatasourceVO.getSlaveField();
                        }*/
                        //因为传来的字段，本身是(表名+"#"+字段名)，因此可以换一种处理关系的方式
                        //ruleDatasourceVO.setRelationship(masterTable+"."+masterStr+"="+currentTable+"."+slaveStr);
                        //验证连接符
                        if(ruleDatasourceVO.getSymbol()==null||"".equals(ruleDatasourceVO.getSymbol())){
                            throw new Exception("关键词连接符不能为空");
                        }
                        if(ruleDatasourceVO.getMasterField()!=null&&ruleDatasourceVO.getSlaveField()!=null){
                            ruleDatasourceVO.setRelationship(ruleDatasourceVO.getMasterField().replace("#",".")+ruleDatasourceVO.getSymbol()+ruleDatasourceVO.getSlaveField().replace("#","."));
                        }
                        if(ruleDatasourceVO.getMasterFieldShow()!=null&&ruleDatasourceVO.getSlaveFieldShow()!=null){
                            ruleDatasourceVO.setRelationshipShow(ruleDatasourceVO.getMasterFieldShow().replace("#",".")+ruleDatasourceVO.getSymbol()+ruleDatasourceVO.getSlaveFieldShow().replace("#","."));
                        }
                    }
                    BeanUtils.copyProperties(ruleDatasourceVO, ruleDatasourceEntity);
                    //还有一个字段，再处理一次吧
                    if("slave".equals(ruleDatasourceVO.getLogic())){
                        if(ruleDatasourceVO.getSlaveField()!=null){
                            ruleDatasourceEntity.setRelationfield(ruleDatasourceVO.getSlaveField().replace("#","."));
                        }
                    }
                    ruleDatasourceEntity.setRuleId(ruleEntity.getRuleId());
                    this.ruleDatasourceMapper.insert(ruleDatasourceEntity);
                }
            }
        }
        //第三步，更新标签规则对象
        this.baseMapper.updateById(ruleEntity);
    }

    /**
     * 复制数据
     *
     * @param ruleId
     */
    @Override
    public Result<String> copyRule(Long ruleId) {

        return Result.rstSuccess();
    }

    /**
     * 引用数据
     *
     * @param ruleId
     */
    @Override
    public Result<String> quoteRule(Long ruleId, String userId, String realName) {
        //第一步，查询规则详情
        //RuleEntity ruleDTO = this.baseMapper.specificExpression(ruleId);
        RuleEntity ruleDTO = this.baseMapper.queryRuleById(ruleId);

        //判断一下规则状态，是否草稿
        if(ruleDTO.getStatus()==1){
            return Result.fail(500,"草稿不能引用");
        }

        //第二步，保存规则主体，完善需要复制的信息
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setRuleName(ruleDTO.getRuleName());
        ruleEntity.setRuleBody(ruleDTO.getRuleBody());
        ruleEntity.setStatus(ruleDTO.getStatus());
        ruleEntity.setCreatorUserName(userId);
        ruleEntity.setCreatorRealName(realName);
        ruleEntity.setCreateType("3");//1.新建 2.复制 3.引用
        this.baseMapper.insert(ruleEntity);

        //第三步保存协同
        List<CollectiveEntity> collectiveEntityList=ruleDTO.getCollectiveEntityList();
        for(int i=0;i<collectiveEntityList.size();i++){
            if(null!=collectiveEntityList.get(i).getCollectiveId()){
                collectiveEntityList.get(i).setRuleId(ruleEntity.getRuleId());
                //协同人不能保存自己，查出来什么，就是什么
                //collectiveEntityList.get(i).setUserName(userId);
                //collectiveEntityList.get(i).setRealName(realName);
                this.collectiveMapper.insert(collectiveEntityList.get(i));
            }
        }

        //第四步保存标签对象
        List<LabelObjectEntity> labelObjectEntityList=ruleDTO.getLabelObjectEntities();
        for (int k=0;k<labelObjectEntityList.size();k++){
            if(null!=labelObjectEntityList.get(k).getLabelObjectId()){
                labelObjectEntityList.get(k).setRuleId(ruleEntity.getRuleId());
                this.labelObjectMapper.insert(labelObjectEntityList.get(k));
            }
        }

        //第五步，保存标签规则表达式
        List<RuleExpressionEntity> ruleExpressionEntityList = ruleDTO.getRuleExpressionEntity();
        if(ruleExpressionEntityList!=null){
            for(int j=0;j<ruleExpressionEntityList.size();j++){
                RuleExpressionEntity ruleExpressionEntity = ruleExpressionEntityList.get(j);
                RuleExpressionEntity ruleExpressionEntity1 = new RuleExpressionEntity();
                ruleExpressionEntity1.setRuleId(ruleEntity.getRuleId());
                ruleExpressionEntity1.setLabelName(ruleExpressionEntity.getLabelName());
                ruleExpressionEntity1.setLabelType(ruleExpressionEntity.getLabelType());
                ruleExpressionEntity1.setRuleDescription(ruleExpressionEntity.getRuleDescription());
                ruleExpressionEntity1.setRuleExpression(ruleExpressionEntity.getRuleExpression());
                ruleExpressionEntity1.setCreatorUserName(userId);
                this.ruleExpressionMapper.insert(ruleExpressionEntity1);
                List<LabelConditionEntity> labelConditionEntityList = ruleExpressionEntityList.get(j).getLabelConditionEntities();
                if (labelConditionEntityList.size() > 0) {
                    for (int s = 0; s < labelConditionEntityList.size(); s++) {
                        LabelConditionEntity labelConditionEntity = labelConditionEntityList.get(s);
                        labelConditionEntity.setRuleExpressionId(ruleExpressionEntity1.getRuleExpressionId());
                        labelConditionEntity.setRuleId(ruleEntity.getRuleId());
                        this.labelConditionMapper.insert(labelConditionEntity);
                    }
                }
            }
        }

        return Result.rstSuccess(Constant.SUCCESS);
    }

    /**
     * 查询规则
     * @param ruleId 规则id
     * @return RuleEntity
     */
    @Override
    public RuleEntity queryRuleById(Long ruleId) {
        return this.baseMapper.queryRuleById(ruleId);
    }


}

