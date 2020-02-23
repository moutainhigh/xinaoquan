package cn.enn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据源
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Data
@TableName("t_datasource")
@ApiModel(value="TDatasource对象", description="数据源")
public class DatasourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据源编码")
    @TableId(value = "datasource_id", type = IdType.AUTO)
    private Long datasourceId;

    @ApiModelProperty(value = "数据源名称")
    private String datasourceName;

    @ApiModelProperty(value = "数据源显示名称")
    private String datasourceShowName;

    @ApiModelProperty(value = "数据源类型")
    private String datasourceType;

    @ApiModelProperty(value = "数据源描述信息")
    private String datasourceDescribe;

}
