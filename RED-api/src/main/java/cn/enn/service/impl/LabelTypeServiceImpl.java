package cn.enn.service.impl;

import cn.enn.dao.LabelConditionMapper;
import cn.enn.dao.LabelTypeMapper;
import cn.enn.dao.RuleExpressionMapper;
import cn.enn.entity.LabelConditionEntity;
import cn.enn.entity.LabelTypeEntity;
import cn.enn.entity.RuleExpressionEntity;
import cn.enn.service.LabelTypeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @创建时间 2019/10/24
 * @Version 1.0
 */
@Service("LabelTypeService")
public class LabelTypeServiceImpl extends ServiceImpl<LabelTypeMapper, LabelTypeEntity> implements LabelTypeService {

    /**
     * 需要从标签条件中查出手动赋予的数据源(这个要去掉了，来源发生变化)
     */
    @Autowired
    private LabelConditionMapper labelConditionMapper;

    /**
     * 自定义的标签来源发生了变化
     */
    @Autowired
    private RuleExpressionMapper ruleExpressionMapper;

    @Override
    public List<LabelTypeEntity> queryLabelType() {

        return this.baseMapper.queryLabelType();
    }

    @Override
    public List<LabelTypeEntity> queryLabelList() {

        return this.baseMapper.queryLabelList();
    }

    @Override
    public List<String[]> queryLabelStructure() {
        //说明：需要两个数据源，一个是固定的二级标签名称，一个是赋予的标签名称（子表数据，与主表是一对多的关系）
        //1、先查出固定（主表）标签列表
        List<LabelTypeEntity> labelList = this.baseMapper.queryLabelList();
        //2、再查出赋予(子表)的标签数据
        RuleExpressionEntity lce = new RuleExpressionEntity();
        Wrapper<RuleExpressionEntity> ma = new QueryWrapper<RuleExpressionEntity>(lce,"label_name","label_type_id");
        List<RuleExpressionEntity> ruleExpressionEntityList = ruleExpressionMapper.selectList(ma);
        //可以将list转为map以提高查询速度

        //对标签列表处理并返回,增加一个数据源
        return structureLabel(labelList,ruleExpressionEntityList);
    }

