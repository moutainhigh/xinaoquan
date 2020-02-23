package cn.enn.base.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据源数据传输对象
 * @author Administrator
 *
 */
@Data
@ApiModel(value="数据源Field对象", description="数据源Field对象")
public class DataSourceFieldDTO {

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

    @ApiModelProperty(value = "如果是条件，设置选项属性")
    private Integer fieldAttr;

    @ApiModelProperty(value = "如果是条件执行sql")
    private String fieldSql;

    @ApiModelProperty(value = "执行sql返回的json字符串")
    private String jsonStr;//动态json字符串，好象没啥用


}
