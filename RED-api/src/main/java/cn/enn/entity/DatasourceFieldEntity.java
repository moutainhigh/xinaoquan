package cn.enn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 数据源-字段
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Data
@TableName("t_datasource_field")
@ApiModel(value="TDatasourceField对象", description="数据源-字段")
public class DatasourceFieldEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "datasource_field_id", type = IdType.AUTO)
    private Long datasourceFieldId;

    @ApiModelProperty(value = "数据源编码")
    private Long datasourceId;

    @ApiModelProperty(value = "序号")
    private String fieldNumber;

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "字段类型")
    private String fieldType;

    @ApiModelProperty(value = "字段显示名称")
    private String fieldShowName;

    @ApiModelProperty(value = "单位")
    private String units;

    @ApiModelProperty(value = "选项或者条件：1.选项，2.条件")
    private Integer fieldOptions;

    @ApiModelProperty(value="如果选项为条件，可以设定单选或多选")
    private String fieldAttr;

    @ApiModelProperty(value = "如果是条件执行sql")
    private String fieldSql;

}
