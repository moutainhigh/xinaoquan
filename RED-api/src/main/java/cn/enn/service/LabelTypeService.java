package cn.enn.service;

import cn.enn.entity.LabelTypeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 标签类型业务类
 * @author Administrator
 */
public interface LabelTypeService extends IService<LabelTypeEntity> {
    /**
     * 查询标签：此方法用于新建标签的标签选项
     * @return List<LabelTypeEntity>
     */
    List<LabelTypeEntity> queryLabelType();

    /**
     * 查询全部标签列表
     * @return List<LabelTypeEntity>
     */
    List<LabelTypeEntity> queryLabelList();

    /**
     * 查询结构化标签列表
     * @return List<LabelTypeEntity>
     */
    List<String[]> queryLabelStructure();
}
