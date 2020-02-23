package cn.enn.base.vo;

import cn.enn.entity.MapEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 数据源数据传输对象
 * @author Administrator
 *
 */
@Data
@ApiModel(value="数据源Field对象", description="数据源Field对象")
public class DataSourceFieldVO {

    @ApiModelProperty(value = "datasourceFieldId")
    private Long datasourceFieldId;

    @ApiModelProperty(value = "数据源(父级)编码")
    private Long datasourceId;

    @ApiModelProperty(value = "数据源表名")
    private String datasourceName;

    @ApiModelProperty(value = "数据源表中文名")
    private String datasourceShowName;

    @ApiModelProperty(value="字段名称")
    private String fieldName;

    @ApiModelProperty(value = "字段类型")
    private String fieldType;

    @ApiModelProperty(value = "序号")
    private String fieldNumber;

    @ApiModelProperty(value = "选项或者条件：1.选项，2.条件")
    private Integer fieldOptions;

    @ApiModelProperty(value = "如果是条件执行sql")
    private String fieldSql;

    @ApiModelProperty(value = "字段显示名称")
    private String fieldShowName;

    @ApiModelProperty(value = "字段选项属性(radio,checkbox等等)")
    private String fieldAttr;

    @ApiModelProperty(value = "条件选项列表")
    private List<MapEntity> answerList;

    @ApiModelProperty(value = "单位")
    private String units;

    //重写get方法
    public String getFieldName() {
        if(fieldName!=null&&fieldName.indexOf("#")==-1){
            return datasourceName+"#"+fieldName;
        }
        return fieldName;
    }
    public String getFieldShowName() {
        if(fieldShowName!=null&&fieldShowName.indexOf("#")==-1){
            return datasourceShowName+"#"+fieldShowName;
        }
        return fieldShowName;
    }
}
