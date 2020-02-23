package cn.enn.entity;

import lombok.Data;

/**
 *
 * @author Administrator
 */
@Data
public class DynamicTable {
   private String id;
    /**
     * 员工编号
     */
   private String empNo;
    /**
     * 员工姓名
     */
   private String empName;
    /**
     * 员工机构
     */
   private String orgCode;
    /**
     * 机构层级
     */
   private String orgPath;
    /**
     * 角色
     */
   private String role;
    /**
     * 成本价格
     */
   private String costPrice;
    /**
     * 销售价格
     */
   private String sellingPrice;
    /**
     * 开始时间
     */
   private String startDate;
    /**
     * 结束时间
     */
   private String endDate;
    /**
     * 创建时间
     */
   private String createTime;
    /**
     * 最后修改时间
     */
   private String lastModifyTime;
    /**
     * 删除标志位 0否 1是
     */
   private int  deleted;

}
