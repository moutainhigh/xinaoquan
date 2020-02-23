package cn.enn.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 项目信息
 * @author Administrator
 */
@Data
public class ProjectInfo implements Serializable {
    private static final long serialVersionUID = -2514701271782746636L;
    /**
     * 员工ID
     */
    private String empNo;
    /**
     * 项目ID
     */
    private String costNo;
    /**
     * 项目名称
     */
    private String costName;
    /**
     * 项目开始时间
     */
    private String startDate;
    /**
     * 项目结束时间
     */
    private String endDate;
    /**
     * 工时
     */
    private int workingHours;


}
