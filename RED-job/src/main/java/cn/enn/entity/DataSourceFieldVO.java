package cn.enn.entity;

import lombok.Data;

/**
 * 数据源数据传输对象
 * @author Administrator
 *
 */
@Data
public class DataSourceFieldVO {
    /**
     * 数据源名称
     */
    private String datasourceName;
    /**
     * 数据源字段
     */
    private String fieldName;
    /**
     * 数据源类型：1、本地数据库；2、其他数据库；3、kafka；4...
     */
    private String dataSourceType;
    /**
     * 数据关联字段
     */
    private String relationField;
    /**
     * 数据关联字段
     */
    private String relationship;

}
