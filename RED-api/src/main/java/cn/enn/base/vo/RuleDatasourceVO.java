package cn.enn.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 规则数据源VO
 * 这个实际上是参数in
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Data
@ApiModel(value="TRuleDatasource对象", description="规则数据源")
public class RuleDatasourceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long ruleDatasourceId;

    @ApiModelProperty(value = "规则编码")
    private Long ruleId;

    @ApiModelProperty(value = "数据源编号")
    private Long datasourceId;

    @ApiModelProperty(value = "表英文显示名称")
    private String datasourceName;

    @ApiModelProperty(value = "表中文显示名称")
    private String datasourceShowName;

    @ApiModelProperty(value = "逻辑主或逻辑从")
    private String logic;

    @ApiModelProperty(value = "如果是逻辑从表，即logic=salve，发送主表名称，否则为null")
    private String masterTable;
    @ApiModelProperty(value = "如果是逻辑从表，即logic=salve，发送关联的主表字段，否则为null，英文")
    private String masterField;
    @ApiModelProperty(value = "如果是逻辑从表，即logic=salve，发送关联的主表字段，否则为null，中文")
    private String masterFieldShow;
    @ApiModelProperty(value = "如果是逻辑从表，即logic=salve，发送关联的子表字段，否则为null，英文")
    private String slaveField;
    @ApiModelProperty(value = "如果是逻辑从表，即logic=salve，发送关联的子表字段，否则为null，中文")
    private String slaveFieldShow;
    @ApiModelProperty(value = "如果是逻辑从表，即logic=salve，发送关联字段联接符")
    private String symbol;
    @ApiModelProperty(value = "关系表达式，英文")
    private String relationship;
    @ApiModelProperty(value = "关系表达式，中文")
    private String relationshipShow;

    //查询详情时，返回具体主子表和具体字段
    public String getMasterTable() {
        return masterTable;
    }

    public String getMasterField() {
        return masterField;
    }

    public String getSlaveField() {
        return slaveField;
    }

    //重写关系表达式的方法，自动生成表达式，用在保存时
    //写的不好，应该写在正向代码里，改在代码里转换
    /*public String getRelationship() {
        if("slave".equals(logic)){
            String masterStr = "";
            String slaveStr = "";
            //去掉字段的表信息
            if(masterField!=null&&masterField.indexOf("#")>-1){
                masterStr = masterField.split("#")[1];
            }else{
                masterStr = masterField;
            }
            if(slaveField!=null&&slaveField.indexOf("#")>-1){
                slaveStr = slaveField.split("#")[1];
            } else {
                slaveStr = slaveField;
            }
            return masterTable+"."+masterStr+"="+datasourceName+"."+slaveStr;
        }else{
            return null;
        }
    }*/
}
