package cn.enn.dao;

import cn.enn.entity.LabelTypeEntity;
import cn.enn.entity.RuleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface LabelTypeMapper extends BaseMapper<LabelTypeEntity> {
    /**
     * 查询标签：此方法用于新建标签的标签选项
     * @return List<LabelTypeEntity>
     */
    @Select("SELECT * FROM t_label_type t where t.parent_id=0 ")
    List<LabelTypeEntity> queryLabelType();

    /**
     * 查询标签：此方法用于查询标签列表
     * @return List<LabelTypeEntity>
     */
    @Select("SELECT * FROM t_label_type ")
    List<LabelTypeEntity> queryLabelList();

}
