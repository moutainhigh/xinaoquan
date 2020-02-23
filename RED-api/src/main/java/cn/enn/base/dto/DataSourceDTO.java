package cn.enn.base.dto;

import cn.enn.entity.DatasourceFieldEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="数据源对象", description="数据源对象")
public class DataSourceDTO {
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

    private List<DatasourceFieldEntity> datasourceFieldEntityList;


}
