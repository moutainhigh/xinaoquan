package cn.enn.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 规则数据源VO
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Data
@ApiModel(value="TRuleDatasource对象", description="规则数据源")
public class RuleDatasourceVoOut implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long ruleDatasourceId;

    @ApiModelProperty(value = "规则编码")
    private Long ruleId;

    @ApiModelProperty(value = "数据源编号")
    private String datasourceId;

    @ApiModelProperty(value = "表英文显示名称")
    private String datasourceName;

    @ApiModelProperty(value = "表中文显示名称")
    private String datasourceShowName;

    @ApiModelProperty(value = "逻辑主或逻辑从")
    private String logic;

    @ApiModelProperty(value = "如果是逻辑从表，根据relationship解析得出")
    private String masterTable;
    @ApiModelProperty(value = "如果是逻辑从表，根据relationship解析得出,英文")
    private String masterField;
    @ApiModelProperty(value = "如果是逻辑从表，根据relationshipShow解析得出，中文")
    private String masterFieldShow;
    @ApiModelProperty(value = "如果是逻辑从表，根据relationship解析得出")
    private String slaveField;
    @ApiModelProperty(value = "如果是逻辑从表，根据relationshipShow解析得出，中文")
    private String slaveFieldShow;

    @ApiModelProperty(value = "关联字段连接符，")
    private String symbol;

    @ApiModelProperty(value = "关系表达式，英文")
    private String relationship;

    @ApiModelProperty(value = "关系表达式，中文")
    private String relationshipShow;


    //查询详情时，返回具体主子表和具体字段
    public String getMasterTable() {
        //解析关系表达式
        if("slave".equals(this.logic)){
            String[] arr1 = this.relationship.split(this.symbol);
            String[] arr2 = arr1[0].split("\\.");
            return arr2[0];
        }
        return null;
    }

    public String getMasterField() {
        //解析关系表达式
        if("slave".equals(this.logic)){
            //其实是将.换成"#"就可以了
            return this.relationship.split(this.symbol)[0].replace(".","#");
        }
        return null;
    }

    public String getMasterFieldShow() {
        //解析关系表达式
        if("slave".equals(this.logic)){
            //其实是将.换成"#"就可以了
            return this.relationshipShow.split(this.symbol)[0].replace(".","#");
        }
        return null;
    }

    public String getSlaveField() {
        //解析关系表达式
        if("slave".equals(this.logic)){
            return this.relationship.split(this.symbol)[1].replace(".","#");
        }
        return null;
    }

    public String getSlaveFieldShow() {
        //解析关系表达式
        if("slave".equals(this.logic)){
            return this.relationshipShow.split(this.symbol)[1].replace(".","#");
        }
        return null;
    }

}