    /**
     * 开始对标签列表进行结构化处理
     * @param labelList
     * @return
     */
    public List<String[]> structureLabel(List<LabelTypeEntity> labelList,List<RuleExpressionEntity> ruleExpressionEntityList){
        //第一步，定义返回的结果
        List<String[]> resultList = new ArrayList<String[]>();
        //第二步，处理labelList,从0（即顶级开始处理）,多了一个数据源,其实这一步，没有必要处理ruleExpressionEntityList，只是历史原因加上的，当时想一次处理完，结果不行
        resultList = findByPid(0,resultList,labelList,ruleExpressionEntityList);

        //第三步，后续处理，把最后节点层，查找自定义标签名称，不需要递归
        //把子节点为空的labelTypeId找出来
        for(int i=0;i<labelList.size();i++){
            //通过id查询子节点
            long labelId = labelList.get(i).getLabelId();
            List list = findListByPid(labelId,labelList);
            if(list==null){
                //说明是最底层，需要根据labelId查找自定义标签名称
                //可能被多次引用,所以还要去重
                List<RuleExpressionEntity> ruleExpressionEntityList1 = findLableConditionEntityListByLabelTypeId(labelId,ruleExpressionEntityList);
                if(ruleExpressionEntityList1!=null){
                    //需要去掉ruleExpressionEntityList1里重复数据
                    for(int m=0;m<ruleExpressionEntityList1.size()-1;m++){
                        for(int l=ruleExpressionEntityList1.size()-1;l>m;l--)  {
                            if  (ruleExpressionEntityList1.get(l).getLabelName().replaceAll("\\\\s*","").equals(ruleExpressionEntityList1.get(m).getLabelName().replaceAll("\\\\s*","")))  {
                                ruleExpressionEntityList1.remove(l);
                            }
                        }
                    }
                    //加入结果集
                    for(int j=0;j<ruleExpressionEntityList1.size();j++){
                        String[] arr = new String[2];
                        arr[0]=labelList.get(i).getLabelName();
                        arr[1]=ruleExpressionEntityList1.get(j).getLabelName();
                        resultList.add(arr);
                    }
                }
            };
        }
        return resultList;
    }
    //多了一个数据源
    public List<String[]> findByPid(long pid,List<String[]> resultList,List<LabelTypeEntity> labelList,List<RuleExpressionEntity> ruleExpressionEntityList){
        //固定标签数据源，根据pid返回子节点数据
        List<LabelTypeEntity> plist = findListByPid(pid,labelList);
        //第一层单独处理
        if(plist!=null&&pid==0){
            for(int x=0;x<plist.size();x++){
                String[] strs = new String[2];
                strs[0]="标签";
                strs[1]=plist.get(x).getLabelName();
                resultList.add(strs);
            }
        }
        //不是最后一级，则递归处理
        if(plist!=null){
            for(int i=0;i<plist.size();i++){
                //当前标签id
                long labelId=labelList.get(i).getLabelId();
                //查找子节点集合
                List<LabelTypeEntity> sublist = findListByPid(labelId,labelList);
                //查找完子节点，还要查找与子节点同级自定义标签，即在当前节点下，有没有自定义的标签，这个属于扩展功能，实应用时，可能永远为null
                List<RuleExpressionEntity> subLabelConditionEntityList = findLableConditionEntityListByLabelTypeId(labelId,ruleExpressionEntityList);
                if(subLabelConditionEntityList!=null){
                    //第一次循环，处理当前层和子层的数组
                    for(int f=0;f<subLabelConditionEntityList.size();f++){
                        //最终结果里的数组
                        String[] strs = new String[2];
                        //取外层循环名称
                        strs[0]=labelList.get(i).getLabelName();
                        //取内层循环名称
                        strs[1]=subLabelConditionEntityList.get(f).getLabelName();
                        //加入返回的结果
                        resultList.add(strs);
                    }
                }
                if(sublist!=null){
                    //第一次循环，处理当前层和子层的数组
                    for(int j=0;j<sublist.size();j++){
                        //最终结果里的数组
                        String[] strs = new String[2];
                        //取外层循环名称
                        strs[0]=labelList.get(i).getLabelName();
                        //取内层循环名称
                        strs[1]=sublist.get(j).getLabelName();
                        //加入返回的结果
                        resultList.add(strs);

                        //递归操作完成后，还要排序(层数少看不出来)，使用外递归（再次循环递归），不用排序
                        //findByPid(sublist.get(j).getLabelId(),resultList, labelList);
                    }

                    //第二次循环，采用递归，处理子层数据
                    for(int k=0;k<sublist.size();k++){
                        findByPid(sublist.get(k).getLabelId(),resultList, labelList,ruleExpressionEntityList);
                    }

                }else{
                    return resultList;
                }
            }
        //如果plist==null，说明当前labelTypeId是最后一级，需要查询当前类别下的自定义类别
        }else if(plist==null){
            //不能再这处理最后一层的业务，不是递归
            //查找完子节点，还要查找与子节点同级自定义标签，即在当前节点下，有没有自定义的标签
            //List<LabelConditionEntity> subLabelConditionEntityList = findLableConditionEntityListByLabelTypeId(pid,labelConditionEntityList);
            /*if(subLabelConditionEntityList!=null){
                //第一次循环，处理当前层和子层的数组
                for(int f=0;f<subLabelConditionEntityList.size();f++){
                    //最终结果里的数组
                    String[] strs = new String[2];
                    //根据id查找固定标签的实体，并取名子
                    LabelTypeEntity labelTypeEntity = findLabelTypeById(pid,labelList);
                    if(labelTypeEntity!=null){
                        strs[0]=labelTypeEntity.getLabelName();
                    }
                    //取内层循环名称
                    strs[1]=subLabelConditionEntityList.get(f).getLabelName();
                    //加入返回的结果
                    resultList.add(strs);
                }
            }*/
            return resultList;
        }
        return resultList;
    }
    //根据pid返回子节点
    public List<LabelTypeEntity> findListByPid(long pid,List<LabelTypeEntity> labelList){
        List<LabelTypeEntity> subLabelList = new ArrayList<LabelTypeEntity>();
        for(int i=0;i<labelList.size();i++){
            if(labelList.get(i).getParentId()==pid){
                subLabelList.add(labelList.get(i));
            }
        }
        if(subLabelList.size()>0){
            return subLabelList;
        }
        return null;
    }
    //根据id查找实体
    public LabelTypeEntity findLabelTypeById(long id,List<LabelTypeEntity> labelList){
        for(int i=0;i<labelList.size();i++){
            if(labelList.get(i).getLabelId()==id){
                return labelList.get(i);
            }
        }
        return null;
    }
    //根据labelTypeId返回自定义的标签名称
    public List<RuleExpressionEntity> findLableConditionEntityListByLabelTypeId(Long labelTypeId,List<RuleExpressionEntity> ruleExpressionEntityList){
        List<RuleExpressionEntity> subRuleExpressionEntityList = new ArrayList<RuleExpressionEntity>();
        for(int i=0;i<ruleExpressionEntityList.size();i++){
            if(null!=ruleExpressionEntityList.get(i)){
                if(labelTypeId.equals(ruleExpressionEntityList.get(i).getLabelTypeId())){
                    subRuleExpressionEntityList.add(ruleExpressionEntityList.get(i));
                }
            }
        }
        if(subRuleExpressionEntityList.size()>0){
            return subRuleExpressionEntityList;
        }
        return null;
    }
}
